package com.courtly.mapper;

import com.courtly.dto.BaseDto;
import com.courtly.entity.BaseEntity;
import org.mapstruct.Mapper;

public abstract class AbstractMapper<E extends BaseEntity, D extends BaseDto> implements CommonMapper<E, D>{
}
