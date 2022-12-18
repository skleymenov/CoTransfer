package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.User;
import com.example.cotransfer.repository.UserRepository;
import com.example.cotransfer.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Получение всех пользователей");
        List<User> allUsers = userRepository.findAll();
        log.info("Все пользователи получены");
        return allUsers;
    }

    @Override
    public User getUser(Long id) {
        log.info("Получение пользователя c token");
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            log.info("Получен пользователь");
            return user;
        } else throw new EntityNotFoundException("Пользователь не найден");
    }

    @Override
    public void save(User user) {
        log.info("Создание пользователя");
        userRepository.save(user);
        log.info("Пользователь создан");
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Удаление пользователя c id {}", id);
        userRepository.deleteById(id);
        log.info("Пользователь c id {} удален", id);
    }

    @Override
    public void update(User user) {
        log.info("Обновление пользователя с id {}", user.getId());
        userRepository.save(user);
        log.info("Пользователь с id {} обновлен", user.getId());
    }
}
