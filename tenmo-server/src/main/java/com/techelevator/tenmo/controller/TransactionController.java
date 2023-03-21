package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;

import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.model.TransferStatus;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("")

public class TransactionController {
    private ReportDao reportDao;
    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    private TransferStatus transferStatus;
    private TransferType transferType;


    public TransactionController(AccountDao accountDao, TransferDao transferDao, UserDao userDao, ReportDao reportDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
       this.reportDao = reportDao;
    }

//@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/tenmo_user", method = RequestMethod.GET)
    public List<User> usernameList(Principal principal) {
       String userName = principal.getName();
       int currentUserId = userDao.findIdByUsername(userName);
        return userDao.findAll();
        // this returns password hash too, which we don't want to show in a users list
    }
//    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public BigDecimal getUserBalance(Principal principal) {
        String userName = principal.getName();
        int userId = userDao.findIdByUsername(userName);
        BigDecimal userBalance = accountDao.getBalanceByUserId(userId);
        return userBalance;
    }


//    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/account", method = RequestMethod.PUT)
    public void updateBalances(Principal principal,User otherAccount, BigDecimal amount) {
       // decrease the sender (current user) balance
        String userName = principal.getName();
        int currentUserID = userDao.findIdByUsername(userName);
        accountDao.decreaseAccount(currentUserID, amount);
       // increase the receiver balance
        int otherUserID = otherAccount.getUserId();
        accountDao.increaseAccount(otherUserID,amount);
    }

//    @PreAuthorize("Has role (User)")
    @RequestMapping(value = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer transferDetails(@PathVariable int transferId, Principal principal) {
        String userName = principal.getName();
        int id = userDao.findIdByUsername(userName);
        Transfer transferDetails = transferDao.getTransferByTransferId(transferId, id);
        return transferDetails;
    }

//    @PreAuthorize("Has role (User)")
//    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
//    public List<Transfer> viewTransfers(Principal principal) {
//        String userName = principal.getName();
//        int id = userDao.findIdByUsername(userName);
//        List<Transfer> transferList = transferDao.getAllTransfers(id);
//        return transferList;
//    }

    //    @PreAuthorize("Has role (User)")
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public List<TransferReport> viewTransfersNew(Principal principal) {
        String userName = principal.getName();
        int id = userDao.findIdByUsername(userName);
        List<TransferReport> transferReportList = reportDao.getAllTransfersNew(id);
        return transferReportList;
    }



//    @PreAuthorize("Has role (User)")
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void sendTransfer(@RequestBody Transfer transfer, Principal principal) {
        String senderUserName = principal.getName();
        int currentUserId = userDao.findIdByUsername(senderUserName);

        //checking if sender is trying to infinity loop those funds yo.
        if (transfer.getAccountFrom() == transfer.getAccountTo()) {
            throw new IllegalArgumentException("You cannot send funds to yourself.");
        }
        //Check if sender has enough money to transfer
        BigDecimal senderBalance = accountDao.getBalanceByUserId(currentUserId);
        BigDecimal amountToTransfer = transfer.getAmount();
        if (senderBalance.compareTo(amountToTransfer) < 0) {
            throw new IllegalArgumentException("Insufficient funds to complete transfer, sorry for the inconvenience");
        }

        //Check if the amount sender is trying to transfer is positive and not 0
        if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }


        transferDao.sendTEBucks(transfer, currentUserId);

    }


//    @PreAuthorize("Has role (User)")
    @RequestMapping(value = "/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferDetails(@PathVariable int transferId, Principal principal){
        String userName = principal.getName();
        int currentUserId = userDao.findIdByUsername(userName);
        return transferDao.getTransferByTransferId(transferId,currentUserId);
}






    }
