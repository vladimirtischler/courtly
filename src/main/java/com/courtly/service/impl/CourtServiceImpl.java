package com.courtly.service.impl;

import com.courtly.dao.CourtDao;
import com.courtly.entity.Court;
import com.courtly.service.CourtService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtServiceImpl implements CourtService {

    private final CourtDao courtDao;

    public CourtServiceImpl(CourtDao courtDao) {
        this.courtDao = courtDao;
    }


    @Override
    @Transactional
    public void save(Court court) {
        courtDao.save(court);
    }

    @Override
    public void update(Court court) {
        courtDao.update(court);
    }

    @Override
    public Court findById(Long id) {
        return courtDao.findById(id);
    }

    @Override
    public List<Court> findAll() {
        return courtDao.findAll();
    }

    @Override
    public void delete(Long id) {
        Court court = courtDao.findById(id);
        if (court == null){
            return;
        }
        courtDao.delete(court);
    }
}
