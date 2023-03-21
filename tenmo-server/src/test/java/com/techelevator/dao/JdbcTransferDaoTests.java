package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {

private int currentUserId;
    private JdbcTransferDao jdbcTransferDao;
private JdbcTemplate jdbcTemplate;
    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTransferDao = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
public void createTransfer_returns_new_transfer_with_id(){
Transfer transfer = new Transfer();
transfer.setTransferTypeID(2001);
transfer.setTransferStatusID(2001);
transfer.setAccountFrom(2001);
transfer.setAccountTo(3001);
transfer.setAmount(new BigDecimal("1000.00"));

        Transfer createdTransfer = jdbcTransferDao.sendTEBucks(transfer,currentUserId);

        Assert.assertNotNull(createdTransfer);
        Assert.assertEquals(2001,createdTransfer.getTransferTypeID());
        Assert.assertEquals(2001, createdTransfer.getTransferStatusID());
        Assert.assertEquals(2001,createdTransfer.getAccountFrom());
    }
    @Test
    public void get_correct_transfers_list_with_userId(){
        int userId = 1001;
        List<Transfer> transfers = jdbcTransferDao.getTransferByUserId(userId);
        Assert.assertNotNull(transfers);
        Assert.assertEquals(1001,transfers.size());
        Assert.assertEquals(2001,transfers.get(0).getAccountFrom());

    }
    @Test
    public void get_all_transfers_returns_list_of_all_transfers(){
        List<Transfer> transfers = jdbcTransferDao.getAllTransfers(1001);
        Assert.assertNotNull(transfers);
        Assert.assertEquals(1001,transfers.size());
        Assert.assertEquals(2001, transfers.get(0).getAccountFrom());

    }
    @Test
    public void get_transfer_by_transfer_id_returns_single_transfer_with_given_id(){
        int transferId = 3001;
        int userID = 1001; // TODO fix this number
        Transfer transfer = jdbcTransferDao.getTransferByTransferId(userID,transferId);
        Assert.assertNotNull(transfer);
        Assert.assertEquals(2001,transfer.getAccountFrom());

    }
    @Test
    public void get_pending_transfers_returns_list_of_transfers_with_pending_status_based_on_userId(){
int userId = 1001;
List<Transfer> transfers = jdbcTransferDao.getPendingTransfers(1001);
Assert.assertNotNull(transfers);
Assert.assertEquals(1001, transfers.size());
Assert.assertEquals(2001, transfers.get(0).getAccountFrom());
    }

    }
