package com.example.cotransfer.controller;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.User;
import com.example.cotransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/allUsers")
    private ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/allTransfers")
    private ResponseEntity<?> getAllUserTransfers(@RequestHeader(name = "id") Long id){
        Set<Transfer> allUserTransfers = userService.getAllUserTransfers(id);
        return ResponseEntity.ok(allUserTransfers);
    }

    @PostMapping("/create-user/{id}")
    private ResponseEntity<?> createUser(@PathVariable("id") Long id,
                                         @RequestBody String user){
        userService.createUser(id, user);
        return ResponseEntity.ok("Пользователь создан");
    }
}
