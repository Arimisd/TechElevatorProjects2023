package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private int userID;
    private BigDecimal balance;


    public Account (){};

    public Account (int id, int userID, BigDecimal balance){
        this.accountId = id;
        this.userID = userID;
        this.balance = balance;
    };


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
