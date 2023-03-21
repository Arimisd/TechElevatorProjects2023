package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalanceByUserId(int user);

    Account getAccountsByUserId(int UserId);

    Account getAccountByAcctId (int AcctId);

    void decreaseAccount(int fromAccount, BigDecimal amountSubtract );

    void increaseAccount(int toAccount, BigDecimal amountToAdd);

}
