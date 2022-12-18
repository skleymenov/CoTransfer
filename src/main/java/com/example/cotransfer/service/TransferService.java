package com.example.cotransfer.service;

import com.example.cotransfer.model.Transfer;

import java.util.List;

public interface TransferService {

    List<Transfer> getAllTransfers();

    Transfer getTransfer(Long id);

    void save(Transfer transfer);

    void deleteTransfer(Long id);

    void update(Transfer transfer);
}
