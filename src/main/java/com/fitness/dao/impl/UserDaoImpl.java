package com.fitness.dao.impl;

import com.fitness.dao.interfaces.UserDao;
import com.fitness.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_USERS = "SELECT u FROM User u";
    private static final String SELECT_COUNT_USERS_BY_EMAIL = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
    private static final String SELECT_COUNT_USER_BY_EMAIL_AND_ID_NOT = "SELECT COUNT(u) FROM User u " +
            "WHERE u.email = :email AND u.id <> :id";
    private static final String SELECT_COUNT_USER_BY_ID = "SELECT COUNT(u) FROM User u WHERE u.id = :id";

    @Override
    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(SELECT_ALL_USERS, User.class);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);

        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public boolean existsByEmail(String email) {

        Long count = entityManager.createQuery(SELECT_COUNT_USERS_BY_EMAIL, Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        Long count = entityManager.createQuery(SELECT_COUNT_USER_BY_EMAIL_AND_ID_NOT, Long.class)
                .setParameter("email", email)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }
}
