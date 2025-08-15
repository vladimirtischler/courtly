package com.courtly.service.impl;

import com.courtly.dao.CourtDao;
import com.courtly.dao.SurfaceTypeDao;
import com.courtly.dto.CourtDto;
import com.courtly.entity.Court;
import com.courtly.entity.SurfaceType;
import com.courtly.mapper.CourtMapper;
import com.courtly.mapper.SurfaceTypeMapper;
import com.courtly.service.CourtService;
import org.springframework.stereotype.Service;

@Service
public class CourtServiceImpl extends AbstractService<Court, CourtDao, CourtDto, CourtMapper> implements CourtService{

    private final SurfaceTypeDao surfaceTypeDao;

    public CourtServiceImpl(CourtDao courtDao, SurfaceTypeDao surfaceTypeDao, CourtMapper courtMapper) {
        super(courtDao, courtMapper);
        this.surfaceTypeDao = surfaceTypeDao;
    }

    @Override
    public void save(CourtDto dto) {
        Court court = super.mapper.toEntity(dto);
        SurfaceType surfaceType;
        String surfaceName = dto.getSurfaceType().getName();
        Long surfaceId = dto.getSurfaceType().getId();
        if (surfaceId != null){
            surfaceType = surfaceTypeDao.findById(surfaceId);
        } else if (surfaceName != null){
            surfaceType = surfaceTypeDao.findByName(surfaceName);
        } else {
            throw new IllegalArgumentException("Surface type is not defined by name or id");
        }

        if (surfaceType == null) {
            surfaceName = surfaceName == null ? "" : surfaceName;
            throw new IllegalArgumentException("Surface " + surfaceName + " with id "+surfaceId+ " not found");
        }

        court.setSurfaceType(surfaceType);
        super.dao.save(court);
    }

    @Override
    public void update(CourtDto dto, Long id) {
        Court court = super.dao.findById(id);
        if (court == null){
            throw new IllegalArgumentException("Court with id "+id+" not " +
                                                       "found");
        }
        super.mapper.update(court, dto);
        SurfaceType surfaceType;
        String surfaceName = dto.getSurfaceType().getName();
        Long surfaceId = dto.getSurfaceType().getId();
        if (surfaceId != null){
            surfaceType = surfaceTypeDao.findById(surfaceId);
        } else if (surfaceName != null){
            surfaceType = surfaceTypeDao.findByName(surfaceName);
        } else {
            throw new IllegalArgumentException("Surface type is not defined by name or id");
        }

        if (surfaceType == null) {
            surfaceName = surfaceName == null ? "" : surfaceName;
            throw new IllegalArgumentException("Surface "+ surfaceName + " with id "+surfaceId+ " not found");
        }

        court.setSurfaceType(surfaceType);
        super.dao.update(court);
    }
}
