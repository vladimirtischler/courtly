package com.courtly.service.impl;

import com.courtly.dao.BaseDao;
import com.courtly.entity.BaseEntity;
import com.courtly.service.CommonService;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class AbstractService<E extends BaseEntity, D extends BaseDao<E>> implements CommonService<E> {
    private D dao;

    public AbstractService(D dao) {
        this.dao = dao;
    }

    @Override
    public void save(E entity) {
        dao.save(entity);
    }

    @Override
    public void update(E entity) {
        dao.update(entity);
    }

    @Override
    public E findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<E> findAll() {
        return dao.findAll();
    }

    @Override
    public void delete(Long id) {
        E entity = dao.findById(id);
        if (entity == null){
            return;
        }
        dao.delete(entity);
    }
}
