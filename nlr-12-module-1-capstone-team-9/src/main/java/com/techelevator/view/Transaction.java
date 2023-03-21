package com.techelevator.view;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {
    private final String type;
    private final BigDecimal amount;
    private final Date dateTime;
    private static final List<Transaction> transactions = new ArrayList<>();
    private double currentBalance;
    private BigDecimal balance;


    public Transaction(String type, BigDecimal amount){
        this.type = type;
        this.amount = amount;
        this.dateTime = new Date();
    }

    public static void logTransaction() {
        String fileName = "Transactions.log";
        try(FileWriter fileWriter = new FileWriter(fileName,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void logTransaction(Transaction transaction){
      transactions.add(transaction);
    }
public static void clearTransactions() {

        transactions.clear();

}

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public static List<Transaction> getTransactions(){
 return transactions;
}



    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDateTime() {
        return dateTime;
    }
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        return String.format("%s %s: $.2f", dateFormat.format(dateTime), type,amount);
    }
}
