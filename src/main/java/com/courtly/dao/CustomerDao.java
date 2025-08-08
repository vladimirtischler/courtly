package com.courtly.dao;

import com.courtly.entity.Customer;
import com.courtly.entity.SurfaceType;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends BaseDao<Customer>{
    public CustomerDao(){
        super(Customer.class);
    }

    public Customer findByPhoneNumber(String phoneNumber){
        try {
            return super.em.createNamedQuery(Customer.FIND_BY_PHONE_NUMBER, Customer.class).setParameter("phoneNumber", phoneNumber).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
