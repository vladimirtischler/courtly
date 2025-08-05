package com.courtly.service.impl;

import com.courtly.dao.CourtDao;
import com.courtly.dao.SurfaceTypeDao;
import com.courtly.entity.Court;
import com.courtly.entity.SurfaceType;
import com.courtly.service.CourtService;
import org.springframework.stereotype.Service;

@Service
public class CourtServiceImpl extends AbstractService<Court, CourtDao> implements CourtService{

    private SurfaceTypeDao surfaceTypeDao;

    public CourtServiceImpl(CourtDao courtDao, SurfaceTypeDao surfaceTypeDao) {
        super(courtDao);
        this.surfaceTypeDao = surfaceTypeDao;
    }

    @Override
    public void save(Court entity) {
        if (entity.getSurfaceType() == null){
            throw new IllegalArgumentException("Surface type is mandatory");
        }
        SurfaceType surfaceType = null;
        String surfaceName = entity.getSurfaceType().getName();
        Long surfaceId = entity.getSurfaceType().getId();
        if (surfaceName != null){
            surfaceType = surfaceTypeDao.findByName(surfaceName);
        } else if (entity.getSurfaceType().getId() != null){
            surfaceType = surfaceTypeDao.findById(surfaceId);
        }

        if (surfaceType == null) {
            throw new IllegalArgumentException("Surface "+ surfaceName + " with id "+surfaceId+ " not found");
        }

        entity.setSurfaceType(surfaceType);
        super.save(entity);
    }

    @Override
    public void update(Court entity) {
        if (entity.getSurfaceType() == null){
            throw new IllegalArgumentException("Surface type is mandatory");
        }
        SurfaceType surfaceType = null;
        String surfaceName = entity.getSurfaceType().getName();
        Long surfaceId = entity.getSurfaceType().getId();
        if (surfaceName != null){
            surfaceType = surfaceTypeDao.findByName(surfaceName);
        } else if (entity.getSurfaceType().getId() != null){
            surfaceType = surfaceTypeDao.findById(surfaceId);
        }

        if (surfaceType == null) {
            throw new IllegalArgumentException("Surface "+ surfaceName + " with id "+surfaceId+ " not found");
        }

        entity.setSurfaceType(surfaceType);
        super.update(entity);
    }

    @Override
    public void validate(Court entity) {

    }
}
