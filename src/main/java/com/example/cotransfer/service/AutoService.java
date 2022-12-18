package com.example.cotransfer.service;

import com.example.cotransfer.model.Auto;

import java.util.List;

public interface AutoService {

    List<Auto> getAllAuto();

    Auto getAuto(Long id);

    void save(Auto auto);

    void deleteAuto(Long id);

    void update(Auto auto);
}
