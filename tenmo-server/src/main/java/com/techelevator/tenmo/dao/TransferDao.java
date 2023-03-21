package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferReport;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    Transfer sendTEBucks(Transfer transfer, int currentUserId);

    Transfer requestTEBucks(Transfer transfer);

    List<Transfer> getTransferByUserId(int userId);

    List<Transfer> getAllTransfers (int userId);

    Transfer getTransferByTransferId(int transferId, int userId);

    List<Transfer> getPendingTransfers (int userId);

    void updateTransfer (Transfer transfer);

    void updateAccountBalance(int userId, BigDecimal amount);


}
