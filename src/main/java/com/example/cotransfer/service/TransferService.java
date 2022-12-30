package com.example.cotransfer.service;

import com.example.cotransfer.model.Transfer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransferService {

    Page<Transfer> getAllTransfers(Pageable pageable);

    Transfer getTransfer(Long id);

    void save(Transfer transfer);

    void deleteTransfer(Long id);

    void update(Transfer transfer);

    ResponseEntity<?> createTransferFromAirport(String transfer, Long id);

    ResponseEntity<?> updateTransfer(String transfer, Long id);
}
