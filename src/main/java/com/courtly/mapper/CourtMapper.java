package com.courtly.mapper;

import com.courtly.dto.CourtDto;
import com.courtly.entity.BaseEntity;
import com.courtly.entity.Court;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
        SurfaceTypeMapper.class
})
public abstract class CourtMapper extends AbstractMapper<Court, CourtDto> {
    public abstract Court toEntity(CourtDto dto);
    public abstract CourtDto toDto(Court entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "surfaceType", ignore = true)
    public abstract void update(@MappingTarget Court entity, CourtDto dto);
    public abstract List<CourtDto> toDtos(List<Court> entities);
}
