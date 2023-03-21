package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeID;
    private int transferStatusID;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;


    public Transfer (){}

    public Transfer(int id, int transferTypeID, int transferStatusID, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferId = id;
        this.transferTypeID = transferTypeID;
        this.transferStatusID = transferStatusID;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }


    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeID() {
        return transferTypeID;
    }

    public void setTransferTypeID(int transferTypeID) {
        this.transferTypeID = transferTypeID;
    }

    public int getTransferStatusID() {
        return transferStatusID;
    }

    public void setTransferStatusID(int transferStatusID) {
        this.transferStatusID = transferStatusID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
