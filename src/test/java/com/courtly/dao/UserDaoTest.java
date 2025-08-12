package com.courtly.dao;

import com.courtly.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<User> typedQuery;

    @InjectMocks
    private UserDao userDao;

    @Test
    void findByUsername_UserExists() {
        User expectedUser = new User();
        expectedUser.setUsername("john");

        when(entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("username", "john"))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenReturn(expectedUser);

        User result = userDao.findByUsername("john");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("john", result.getUsername());
        verify(entityManager).createNamedQuery(User.FIND_BY_USERNAME, User.class);
        verify(typedQuery).setParameter("username", "john");
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findByUsername_UserDoesNotExist() {
        when(entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("username", "unknown"))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenThrow(new NoResultException());

        User result = userDao.findByUsername("unknown");

        Assertions.assertNull(result);
        verify(entityManager).createNamedQuery(User.FIND_BY_USERNAME, User.class);
        verify(typedQuery).setParameter("username", "unknown");
        verify(typedQuery).getSingleResult();
    }
}
