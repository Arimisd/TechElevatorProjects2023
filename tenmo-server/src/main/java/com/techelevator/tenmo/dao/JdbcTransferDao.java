package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer sendTEBucks(Transfer transfer, int currentUserId) {
        Transfer newTransfer = new Transfer();
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                " VALUES ( " +
                " 2, 2, ?,?,?)";
        // I manually entered the IDs because the SQL below is giving me an error in postman
//                "(SELECT transfer_type_id FROM  transfer_type WHERE transfer_type_desc = 'Send'), " +
//                " (SELECT transfer_status_id FROM transfer_status WHERE transfer_status = 'Approved'),?,?,?)";
         jdbcTemplate.update(sql, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        newTransfer = null; // need to change this to something??
        return newTransfer;

    }



    @Override
    public Transfer requestTEBucks(Transfer transfer) {
        Transfer newTransfer = new Transfer();
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                " VALUES ((SELECT transfer_type_id FROM  transfer_type WHERE transfer_type_desc = 'Request'), " +
                " (SELECT transfer_status_id FROM transfer_status WHERE transfer_status = 'Pending')),?,?,?)";
        jdbcTemplate.update(sql, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return newTransfer;

    }

    @Override
    public List<Transfer> getTransferByUserId(int userId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "FROM transfer" +
                "JOIN account ON account.account_id = transfer.account_from OR account.account_id = transfer.account_to "+
                "WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        List<Transfer> transfers = new ArrayList<>();

        while(results.next()){
            transfers.add(mapResultToTransfer(results));
        }
        return transfers;
    }

    @Override
    public List<Transfer> getAllTransfers(int user) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount "+
                " FROM transfer " +
                " JOIN account ON account.account_id = transfer.account_from " +
                " OR account.account_id = transfer.account_to " +
                " WHERE user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
        List<Transfer> transfers = new ArrayList<>();

        while(results.next()){
            transfers.add(mapResultToTransfer(results));
        }
        return transfers;
    }


    @Override
    public Transfer getTransferByTransferId(int transferId,int userId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId, userId);
        Transfer transfer = null;

        if (result.next()){
            transfer = mapResultToTransfer(result) ;
        }
        return transfer;
    }

    @Override
    public List<Transfer> getPendingTransfers(int userId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer.transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "JOIN account ON account.account_id = transfer.account_from " +
                "JOIN transfer_statuses ON transfer.transfer_status_id = transfer_statuses.transfer_status_id "+
                "WHERE user_id = ? AND transfer_status_desc = 'Pending'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        List<Transfer> transfers = new ArrayList<>();

        while(results.next()){
            transfers.add(mapResultToTransfer(results));
        }
        return transfers;
    }

    @Override
    public void updateTransfer(Transfer transfer) {
        String sql = "UPDATE transfer " +
                "SET transfer_status_id = ?" +
                "WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferStatusID(), transfer.getTransferId());
    }
    @Override
    public void updateAccountBalance(int userId, BigDecimal amount){
    String sql = "UPDATE account SET balance = balance + ? WHERE user_id = ?";
    jdbcTemplate.update(sql, amount, userId);
}


    private Transfer mapResultToTransfer(SqlRowSet result){
        int transferId = result.getInt("transfer_id");
        int transferTypeId = result.getInt("transfer_type_id");
        int transferStatusId = result.getInt("transfer_status_id");
        int accountFrom = result.getInt("account_from");
        int accountTo = result.getInt("account_to");
        BigDecimal amount = result.getBigDecimal("amount");
//        String amountDouble = result.getString("amount");

        Transfer transfer = new Transfer(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
        return transfer;
    }
}
