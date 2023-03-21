package com.techelevator.view;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Test;

public class TransactionTest {



    @Test
    public void transaction_test_log_transaction() {

        Transaction transaction = new Transaction("purchase" , new BigDecimal(20));
        Transaction.logTransaction(transaction);

        List<Transaction> transactions = Transaction.getTransactions();

        assertEquals(1, transactions.size());
        assertEquals("purchase", transactions.get(0).getType());
    }

    @Test
    public void transaction_test_clearing_transactions() {

        Transaction transaction = new Transaction("restock", new BigDecimal(100));
        Transaction.logTransaction(transaction);

        Transaction.clearTransactions();
        List<Transaction> transactions = Transaction.getTransactions();

        assertEquals(0, transactions.size());
    }

    @Test
    public void transaction_test_get_current_balance() {

        Transaction transaction = new Transaction("deposit", new BigDecimal(50));

        transaction.setCurrentBalance(50.0);

        assertEquals(50.0, transaction.getCurrentBalance(), 0.01);

    }

    @Test
    public void transaction_test_get_type() {

        Transaction transaction = new Transaction("Stackers", new BigDecimal(1.45));

        assertEquals("Stackers", transaction.getType());

    }

    @Test
    public void transaction_test_get_amount() {

        Transaction transaction = new Transaction("addedMoney", new BigDecimal(100));

        assertEquals(100.0, transaction.getAmount().doubleValue(), 0.01);

    }

    @Test
    public void transaction_test_get_date_time() {

        Transaction transaction = new Transaction("deposit", new BigDecimal(20));

        long currentTime = System.currentTimeMillis()/ 200;

        assertEquals(currentTime, transaction.getDateTime().getTime()/ 200, 1);

    }

}
