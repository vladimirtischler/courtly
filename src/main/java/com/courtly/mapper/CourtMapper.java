package com.courtly.mapper;

import com.courtly.dto.CourtDto;
import com.courtly.entity.Court;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CourtMapper extends AbstractMapper<Court, CourtDto> {
    public abstract Court toEntity(CourtDto dto);
    public abstract CourtDto toDto(Court entity);
    public abstract void update(@MappingTarget Court entity, CourtDto dto);
    public abstract List<Court> toEntities(List<CourtDto> dtos);
    public abstract List<CourtDto> toDtos(List<Court> entities);
}
