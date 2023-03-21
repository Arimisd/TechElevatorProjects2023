package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transaction {

    private int transferId;
    private int transferTypeID;
    private int transferStatusID;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private String type;
    private String status;
    private String fromName;
    private String toName;




    @Override
    public String toString() {     //TODO partially complete
//        return "\n--------------------------------------------" +
//                "\n Transfers" +
//                "\n ID" +"\t"+ "From/To" +"\t"+ "Amount" +
//                "\n --------------------------------------------" +
               return "\n" + transferId +
                       "\t"+ "type/from:to:" +
                       "\t from:" + fromName +
                ", accountTo=" + toName +
                " $ " + amount ;
//                "transferTypeID=" + transferTypeID +
//                ", transferStatusID=" + transferStatusID + // change this to description

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }
}
