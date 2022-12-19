package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Auto;
import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.repository.AutoRepository;
import com.example.cotransfer.repository.TransferRepository;
import com.example.cotransfer.service.TransferService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImplementation implements TransferService {

    private final TransferRepository transferRepository;
    private final AutoRepository autoRepository;

    @Override
    public Page<Transfer> getAllTransfers(Pageable pageable) {
        log.info("Получение всех трансферов");
        Page<Transfer> allTransfers = transferRepository.findAll(pageable);
        log.info("Все трансферы получены");
        return allTransfers;
    }

    @Override
    public Transfer getTransfer(Long id) {
        log.info("Получение трансфера с id:{}", id);
        Optional<Transfer> optional = transferRepository.findById(id);
        if (optional.isPresent()) {
            Transfer transfer = optional.get();
            log.info("Трансфер получен с id:{}", id);
            return transfer;
        } else
            throw new EntityNotFoundException("Трансфер не найден");
    }

    @Override
    public void save(Transfer transfer) {
        log.info("Создание трансфера");
        transferRepository.save(transfer);
        log.info("Трансфер создан и записан в базу");
    }

    @Override
    public void deleteTransfer(Long id) {
        log.info("Удаление трансфера с id:{}", id);
        transferRepository.deleteById(id);
        log.info("Трансфер удалён с id:{}", id);
    }

    @Override
    public void update(Transfer transfer) {
        log.info("Обновление данных о трансфере с id: {} ", transfer.getId());
        transferRepository.save(transfer);
        log.info("Данные обновлены");
    }

    @Override
    public ResponseEntity<?> createTransfer(String transfer) {
        log.info("Создание трансфера");
        JSONObject jsonObject = new JSONObject(transfer);
        Transfer newTransfer = new Transfer();

        newTransfer.setTripDate(jsonObject.getString("date"));
        newTransfer.setStartPlace(jsonObject.getString("startPlace"));
        newTransfer.setEndPlace(jsonObject.getString("endPlace"));
        newTransfer.setAdultsAmount(jsonObject.getInt("adults"));
        newTransfer.setChildrenAmount(jsonObject.getInt("children"));

        String autoType = jsonObject.getString("auto");
        int passengers = jsonObject.getInt("adults") + jsonObject.getInt("children");

        if (autoType.equals("VITO") && passengers > 0 && passengers < 8) {
            newTransfer.setAutoType(autoType);
            transferRepository.save(newTransfer);
            log.info("Трансфер создан");
            return ResponseEntity.ok("Трансфер создан, Вы выбрали вито");
        } else if (autoType.equals("Седан")&& passengers > 0 && passengers < 4) {
            newTransfer.setAutoType(autoType);
            transferRepository.save(newTransfer);
            log.info("Трансфер создан");
            return ResponseEntity.ok("Трансфер создан, Вы выбрали Седан");
        } else
            return ResponseEntity.status(400).body("Слишком много пассажиров");
    }


}
