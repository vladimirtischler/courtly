package com.courtly.dao;

import com.courtly.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends BaseDao<Customer>{
    public CustomerDao(){
        super(Customer.class);
    }
}
