package com.courtly.dao;

import com.courtly.entity.User;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Repository
public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username){
        try {
            return super.em.createNamedQuery(User.FIND_BY_USERNAME, User.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
