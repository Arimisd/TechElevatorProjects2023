package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransactionService;

import java.math.BigDecimal;
import java.util.List;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private AuthenticatedUser currentUser;
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TransactionService transactionService = new TransactionService(API_BASE_URL, currentUser);


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        System.out.println("$$$ TEBucks Balance $$$ : " + transactionService.getUserBalance(currentUser.getToken()));


    }

    private void viewTransferHistory() {
        // TODO Method works but details returned in output need formatted
        List<Transaction> transactions = transactionService.viewPastTransfers(currentUser.getToken());

        System.out.println(
                "\n--------------------------------------------" +
                        "\n Transfers" +
                        "\n ID" + "\t\t" + "From/To" + "\t\t" + "Amount" +
                        "\n --------------------------------------------");

        if (transactions.isEmpty()) {
            System.out.println("You have no transfer history to show.");
        } else {
            for (Transaction transaction : transactions) {
                String fromTo = transaction.getAccountFrom() == currentUser.getUser().getId() ?
                        "To: " + transaction.getAccountTo() :
                        "From: " + transaction.getAccountFrom();
                System.out.format("%-8d%-25s$%-10.2f\n", transaction.getTransferId(), fromTo, transaction.getAmount());
            }
        }
        System.out.println("\n--------------------------------------------");
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method
        List<Transaction> pendingTransfer = transactionService.viewPendingRequests(currentUser.getToken());

        if (pendingTransfer.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("Pending Requests: ");
        System.out.println("---------------------");
        for (int i = 0; i < pendingTransfer.size(); i++) {
            Transaction request = pendingTransfer.get(i);
            System.out.println("Request #" + (i + 1) + ":");
            System.out.println("From: " + request.getAccountFrom());
            System.out.println("To: " + request.getAccountTo());
            System.out.println("___________________________");

        }
    }


    private void sendBucks() {
        // TODO Auto-generated method stub
        List<User> userList = transactionService.listUsers(currentUser.getToken());
        String requestedUsername = null;
        Transaction transaction = new Transaction();

        printUserList();
        User recipient = selectUser();

        BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:  ");
        BigDecimal userBalance = transactionService.getUserBalance(currentUser.getToken());
        // if x > y, then = 1
        if ((amount.compareTo(userBalance) == 1) || BigDecimal.valueOf(0.01).compareTo(amount) == 1){
            System.out.println("Invalid amount. please try again");
            return;
        }

        transaction.setTransferTypeID(2);
        transaction.setTransferStatusID(2);
        transaction.setAmount(amount);
        transaction.setAccountFrom(2003);
        transaction.setAccountTo(2002);
//        transaction.setAccountFrom(currentUser.getUser().getId());
//        transaction.setAccountTo(recipient.getId());

        try {
            transactionService.sendTeBucks(transaction, currentUser.getToken());
            System.out.println("You have completed your transfer of " + amount + " TeBucks to " + recipient.getUsername() + ".");
        } catch (Exception e) {
            System.out.println("Transfer failed -- please try again.");
        }
        // update both accounts () transferTo()  ACCOUNT
        // use transactions on SQL side in case of error and need to Rollback?

    }


    private void requestBucks() {
        List<User> userList = transactionService.listUsers(currentUser.getToken());
        String requestedUsername = null;
        Transaction transaction = new Transaction();

        printUserList();
        User recipient = selectUser();


        BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:  ");
        // if x > y, then = 1
        if (BigDecimal.valueOf(0.01).compareTo(amount) == 1){
            System.out.println("Invalid amount. please try again");
            return;
        }

        transaction.setAccountFrom(currentUser.getUser().getId());
        transaction.setAccountTo(recipient.getId());
        transaction.setAmount(amount);
        transaction.setTransferStatusID(1);
        transaction.setTransferTypeID(2);

        System.out.println("___________________________");
        System.out.println("");
        try {
            transactionService.requestBucks(currentUser.getToken(), recipient.getId(), amount);
            System.out.println("Request has been sent to " + recipient.getUsername() + " for the amount of "+ amount + ".");
        } catch (Exception e) {
            System.out.println("Transfer request failed please try again");
        }
        System.out.println("");
        System.out.println("___________________________");
        System.out.println("");
    }


    private void printUserList() {
        System.out.println("___________________________");
        System.out.println("");
        System.out.println("  ::: USERS :::");
        System.out.println("___________________________");
        for (User user : transactionService.listUsers(currentUser.getToken())) {
            if (!currentUser.getUser().getUsername().equals(user.getUsername())) {
                System.out.println(" " +user.getUsername());
            }
        }
    }

    private User selectUser() {
        System.out.println("___________________________");
        System.out.println("");
        String enteredName = consoleService.promptForString("Enter a Username:   ");
        List<User> userList = transactionService.listUsers(currentUser.getToken());
        User requestedUser = null;

        for (User user : userList) {
            if (user.getUsername().equals(enteredName)) {
                requestedUser = user;
                return requestedUser;
            }
        }
            if (requestedUser == null) {
                System.out.println("___________________________");
                System.out.println("");
                System.out.println("Invalid username. Please try again.");
                return null;
            }
        return requestedUser;
    }

    private void updatePendingTransfer(){
        List<Transaction> pendingTransfers = transactionService.viewPendingRequests(currentUser.getToken());
        if(pendingTransfers.isEmpty()){
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("-----PENDING REQUESTS-----");
        for(int i = 0; i < pendingTransfers.size(); i++){
            Transaction transfer = pendingTransfers.get(i);
            System.out.println("Request # " + (i + 1) + ":");
            System.out.println("From:" + transfer.getAccountFrom());
            System.out.println("To: " + transfer.getAccountTo());
            System.out.println("Amount $: " + transfer.getAmount());

            int choice =

                    consoleService.promptForMenuSelection("1: Approve\n" +
                            "2: Reject \n")
                    if(choice == 1){
                        transfer.setTransferStatusID(2);//approved
                    }
                    transactionService.updateAccounts(currentUser.getToken(),transfer);

        }


        // transactionService.setPendingStatus(status);

    }

}
