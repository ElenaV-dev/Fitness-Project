package com.fitness.dao.interfaces;

import com.fitness.model.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<User> findByEmail(String email);

}
