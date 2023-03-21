package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReportDao implements ReportDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<TransferReport> getAllTransfersNew(int user) {
        String sql = "SELECT transfer_id, transfer_type_id, sender.username as sender, receiver.username as receiver, amount "+
                " FROM transfer " +
                " JOIN account as from_account ON from_account.account_id = transfer.account_from " +
                " JOIN account as to_account ON to_account.account_id = transfer.account_to " +
                " JOIN tenmo_user as sender ON to_account.user_id = sender.user_id " +
                " JOIN tenmo_user as receiver ON from_account.user_id = receiver.user_id " +
                " WHERE user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
        List<TransferReport> transfers = new ArrayList<>();

        while(results.next()){
            transfers.add(mapResultToReport(results));
        }
        return transfers;
    }


    private TransferReport mapResultToReport(SqlRowSet result){
        int transferId = result.getInt("transfer_id");
        String from = result.getString("sender");
        String to = result.getString("receiver");
        String type = result.getString("type");
        String status = result.getString("status");
        BigDecimal amount = result.getBigDecimal("amount");

        TransferReport transferReport = new TransferReport(transferId,from,to, type, status, amount);
        return transferReport;
    }

}
