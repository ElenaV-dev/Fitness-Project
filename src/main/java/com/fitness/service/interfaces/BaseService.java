package com.fitness.service.interfaces;

import java.util.List;
import java.util.Optional;

public interface BaseService<RESPONSE, CREATE, UPDATE, ID> {

    RESPONSE findById(ID id);

    List<RESPONSE> findAll();

    void save(CREATE dto);

    void update(UPDATE dto);

    void deleteById(ID id);
}
