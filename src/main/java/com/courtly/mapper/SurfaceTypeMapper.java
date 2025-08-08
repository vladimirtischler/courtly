package com.courtly.mapper;

import com.courtly.dto.BaseDto;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.entity.BaseEntity;
import com.courtly.entity.SurfaceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SurfaceTypeMapper extends AbstractMapper<SurfaceType, SurfaceTypeDto>{
    public abstract SurfaceType toEntity(SurfaceTypeDto dto);
    public abstract SurfaceTypeDto toDto(SurfaceType entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    public abstract void update(@MappingTarget SurfaceType entity, SurfaceTypeDto dto);
    public abstract List<SurfaceTypeDto> toDtos(List<SurfaceType> entities);
}