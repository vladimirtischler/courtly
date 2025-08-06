package com.courtly.mapper;

import com.courtly.dto.CustomerDto;
import com.courtly.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper extends AbstractMapper<Customer, CustomerDto>{
    public abstract Customer toEntity(CustomerDto dto);
    public abstract CustomerDto toDto(Customer entity);
    public abstract void update(@MappingTarget Customer entity, CustomerDto dto);
    public abstract List<Customer> toEntities(List<CustomerDto> dtos);
    public abstract List<CustomerDto> toDtos(List<Customer> entities);
}
