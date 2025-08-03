package com.courtly.service;

import com.courtly.entity.Court;

import java.util.List;

public interface CourtService {
    void save(Court court);
    void update(Court court);
    Court findById(Long id);
    List<Court> findAll();
    void delete(Long id);
}
