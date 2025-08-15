package com.courtly.service.impl;

import com.courtly.dao.BaseDao;
import com.courtly.dto.BaseDto;
import com.courtly.entity.BaseEntity;
import com.courtly.mapper.AbstractMapper;
import com.courtly.service.CommonService;

import java.util.List;

public abstract class AbstractService<E extends BaseEntity, D extends BaseDao<E>,
        T extends BaseDto, M extends AbstractMapper<E, T>> implements CommonService<T, E> {

    protected D dao;
    protected M mapper;

    public AbstractService(D dao, M mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public void save(T dto) {
        E entity = mapper.toEntity(dto);
        dao.save(entity);
    }

    @Override
    public void update(T dto, Long id) {
        E entity = dao.findById(id);
        if (entity == null){
            String nameOfEntity = dto.getClass().getSimpleName().replace("Dto", "");
            throw new IllegalArgumentException(nameOfEntity+" with id "+id+" not " +
                                                       "found");
        }
        mapper.update(entity, dto);
        dao.update(entity);
    }

    @Override
    public T findById(Long id) {
        E entity = dao.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public List<T> getAll() {
        List<E> entities = dao.findAll();
        return mapper.toDtos(entities);
    }

    @Override
    public void delete(Long id) {
        E entity = dao.findById(id);
        if (entity == null){
            throw new IllegalArgumentException("Entity with id "+id+" not found");
        }
        dao.delete(entity);
    }
}
