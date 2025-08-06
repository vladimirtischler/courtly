package com.courtly.mapper;

import com.courtly.dto.ReservationDto;
import com.courtly.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper extends AbstractMapper<Reservation, ReservationDto>{
    public abstract Reservation toEntity(ReservationDto dto);
    public abstract ReservationDto toDto(Reservation entity);
    public abstract void update(@MappingTarget Reservation entity, ReservationDto dto);
    public abstract List<Reservation> toEntities(List<ReservationDto> dtos);
    public abstract List<ReservationDto> toDtos(List<Reservation> entities);
}
