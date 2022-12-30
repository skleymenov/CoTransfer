package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.TransferUser;
import com.example.cotransfer.model.User;
import com.example.cotransfer.repository.TransferRepository;
import com.example.cotransfer.repository.TransferUserRepository;
import com.example.cotransfer.repository.UserRepository;
import com.example.cotransfer.service.TransferService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImplementation implements TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final TransferUserRepository transferUserRepository;

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

    public ResponseEntity<?> createTransferFromAirport(String transfer, Long id) {
        log.info("Создание трансфера");

        JSONObject jsonObjectTr = new JSONObject(transfer);
        JSONObject jsonObject = jsonObjectTr.getJSONObject(("order"));

        Transfer newTransfer = new Transfer();

        if (jsonObject.getBoolean("pickYouUpFromAirPort")) {

            newTransfer.setTripDate(jsonObject.getString("transferDate") + jsonObject.getString("transferTime"));
            //newTransfer.setStartPlace(jsonObject.getString("start"));
            newTransfer.setEndPlace(jsonObject.getString("end"));
            newTransfer.setAdultsAmount(jsonObject.getInt("adults"));
            newTransfer.setChildrenAmount((jsonObject.getInt("childrenUnder5") + jsonObject.getInt("childrenAbove5")));

            JSONArray jsonArray = jsonObject.getJSONArray("passengers");

            for (int i = 0; i < jsonArray.length(); i++) {
                User newUser = new User();
                TransferUser transferUser = new TransferUser();
                JSONObject arrayJson = jsonArray.getJSONObject(i);
                String name = arrayJson.getString("fullName");

                newUser.setIdentificationNumber(id);
                newUser.setName(name);
                newUser.setArrivalDate(arrayJson.getString("departureDate") + " " + arrayJson.getString("departureTime"));
                //newUser.setFlightNumber(arrayJson.getString("flightNumber"));
                newUser.setPhoneNumber(String.valueOf(arrayJson.getLong("phoneNumber")));
                //newUser.setEmail(arrayJson.getString("email"));
                newUser.setPassport(arrayJson.getString("passportId"));
                newUser.setTelegramLogin(arrayJson.getString("telegramId"));
                newUser.setTripComment(arrayJson.getString("transferComment"));

                transferUser.setUserIdentificationNumber(id);
                transferUser.setTransferId(newTransfer);
                transferUser.setUserId(newUser);

                userRepository.save(newUser);
                transferRepository.save(newTransfer);
                transferUserRepository.save(transferUser);

                log.info("Пользователь создан");
            }

            String autoType = jsonObject.getString("carType");
            int passengers = (jsonObject.getInt("adults") + jsonObject.getInt("childrenUnder5") + jsonObject.getInt("childrenAbove5"));

            if (autoType.equals("Vito") && passengers > 0 && passengers < 8) {
                newTransfer.setAutoType(autoType);
                transferRepository.save(newTransfer);
                log.info("Трансфер создан");
                return ResponseEntity.ok("Трансфер создан, Вы выбрали вито");
            } else if (autoType.equals("sedan") && passengers > 0 && passengers < 4) {
                newTransfer.setAutoType(autoType);
                transferRepository.save(newTransfer);
                log.info("Трансфер создан");
                return ResponseEntity.ok("Трансфер создан, Вы выбрали Седан");
            }
        } else if(!(jsonObject.getBoolean("pickYouUpFromAirPort"))){
            newTransfer.setTripDate(jsonObject.getString("transferDate") + jsonObject.getString("transferTime"));
            newTransfer.setStartPlace(jsonObject.getString("start"));
            newTransfer.setEndPlace(jsonObject.getString("end"));
            newTransfer.setAdultsAmount((jsonObject.getInt("adults")));
            newTransfer.setChildrenAmount(jsonObject.getInt("childrenUnder5") + jsonObject.getInt("childrenAbove5"));

            JSONArray jsonArray = jsonObject.getJSONArray("passengers");

            for (int i = 0; i < jsonArray.length(); i++) {
                User newUser = new User();
                TransferUser transferUser = new TransferUser();
                JSONObject arrayJson = jsonArray.getJSONObject(i);
                String name = arrayJson.getString("fullName");

                newUser.setIdentificationNumber(id);
                newUser.setName(name);
                newUser.setArrivalDate(arrayJson.getString("departureDate") + " " + arrayJson.getString("departureTime"));
                //newUser.setFlightNumber(arrayJson.getString("flightNumber"));
                newUser.setPhoneNumber(String.valueOf(arrayJson.getLong("phoneNumber")));
                //newUser.setEmail(arrayJson.getString("email"));
                newUser.setPassport(arrayJson.getString("passportId"));
                newUser.setTelegramLogin(arrayJson.getString("telegramId"));
                newUser.setTripComment(arrayJson.getString("transferComment"));

                transferUser.setUserIdentificationNumber(id);
                transferUser.setTransferId(newTransfer);
                transferUser.setUserId(newUser);

                userRepository.save(newUser);
                transferRepository.save(newTransfer);
                transferUserRepository.save(transferUser);

                log.info("Пользователь создан");
            }

            String autoType = jsonObject.getString("carType");
            int passengers = (jsonObject.getInt("adults") + jsonObject.getInt("childrenUnder5") + jsonObject.getInt("childrenAbove5"));

            if (autoType.equals("Vito") && passengers > 0 && passengers < 8) {
                newTransfer.setAutoType(autoType);
                transferRepository.save(newTransfer);
                log.info("Трансфер создан");
                return ResponseEntity.ok("Трансфер создан, Вы выбрали вито");
            } else if (autoType.equals("sedan") && passengers > 0 && passengers < 4) {
                newTransfer.setAutoType(autoType);
                transferRepository.save(newTransfer);
                log.info("Трансфер создан");
                return ResponseEntity.ok("Трансфер создан, Вы выбрали Седан");
            }
        }
        return  ResponseEntity.ok("Трансфер создан");
    }


    @Override
    public ResponseEntity<?> updateTransfer(String transfer, Long id) {
        log.info("Обновление трансфера с id:{}", id);
        List<TransferUser> allTransferUser = transferUserRepository.findAllByUserIdentificationNumber(id);

        List<Transfer> allTransfers = new ArrayList<>();

        for (TransferUser transferUser : allTransferUser) {
            allTransfers.add(transferUser.getTransferId());
        }
        for (Transfer newTransfer : allTransfers) {
            JSONObject jsonObjectTr = new JSONObject(transfer);
            JSONObject jsonObject = jsonObjectTr.getJSONObject(("order"));

            newTransfer.setTripDate(jsonObject.getString("date"));
            newTransfer.setStartPlace(jsonObject.getString("start"));
            newTransfer.setEndPlace(jsonObject.getString("end"));
            newTransfer.setAdultsAmount(Integer.valueOf(jsonObject.getString("adults")));
            newTransfer.setChildrenAmount(Integer.valueOf(jsonObject.getString("childrenUnder5") + jsonObject.getInt("childrenAbove5")));

            JSONArray jsonArray = jsonObject.getJSONArray("passengers");


            List<User> userList = userRepository.findAllByIdentificationNumber(id);
            for (int i = 0; i < jsonArray.length(); i++) {
                User newUser = userList.get(i);
                JSONObject arrayJson = jsonArray.getJSONObject(i);
                String name = arrayJson.getString("fullName");
                List<String> nameList = List.of(name.split(" "));

                newUser.setIdentificationNumber(id);
                newUser.setName(nameList.get(0));
                newUser.setLastName(nameList.get(1));
                newUser.setPatronymic(nameList.get(2));
                newUser.setArrivalDate(arrayJson.getString("departureDate") + " " + arrayJson.getString("departureTime"));
                //newUser.setFlightNumber(arrayJson.getString("flightNumber"));
                newUser.setPhoneNumber(String.valueOf(arrayJson.getLong("phoneNumber")));
                //newUser.setEmail(arrayJson.getString("email"));
                newUser.setPassport(arrayJson.getString("passportId"));
                newUser.setTelegramLogin(arrayJson.getString("telegramId"));
                newUser.setTripComment(arrayJson.getString("transferComment"));
                System.out.println(newUser.getTelegramLogin());
                userRepository.save(newUser);
            }

            log.info("Пользователь создан");
            transferRepository.save(newTransfer);
            String autoType = jsonObject.getString("carType");
            int passengers = Integer.parseInt(jsonObject.getString("adults") + jsonObject.getString("childrenUnder5") + jsonObject.getString("childrenAbove5"));

            if (autoType.equals("Vito") && passengers > 0 && passengers < 8) {
                newTransfer.setAutoType(autoType);
                transferRepository.save(newTransfer);
                log.info("Трансфер создан");
                return ResponseEntity.ok("Трансфер создан, Вы выбрали вито");
            } else if (autoType.equals("sedan") && passengers > 0 && passengers < 4) {
                newTransfer.setAutoType(autoType);
                transferRepository.save(newTransfer);
                log.info("Трансфер создан");
                return ResponseEntity.ok("Трансфер создан, Вы выбрали Седан");
            } else
                return ResponseEntity.status(400).body("Слишком много пассажиров");
        }
        return ResponseEntity.ok("Трансфер обновлён");
    }
}
