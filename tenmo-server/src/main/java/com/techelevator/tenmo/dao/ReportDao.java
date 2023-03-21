package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferReport;

import java.util.List;

public interface ReportDao {

    List<TransferReport> getAllTransfersNew(int user);


}
