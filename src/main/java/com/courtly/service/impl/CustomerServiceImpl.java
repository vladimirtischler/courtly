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

    public CustomerServiceImpl(CustomerDao dao, CustomerMapper customerMapper) {
        super(dao, customerMapper);
        this.customerDao = dao;
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null){
            return null;
        }
        return customerDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void validate(Customer entity) {

    }
}
