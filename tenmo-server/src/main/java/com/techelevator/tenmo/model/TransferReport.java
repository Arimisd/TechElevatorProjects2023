package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferReport {

    private int transferId;
    private String from;
    private String to;
    private String type;
    private String status;
    private BigDecimal amount;

    public TransferReport() {
    }

    public TransferReport(int transferId, String from, String to, String type, String transferTypeDescription, BigDecimal amount) {
        this.transferId = transferId;
        this.from = from;
        this.to = to;
        this.type = type;
        this.status = status;
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
