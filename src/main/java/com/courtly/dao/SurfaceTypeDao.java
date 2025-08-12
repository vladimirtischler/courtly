package com.courtly.dao;

import com.courtly.entity.SurfaceType;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

@Repository
public class SurfaceTypeDao extends BaseDao<SurfaceType>{
    public SurfaceTypeDao() {
        super(SurfaceType.class);
    }

    public SurfaceType findByName(String name){
        try {
            return super.em.createNamedQuery(SurfaceType.FIND_BY_NAME, SurfaceType.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
