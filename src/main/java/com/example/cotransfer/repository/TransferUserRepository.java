package com.example.cotransfer.repository;



import com.example.cotransfer.model.TransferUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface TransferUserRepository extends JpaRepository<TransferUser, Long> {

    List<TransferUser> findAllByUserIdentificationNumber(Long userIdentificationNumber);
}
