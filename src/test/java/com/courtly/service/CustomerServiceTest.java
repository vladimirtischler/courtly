package com.courtly.service;

import com.courtly.dao.CustomerDao;
import com.courtly.dto.CustomerDto;
import com.courtly.entity.Customer;
import com.courtly.mapper.CustomerMapper;
import com.courtly.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private CustomerMapper customerMapper;

    @Test
    public void findByPhoneNumber_NullPhoneNumber() {

        Assertions.assertNull(customerService.findByPhoneNumber(null));

        verify(customerDao, never()).findByPhoneNumber(any());
    }

    @Test
    public void findByPhoneNumber_CorrectPhoneNumber() {

        Customer customer = new Customer();
        customer.setFirstName("Jozef");
        customer.setLastName("Mrkvicka");
        customer.setPhoneNumber("0954632156");
        customer.setId(1L);
        customer.setDeleted(Boolean.FALSE);
        customer.setCreated(LocalDateTime.now());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Jozef");
        customerDto.setLastName("Mrkvicka");
        customerDto.setPhoneNumber("0954632156");
        customerDto.setId(1L);
        customerDto.setCreated(LocalDateTime.now());

        when(customerDao.findByPhoneNumber("0954632156")).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.findByPhoneNumber("0954632156");

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("0954632156", result.getPhoneNumber());

        verify(customerDao).findByPhoneNumber(any());
        verify(customerMapper).toDto(any());
    }
}
