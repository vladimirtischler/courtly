package com.courtly.dao;

import com.courtly.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<E extends BaseEntity> {

    private Class<E> entityClass;

    @PersistenceContext
    protected EntityManager em;

    protected BaseDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public void save(E entity){
        em.persist(entity);
    }

    @Transactional
    public void update(E entity){
        em.merge(entity);
    }

    public E findById(Long id){
        return em.find(entityClass, id);
    }

    public List<E> findAll(){
        String sqlCommand = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.deleted=FALSE";
        return em.createQuery(sqlCommand, entityClass).getResultList();
    }

    @Transactional
    public void delete(E entity){
        entity.setDeleted(Boolean.TRUE);
        em.merge(entity);
    }
}
