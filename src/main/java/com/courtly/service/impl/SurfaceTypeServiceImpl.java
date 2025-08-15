package com.courtly.service.impl;

import com.courtly.dao.SurfaceTypeDao;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.entity.Court;
import com.courtly.entity.SurfaceType;
import com.courtly.mapper.SurfaceTypeMapper;
import com.courtly.service.SurfaceTypeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SurfaceTypeServiceImpl extends AbstractService<SurfaceType, SurfaceTypeDao, SurfaceTypeDto, SurfaceTypeMapper> implements SurfaceTypeService {

    public SurfaceTypeServiceImpl(SurfaceTypeDao surfaceTypeDao, SurfaceTypeMapper surfaceTypeMapper) {
        super(surfaceTypeDao, surfaceTypeMapper);
    }

    @Override
    public void save(SurfaceTypeDto dto) {
        SurfaceType surfaceType = super.dao.findByName(dto.getName());
        if (surfaceType != null){
            throw new IllegalArgumentException("Surface with name " + dto.getName() + " already exists");
        }
        super.save(dto);
    }

    @Override
    public void update(SurfaceTypeDto dto, Long id) {
        SurfaceType surfaceType = super.dao.findByName(dto.getName());
        if (surfaceType != null && !surfaceType.getId().equals(id)){
            throw new IllegalArgumentException("Surface with name " + dto.getName() + " already exists");
        }
        super.update(dto, id);
    }
}
