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
    private final CourtMapper courtMapper;
    private final CourtDao courtDao;

    public CourtServiceImpl(CourtDao courtDao, SurfaceTypeDao surfaceTypeDao, CourtMapper courtMapper) {
        super(courtDao, courtMapper);
        this.surfaceTypeDao = surfaceTypeDao;
        this.courtMapper = courtMapper;
        this.courtDao = courtDao;
    }

    @Override
    public void save(CourtDto dto) {
        Court court = courtMapper.toEntity(dto);
        SurfaceType surfaceType = null;
        String surfaceName = dto.getSurfaceType().getName();
        Long surfaceId = dto.getSurfaceType().getId();
        if (surfaceId != null){
            surfaceType = surfaceTypeDao.findById(surfaceId);
        } else if (surfaceName != null){
            surfaceType = surfaceTypeDao.findByName(surfaceName);
        }

        if (surfaceType == null) {
            surfaceName = surfaceName == null ? "" : surfaceName;
            throw new IllegalArgumentException("Surface " + surfaceName + " with id "+surfaceId+ " not found");
        }

        court.setSurfaceType(surfaceType);
        courtDao.save(court);
    }

    @Override
    public void update(CourtDto dto, Long id) {
        Court court = courtDao.findById(id);
        if (court == null){
            throw new IllegalArgumentException("Court with id "+id+" not " +
                                                       "found");
        }
        courtMapper.update(court, dto);
        SurfaceType surfaceType = null;
        String surfaceName = dto.getSurfaceType().getName();
        Long surfaceId = dto.getSurfaceType().getId();
        if (surfaceId != null){
            surfaceType = surfaceTypeDao.findById(surfaceId);
        } else if (surfaceName != null){
            surfaceType = surfaceTypeDao.findByName(surfaceName);
        }

        if (surfaceType == null) {
            surfaceName = surfaceName == null ? "" : surfaceName;
            throw new IllegalArgumentException("Surface "+ surfaceName + " with id "+surfaceId+ " not found");
        }

        court.setSurfaceType(surfaceType);
        courtDao.update(court);
    }

    @Override
    public void validate(Court entity) {

    }
}
