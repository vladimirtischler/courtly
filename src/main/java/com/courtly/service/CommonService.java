package com.courtly.service;

import com.courtly.entity.BaseEntity;

import java.util.List;

public interface CommonService<E extends BaseEntity> {
    void save(E entity);
    void update(E entity);
    E findById(Long id);
    List<E> findAll();
    void delete(Long id);
    void validate(E entity);
}
