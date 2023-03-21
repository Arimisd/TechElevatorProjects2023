package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionService {
private TransferType transferType;
private TransferStatus transferStatus;
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser currentUser;
    public TransactionService(String url,AuthenticatedUser currentUser) {
        this.baseUrl = url;
        this.currentUser = currentUser;
    }


    private String authToken = null;
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    private void setCurrentUser(AuthenticatedUser currentUser){
     this.currentUser = currentUser;
    }


    /**
     * view my current balance
     */
    public BigDecimal getUserBalance (String userToken){
        BigDecimal userBalance = null;
        try{
        ResponseEntity<BigDecimal> response =
                restTemplate.exchange(baseUrl + "account", HttpMethod.GET, makeAuthEntity(userToken), BigDecimal.class);
        userBalance = response.getBody();
    } catch (RestClientResponseException | ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
        return userBalance;
    }


    /**
     * view my past transfers
     */
    public List<Transaction> viewPastTransfers(String userToken) {
        List<Transaction> transferList = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Transaction[]> response = restTemplate.exchange(baseUrl + "transfers", HttpMethod.GET,entity,Transaction[].class);
            Transaction[] transactions = response.getBody();

            for(Transaction transaction : transactions){
                HttpHeaders headers1 = new HttpHeaders();
                headers1.setBearerAuth(authToken);
                HttpEntity<String> entity1 = new HttpEntity<>(headers1);
                ResponseEntity<User> senderResponse = restTemplate.exchange(baseUrl + "users/" + transaction.getAccountFrom(), HttpMethod.GET, entity1, User.class);
                ResponseEntity<User> receiverResponse = restTemplate.exchange(baseUrl + "user/" + transaction.getAccountTo(), HttpMethod.GET, entity1, User.class);
                User sender = senderResponse.getBody();
                User receiver = receiverResponse.getBody();

                transaction.setFromName(sender.getUsername());
                transaction.setToName(receiver.getUsername());

                transferList.add(transaction);
            }
        } catch (RestClientResponseException | ResourceAccessException exception) {
            BasicLogger.log(exception.getMessage());
        }
        return transferList;
    }


    /**
     * view transaction details by ID
     */
    public void viewTransactionDetails(String userToken, int transactionId) {
        try{
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl + "transfer/{"+ transactionId + "}," , HttpMethod.GET, makeAuthEntity(userToken), Object[].class);
        Object[] transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException exception){
            BasicLogger.log(exception.getMessage());
        }
    }






    /**
     * view my pending requests
     * @return
     */
public List<Transaction> viewPendingRequests(String userToken) {
    try {
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl + "transfer/transfer_status/" , HttpMethod.GET, makeAuthEntity(userToken), Object[].class);
        Object[] transfers = response.getBody();
    } catch (RestClientResponseException | ResourceAccessException e) {
BasicLogger.log(e.getMessage());
    }
    return null;
}

//
//public Transaction approvePendingRequest(String userToken, )


    /**
     * LIST USERS
     */
    public List<User> listUsers(String userToken) {
        List<User> users = new ArrayList<>();
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "tenmo_user", HttpMethod.GET, makeAuthEntity(userToken),User[].class);
            users = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    /**
     * Send TE bucks
     */
    public Transaction sendTeBucks(Transaction newTransaction, String userToken) {
    Transaction returnedTransaction = null;

    HttpEntity<Transaction> entity = makeTransactionEntity(newTransaction,userToken);
    try {
        returnedTransaction = restTemplate.postForObject(baseUrl + "transfer", entity, Transaction.class);
    } catch (RestClientResponseException | ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
    return returnedTransaction;
}

    public Account updateAccounts(String userToken, User otherAcct, BigDecimal amount){
Account updatedSenderAccount = null;
Account updatedReceiverAccount = null;

ResponseEntity<Account[]> response = restTemplate.exchange(baseUrl + "account", HttpMethod.GET, makeAuthEntity(userToken), Account[].class);
Account[] accountSender = response.getBody();
Account senderAccount = null;
for (Account account : accountSender){
    if(account.getUserID() == currentUser.getUser().getId()){
        senderAccount = account;
        break;
    }
}
ResponseEntity<Account[]> responseReceiver = restTemplate.exchange(baseUrl + "account", HttpMethod.GET, makeAuthEntity(userToken), Account[].class);
Account[] accountReceiver = responseReceiver.getBody();
Account receiverAccount = null;
for(Account account : accountReceiver){
    if(account.getUserID() == otherAcct.getId()){
        receiverAccount = account;
        break;
    }
}
BigDecimal senderBalance = senderAccount.getBalance().subtract(amount);
senderAccount.setBalance(senderBalance);
HttpEntity<Account> senderAccountRequest = new HttpEntity<>(senderAccount, makeAuthEntity(userToken));
ResponseEntity<Account> senderResponse = restTemplate.exchange(baseUrl + "account/" + senderAccount.getAccountId(), HttpMethod.PUT, senderAccountRequest, Account.class);
updatedSenderAccount = senderResponse.getBody();

BigDecimal receiverBalance = receiverAccount.getBalance().add(amount);
receiverAccount.setBalance(receiverBalance);
HttpEntity<Account> receiverAccountRequest = new HttpEntity<>(receiverAccount, makeAuthEntity(userToken));
ResponseEntity<Account> receiverResponse = restTemplate.exchange(baseUrl + "account/" + receiverAccount.getAccountId(), HttpMethod.PUT, receiverAccountRequest, Account.class);
updatedReceiverAccount = receiverResponse.getBody();

Transaction transaction = new Transaction();
transaction.setAccountFrom(senderAccount.getAccountId());
transaction.setAccountTo(receiverAccount.getAccountId());
transaction.setAmount(amount);

HttpEntity<Transaction> transactionRequest = new HttpEntity<>(transaction, makeAuthEntity(userToken));
ResponseEntity<Transaction> transactionResponse = restTemplate.exchange(baseUrl + "transaction", HttpMethod.POST, transactionRequest, Transaction.class);
Transaction updatedTransaction = transactionResponse.getBody();

return updatedSenderAccount;
    }



    /**
     * Request TE bucks
     */
    public void requestBucks(String userToken, int accountToId, BigDecimal amount){
        try{
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(currentUser.getUser().getId());
            transaction.setAccountTo(accountToId);
            transaction.setAmount(amount);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }



//need to fix transferlist




    /**
     * Entity
     */
    private HttpEntity<Void> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transaction> makeTransactionEntity(Transaction transaction, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(transaction, headers);
    }

    public void updateAccounts(String token, Transaction transfer) {
    }
}
