package com.courtly.dao;

import com.courtly.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerDaoTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Customer> typedQuery;

    @InjectMocks
    private CustomerDao customerDao;

    @Test
    void findByPhoneNumber_FoundCustomer() {
        Customer expected = new Customer();
        expected.setPhoneNumber("123456789");

        when(entityManager.createNamedQuery(Customer.FIND_BY_PHONE_NUMBER, Customer.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("phoneNumber", "123456789")).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expected);

        Customer result = customerDao.findByPhoneNumber("123456789");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void findByPhoneNumber_CatchNRE() {
        when(entityManager.createNamedQuery(Customer.FIND_BY_PHONE_NUMBER, Customer.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("phoneNumber", "000")).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new NoResultException());

        Customer result = customerDao.findByPhoneNumber("000");

        Assertions.assertNull(result);
    }
}
