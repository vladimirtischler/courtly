package com.courtly.service;

import com.courtly.entity.Customer;

public interface CustomerService extends CommonService<Customer>{
    Customer findByPhoneNumber(String phoneNumber);
}
