package com.courtly.service;

import com.courtly.dto.CustomerDto;
import com.courtly.entity.Customer;

public interface CustomerService extends CommonService<CustomerDto, Customer>{
    Customer findByPhoneNumber(String phoneNumber);
}
