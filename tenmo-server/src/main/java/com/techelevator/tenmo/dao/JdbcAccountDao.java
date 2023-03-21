package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.swing.*;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public BigDecimal getBalanceByUserId(int user){
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,user);
        BigDecimal accountBalance = null;

        if (results.next()){
            accountBalance = results.getBigDecimal("balance");
        }
        return accountBalance;
    }



    @Override
    public Account getAccountsByUserId(int userId) {
     String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
     SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
     Account account = null;
     if(results.next()){
         account = mapRowToAccount(results);
     }
     return account;
    }

    @Override
    public Account getAccountByAcctId(int acctId) {
        String sql = "SELECT account_id, user_id, balance WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,acctId);
        Account account = null;
        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public void increaseAccount(int toAccount, BigDecimal amountToAdd){
        String sql = "UPDATE account SET balance = balance + ? WHERE user_id = ?";
        jdbcTemplate.update(sql, amountToAdd, toAccount);

    }

    @Override
    public void decreaseAccount(int fromAccount, BigDecimal amountSubtract ){
        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?";
        jdbcTemplate.update(sql, amountSubtract,fromAccount);
    }





    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setUserID(rs.getInt("user_id"));
        return account;
    }

}
