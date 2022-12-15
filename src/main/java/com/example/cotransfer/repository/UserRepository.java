package com.example.cotransfer.repository;

import com.example.cotransfer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
