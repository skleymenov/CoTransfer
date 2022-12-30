package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.TransferUser;
import com.example.cotransfer.model.User;
import com.example.cotransfer.repository.TransferRepository;
import com.example.cotransfer.repository.TransferUserRepository;
import com.example.cotransfer.repository.UserRepository;
import com.example.cotransfer.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    private final TransferRepository transferRepository;

    private final TransferUserRepository transferUserRepository;

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

    @Override
    public void createUser(Long transferId, String user) {
        log.info("Создание пользователя");
        JSONObject jsonObject = new JSONObject(user);
        Optional<Transfer> transfer = transferRepository.findById(transferId);
        User newUser = new User();
        String name = jsonObject.getString("FCs");
        List<String> nameList = List.of(name.split(" "));
        newUser.setName(nameList.get(0));
        newUser.setLastName(nameList.get(1));
        newUser.setPatronymic(nameList.get(2));
        newUser.setArrivalDate(jsonObject.getString("arrivalDate"));
        newUser.setFlightNumber(jsonObject.getString("flightNumber"));
        newUser.setPhoneNumber(jsonObject.getString("phoneNumber"));
        newUser.setEmail(jsonObject.getString("email"));
        newUser.setTelegramLogin(jsonObject.getString("telegramLogin"));
        newUser.setTripComment(jsonObject.getString("tripComment"));
        newUser.setTransfer((List<Transfer>) transfer.get());
        userRepository.save(newUser);
        log.info("Пользователь создан");
    }

    @Override
    public Set<Transfer> getAllUserTransfers(Long id) {

        List<TransferUser> allTransferUser = transferUserRepository.findAllByUserIdentificationNumber(id);
        List<User> allUsers = userRepository.findAllByIdentificationNumber(id);
        Set<Transfer> allTransfers = new HashSet<>();

        for (TransferUser transferUser: allTransferUser) {
            allTransfers.add(transferUser.getTransferId());
            for (Transfer transfer: allTransfers) {
                transfer.setUsers(allUsers);
            }
        }
        return allTransfers;
    }


}
