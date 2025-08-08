package com.courtly.mapper;

import com.courtly.dto.ReservationDto;
import com.courtly.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
        SurfaceTypeMapper.class,
        CustomerMapper.class,
        CourtMapper.class
})
public abstract class ReservationMapper extends AbstractMapper<Reservation, ReservationDto>{
    public abstract Reservation toEntity(ReservationDto dto);
    public abstract ReservationDto toDto(Reservation entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "court", ignore = true)
    public abstract void update(@MappingTarget Reservation entity, ReservationDto dto);
    public abstract List<ReservationDto> toDtos(List<Reservation> entities);
}
