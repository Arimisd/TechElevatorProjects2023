package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcAccountDaoTests extends BaseDaoTests {


    private JdbcAccountDao accountDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        accountDao = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void user_getBalance_returns_correct_amount() {
        BigDecimal balance = accountDao.getBalanceByUserId(1001);

        assertEquals(new BigDecimal("1000.00"), balance);
    }

    @Test
    public void getAccountByUserId_returns_correct_account() {
        Account account = accountDao.getAccountsByUserId(1001);
        assertEquals(1001, account.getUserID());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    public void get_account_by_acct_id_returns_correct_account() {
        Account account = accountDao.getAccountByAcctId(2001);
        assertEquals(1001, account.getUserID());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }


//    @Test
//    public void update_account_updates_balance_correctly() {
//        Account account = accountDao.getAccountByAcctId(2001);
//        BigDecimal oldBalance = account.getBalance();
//        BigDecimal amountToAdd = new BigDecimal("500.00");
//        accountDao.updateAccount(account.getAccountId(),amountToAdd);
//
//        account = accountDao.getAccountByAcctId(2001);
//        BigDecimal newBalance = account.getBalance();
//        assertEquals(oldBalance.add(amountToAdd), newBalance);
//
//    }

}
