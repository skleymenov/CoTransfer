package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.repository.TransferRepository;
import com.example.cotransfer.service.TransferService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImplementation  implements TransferService {

    private final TransferRepository transferRepository;

    @Override
    public List<Transfer> getAllTransfers() {
        log.info("Получение всех трансферов");
        List<Transfer> allTransfers = transferRepository.findAll();
        log.info("Все трансферы получены");
        return allTransfers;
    }

    @Override
    public Transfer getTransfer(Long id) {
        log.info("Получение трансфера с id:{}", id);
        Optional<Transfer> optional = transferRepository.findById(id);
        if(optional.isPresent()){
            Transfer transfer = optional.get();
            log.info("Трансфер получен с id:{}", id);
            return transfer;
        }else
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
}
