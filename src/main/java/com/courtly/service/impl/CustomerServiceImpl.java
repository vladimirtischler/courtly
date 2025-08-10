package com.courtly.service.impl;

import com.courtly.dao.CustomerDao;
import com.courtly.dto.CustomerDto;
import com.courtly.entity.Customer;
import com.courtly.mapper.CustomerMapper;
import com.courtly.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends AbstractService<Customer, CustomerDao, CustomerDto, CustomerMapper> implements CustomerService {
    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerDao dao, CustomerMapper customerMapper) {
        super(dao, customerMapper);
        this.customerDao = dao;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null){
            return null;
        }
        Customer customer = customerDao.findByPhoneNumber(phoneNumber);
        return customerMapper.toDto(customer);
    }
}
