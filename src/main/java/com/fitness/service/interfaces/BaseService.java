package com.fitness.service.interfaces;

import com.fitness.exception.EntityNotFoundException;

import java.util.List;

/**
 * Generic service interface that defines basic CRUD operations.
 *
 * @param <RESPONSE> response DTO type
 * @param <CREATE>   create DTO type
 * @param <UPDATE>   update DTO type
 * @param <ID>       entity identifier type
 */
public interface BaseService<RESPONSE, CREATE, UPDATE, ID> {

    /**
     * Finds an entity by its identifier.
     *
     * @param id entity identifier.
     * @return found entity data
     * @throws EntityNotFoundException if the entity is not found
     */
    RESPONSE findById(ID id);

    /**
     * Returns all entities.
     *
     * @return list of entities
     */
    List<RESPONSE> findAll();

    /**
     * Saves a new entity
     *
     * @param dto data required to create the entity
     */
    void save(CREATE dto);

    /**
     * Updates an existing entity.
     *
     * @param dto data required to update the entity
     * @throws EntityNotFoundException if the entity does not exist
     */
    void update(UPDATE dto);

    /**
     * Deletes an entity by its identifier.
     *
     * @param id entity identifier
     * @throws EntityNotFoundException if the entity is not found
     */
    void deleteById(ID id);
}
