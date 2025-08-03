package com.courtly.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<T> {

    private Class< T > entityClass;

    @PersistenceContext
    protected EntityManager em;

    protected BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity){
        em.persist(entity);
    }

    public void update(T entity){
        em.merge(entity);
    }

    public T findById(Long id){
        return em.find(entityClass, id);
    }

    public List<T> findAll(){
        String sqlCommand = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(sqlCommand, entityClass).getResultList();
    }
    public void delete(T entity){
        em.remove(entity);
    }
}
