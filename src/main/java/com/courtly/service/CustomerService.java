package com.courtly.service;

import com.courtly.dao.CustomerDao;
import com.courtly.dto.CustomerDto;
import com.courtly.entity.Customer;

public interface CustomerService extends CommonService<CustomerDto, Customer>{
    CustomerDto findByPhoneNumber(String phoneNumber);
}
