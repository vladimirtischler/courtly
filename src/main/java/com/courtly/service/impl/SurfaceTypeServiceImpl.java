package com.courtly.service.impl;

import com.courtly.dao.SurfaceTypeDao;
import com.courtly.entity.Court;
import com.courtly.entity.SurfaceType;
import com.courtly.service.SurfaceTypeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SurfaceTypeServiceImpl extends AbstractService<SurfaceType, SurfaceTypeDao> implements SurfaceTypeService {

    private final SurfaceTypeDao surfaceTypeDao;

    public SurfaceTypeServiceImpl(SurfaceTypeDao surfaceTypeDao) {
        super(surfaceTypeDao);
        this.surfaceTypeDao = surfaceTypeDao;
    }

    @Override
    public void save(SurfaceType entity) {
        SurfaceType surfaceType = surfaceTypeDao.findByName(entity.getName());
        if (surfaceType != null){
            throw new IllegalArgumentException("Surface with name " + entity.getName() + " already exists");
        }
        super.save(entity);
    }

    @Override
    public void validate(SurfaceType entity) {

    }
}
