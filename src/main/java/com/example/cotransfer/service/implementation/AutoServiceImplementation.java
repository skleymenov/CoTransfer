package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Auto;
import com.example.cotransfer.repository.AutoRepository;
import com.example.cotransfer.service.AutoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoServiceImplementation implements AutoService {

    private final AutoRepository autoRepository;

    @Override
    public List<Auto> getAllAuto() {
        log.info("Получение всех авто");
        List<Auto> allAuto = autoRepository.findAll();
        log.info("Все авто получены");
        return allAuto;
    }

    @Override
    public Auto getAuto(Long id) {
        log.info("Получение автомобиля с id:{}", id);
        Optional<Auto> optional = autoRepository.findById(id);
        if(optional.isPresent()){
            Auto auto = optional.get();
            log.info("Автомобиль получен с id:{}", id);
            return auto;
        }else
            throw new EntityNotFoundException("Автомобиль не найден");
    }

    @Override
    public void save(Auto auto) {
        log.info("Создание автомобиля");
        autoRepository.save(auto);
        log.info("Автомобиль создан и записан в базу");
    }

    @Override
    public void deleteAuto(Long id) {
        log.info("Удаление автомобиля с id:{}", id);
        autoRepository.deleteById(id);
        log.info("Автомобиль удалён с id:{}", id);
    }

    @Override
    public void update(Auto auto) {
        log.info("Обновление данных об автомобиле с id: {} ", auto.getId());
        autoRepository.save(auto);
        log.info("Данные обновлены");
    }
}
