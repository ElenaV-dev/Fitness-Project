package com.fitness.dao;

import com.fitness.config.DatabaseConfig;
import com.fitness.dao.impl.TrainingRecordDaoImpl;
import com.fitness.dao.interfaces.TrainingRecordDao;
import com.fitness.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class, TrainingRecordDaoImpl.class})
@Transactional
public class TrainingRecordDaoImplTest {

    @Autowired
    private TrainingRecordDao trainingRecordDao;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private WorkoutType workoutType1;
    private WorkoutType workoutType2;
    private TrainingRecord trainingRecord1;
    private TrainingRecord trainingRecord2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setFirstName("Ivan");
        user1.setLastName("Ivanov");
        user1.setEmail("ivan@dao-test.com");
        user1.setPassword("Secure123");
        user1.setRole(UserRole.CLIENT);

        user2 = new User();
        user2.setFirstName("Petr");
        user2.setLastName("Petrov");
        user2.setEmail("petr@dao-test.com");
        user2.setPassword("Secure123");
        user2.setRole(UserRole.CLIENT);

        workoutType1 = new WorkoutType();
        workoutType1.setTitle("Yoga");

        workoutType2 = new WorkoutType();
        workoutType2.setTitle("Pilates");

        trainingRecord1 = new TrainingRecord();
        trainingRecord1.setUser(user1);
        trainingRecord1.setWorkoutType(workoutType1);

        trainingRecord2 = new TrainingRecord();
        trainingRecord2.setUser(user2);
        trainingRecord2.setWorkoutType(workoutType2);
    }

    @Test
    void findById_ShouldReturnTrainingRecord_WhenTrainingRecordExists() {
        entityManager.persist(user1);
        entityManager.persist(workoutType1);
        entityManager.persist(trainingRecord1);
        entityManager.flush();

        Optional<TrainingRecord> result = trainingRecordDao.findById(trainingRecord1.getId());

        assertTrue(result.isPresent());
        assertEquals(trainingRecord1.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenTrainingRecordDoesNotExist() {
        Long nonExistentId = 999L;

        Optional<TrainingRecord> result = trainingRecordDao.findById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnTrainingRecordList_WhenTrainingRecordsExist() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(workoutType1);
        entityManager.persist(workoutType2);
        entityManager.persist(trainingRecord1);
        entityManager.persist(trainingRecord2);
        entityManager.flush();

        List<TrainingRecord> result = trainingRecordDao.findAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(t -> t.getUser().equals(trainingRecord1.getUser())));
        assertTrue(result.stream().anyMatch(t -> t.getUser().equals(trainingRecord2.getUser())));
        assertTrue(result.contains(trainingRecord1));
        assertTrue(result.contains(trainingRecord2));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoTrainingRecordsExist() {
        List<TrainingRecord> result = trainingRecordDao.findAll();

        assertNotNull(result);

        Long expectedCount = entityManager.createQuery("SELECT COUNT(t) FROM TrainingRecord t", Long.class).getSingleResult();
        assertEquals(expectedCount, Long.valueOf(result.size()));
    }

    @Test
    void save_ShouldPersistTrainingRecordInDatabase() {
        entityManager.persist(user1);
        entityManager.persist(workoutType1);
        entityManager.flush();

        trainingRecordDao.save(trainingRecord1);
        entityManager.flush();

        TrainingRecord foundTrainingRecord = entityManager.find(TrainingRecord.class, trainingRecord1.getId());

        assertNotNull(foundTrainingRecord);
        assertEquals(trainingRecord1.getId(), foundTrainingRecord.getId());
    }

    @Test
    void update_ShouldUpdateTrainingRecordInDatabase() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(workoutType1);
        entityManager.persist(workoutType2);
        entityManager.persist(trainingRecord1);
        entityManager.flush();

        trainingRecord1.setUser(user2);

        trainingRecordDao.update(trainingRecord1);
        entityManager.flush();
        entityManager.clear();

        TrainingRecord updatedTrainingRecord = entityManager.find(TrainingRecord.class, trainingRecord1.getId());

        assertNotNull(updatedTrainingRecord);
        assertEquals(user2.getId(), updatedTrainingRecord.getUser().getId());
    }

    @Test
    void delete_ShouldDeleteTrainingRecordInDatabase() {
        entityManager.persist(user1);
        entityManager.persist(workoutType1);
        entityManager.persist(trainingRecord1);
        entityManager.flush();

        trainingRecordDao.delete(trainingRecord1);
        entityManager.flush();
        entityManager.clear();

        TrainingRecord deletedTrainingRecord = entityManager.find(TrainingRecord.class, trainingRecord1.getId());

        assertNull(deletedTrainingRecord);
    }

    @Test
    void existsByUserIdAndWorkoutTypeId_ShouldReturnTrue_WhenTrainingRecordExists() {
        entityManager.persist(user1);
        entityManager.persist(workoutType1);
        entityManager.persist(trainingRecord1);
        entityManager.flush();

        boolean result = trainingRecordDao.existsByUserIdAndWorkoutTypeId(user1.getId(), workoutType1.getId());

        assertTrue(result);
    }

    @Test
    void existsByUserIdAndWorkoutTypeId_ShouldReturnFalse_WhenTrainingRecordDoesNotExist() {
        entityManager.persist(user1);
        entityManager.persist(workoutType1);
        entityManager.flush();

        boolean result = trainingRecordDao.existsByUserIdAndWorkoutTypeId(user1.getId(), workoutType1.getId());

        assertFalse(result);
    }

    @Test
    void findAllRecordsForUser_ShouldReturnRecordsOnlyForTargetUser() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(workoutType1);
        entityManager.persist(workoutType2);
        entityManager.persist(trainingRecord1);
        entityManager.persist(trainingRecord2);
        entityManager.flush();
        entityManager.clear();

        List<TrainingRecord> result = trainingRecordDao.findAllForUserId(user1.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user1.getId(), result.get(0).getUser().getId());
    }

    @Test
    void findAllRecordsForUser_ShouldReturnEmptyList_WhenUserHasNoTrainingRecords() {
        entityManager.persist(user1);
        entityManager.flush();

        List<TrainingRecord> result = trainingRecordDao.findAllForUserId(user1.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
} 
