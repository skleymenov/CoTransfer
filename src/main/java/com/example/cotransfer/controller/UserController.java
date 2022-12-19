package com.example.cotransfer.controller;

import com.example.cotransfer.model.User;
import com.example.cotransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/create-user/{id}")
    private ResponseEntity<?> createUser(@PathVariable("id") Long id,
                                         @RequestBody String user){
        userService.createUser(id, user);
        return ResponseEntity.ok("Пользователь создан");
    }
}
