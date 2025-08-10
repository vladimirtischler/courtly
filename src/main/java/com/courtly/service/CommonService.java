package com.courtly.service;

import com.courtly.dto.BaseDto;
import com.courtly.entity.BaseEntity;

import java.util.List;

public interface CommonService<D extends BaseDto, E extends BaseEntity> {
    void save(D dto);
    void update(D dto, Long id);
    D findById(Long id);
    List<D> getAll();
    void delete(Long id);
}
