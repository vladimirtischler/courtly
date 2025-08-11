package com.courtly.mapper;

import com.courtly.dto.AuthRequest;
import com.courtly.dto.CourtDto;
import com.courtly.dto.UserDto;
import com.courtly.entity.Court;
import com.courtly.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.control.MappingControl;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends AbstractMapper<User, UserDto> {
    public abstract User toEntity(UserDto dto);
    public abstract UserDto toDto(User entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    public abstract void update(@MappingTarget User entity, UserDto dto);
    public abstract List<UserDto> toDtos(List<User> entities);

    public abstract UserDto toDto(AuthRequest authRequest);
}
