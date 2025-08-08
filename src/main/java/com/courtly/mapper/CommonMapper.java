package com.courtly.mapper;

import com.courtly.dto.BaseDto;
import com.courtly.entity.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface CommonMapper<E extends BaseEntity, D extends BaseDto> {
    E toEntity(D dto);
    D toDto(E entity);
    void update(E entity, D dto);
    List<D> toDtos(List<E> entities);
}
