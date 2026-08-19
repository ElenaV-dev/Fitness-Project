package com.fitness.dao;

import com.fitness.config.DatabaseConfig;
import com.fitness.dao.impl.WorkoutTypeDaoImpl;
import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
import com.fitness.model.TrainingRecord;
import com.fitness.model.User;
import com.fitness.model.UserRole;
import com.fitness.model.WorkoutType;
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
@ContextConfiguration(classes = {DatabaseConfig.class, WorkoutTypeDaoImpl.class})
@Transactional
public class WorkoutTypeDaoImplTest {

    @Autowired
    private WorkoutTypeDao workoutTypeDao;

    @PersistenceContext
    private EntityManager entityManager;

    private WorkoutType workoutType1;
    private WorkoutType workoutType2;
    private WorkoutType workoutType3;
    private User testUser;
    private TrainingRecord trainingRecord;

    @BeforeEach
    void setUp() {
        workoutType1 = new WorkoutType();
        workoutType1.setTitle("Yoga");

        workoutType2 = new WorkoutType();
        workoutType2.setTitle("Pilates");

        workoutType3 = new WorkoutType();
        workoutType3.setTitle("Crossfit");

        testUser = new User();
        testUser.setFirstName("Ivan");
        testUser.setLastName("Ivanov");
        testUser.setEmail("ivan.record@dao-test.com");
        testUser.setPassword("Secure123");
        testUser.setRole(UserRole.CLIENT);

        trainingRecord = new TrainingRecord();
        trainingRecord.setUser(testUser);
        trainingRecord.setWorkoutType(workoutType1);
    }

    @Test
    void findById_ShouldReturnWorkoutType_WhenWorkoutTypeExists() {
        entityManager.persist(workoutType1);
        entityManager.flush();

        Optional<WorkoutType> result = workoutTypeDao.findById(workoutType1.getId());

        assertTrue(result.isPresent());
        assertEquals(workoutType1.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenWorkoutTypeDoesNotExist() {
        Long nonExistentId = 999L;

        Optional<WorkoutType> result = workoutTypeDao.findById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnWorkoutTypeList_WhenWorkoutTypesExist() {
        entityManager.persist(workoutType1);
        entityManager.persist(workoutType2);

        entityManager.flush();

        List<WorkoutType> result = workoutTypeDao.findAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(w -> w.getTitle().equals(workoutType1.getTitle())));
        assertTrue(result.stream().anyMatch(w -> w.getTitle().equals(workoutType2.getTitle())));
        assertTrue(result.contains(workoutType1));
        assertTrue(result.contains(workoutType2));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoWorkoutTypesExist() {
        List<WorkoutType> result = workoutTypeDao.findAll();

        assertNotNull(result);

        Long expectedCount = entityManager.createQuery("SELECT COUNT(w) FROM WorkoutType w", Long.class).getSingleResult();
        assertEquals(expectedCount, Long.valueOf(result.size()));
    }

    @Test
    void save_ShouldPersistWorkoutTypeInDatabase() {
        workoutTypeDao.save(workoutType1);
        entityManager.flush();

        WorkoutType foundWorkoutType = entityManager.find(WorkoutType.class, workoutType1.getId());

        assertNotNull(foundWorkoutType);
        assertEquals(workoutType1.getTitle(), foundWorkoutType.getTitle());
    }

    @Test
    void update_ShouldUpdateWorkoutTypeInDatabase() {
        entityManager.persist(workoutType1);
        entityManager.flush();

        workoutType1.setTitle("Zumba");

        workoutTypeDao.update(workoutType1);
        entityManager.flush();
        entityManager.clear();

        WorkoutType updatedWorkoutType = entityManager.find(WorkoutType.class, workoutType1.getId());

        assertNotNull(updatedWorkoutType);
        assertEquals(workoutType1.getTitle(), updatedWorkoutType.getTitle());
    }

    @Test
    void delete_ShouldDeleteWorkoutTypeInDatabase() {
        entityManager.persist(workoutType1);
        entityManager.flush();

        workoutTypeDao.delete(workoutType1);
        entityManager.flush();
        entityManager.clear();

        WorkoutType deletedWorkoutType = entityManager.find(WorkoutType.class, workoutType1.getId());

        assertNull(deletedWorkoutType);
    }

    @Test
    void existsByTitle_ShouldReturnTrue_WhenTitleExists() {
        entityManager.persist(workoutType1);
        entityManager.flush();

        assertTrue(workoutTypeDao.existsByTitle(workoutType1.getTitle()));
    }

    @Test
    void existsByTitle_ShouldReturnFalse_WhenTitleDoesNotExists() {
        String nonExistentTitle = "not found title";

        boolean result = workoutTypeDao.existsByTitle(nonExistentTitle);

        assertFalse(result);
    }

    @Test
    void existsByTitleAndIdNot_ShouldReturnTrue_WhenTitleIsTakenByAnotherId() {
        entityManager.persist(workoutType1);
        entityManager.persist(workoutType2);
        entityManager.flush();

        boolean result = workoutTypeDao.existsByTitleAndIdNot(workoutType1.getTitle(), workoutType2.getId());

        assertTrue(result);
    }

    @Test
    void existsByTitleAndIdNot_ShouldReturnFalse_WhenTitleBelongsToSameId() {
        entityManager.persist(workoutType1);
        entityManager.flush();

        boolean result = workoutTypeDao.existsByTitleAndIdNot(workoutType1.getTitle(), workoutType1.getId());

        assertFalse(result);
    }

    @Test
    void findWorkoutTypesWithPeopleCount_ShouldReturnWorkoutTypesWithCorrectCount() {
        entityManager.persist(workoutType1);
        entityManager.persist(testUser);
        entityManager.persist(trainingRecord);
        entityManager.flush();
        entityManager.clear();

        List<TrainerWorkoutTypeDto> result = workoutTypeDao.findWorkoutTypesWithPeopleCount();

        assertNotNull(result);
        assertFalse(result.isEmpty());

        TrainerWorkoutTypeDto yogaDto = result.stream()
                .filter(dto -> dto.getId().equals(workoutType1.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("DTO для тренировки Yoga не найдено в списке"));

        assertEquals(workoutType1.getTitle(), yogaDto.getTitle());
        assertEquals(1L, yogaDto.getCountPeople());
    }

    @Test
    void findWorkoutTypesWithPeopleCount_ShouldReturnEmptyList_WhenNoWorkoutTypesExist() {
        List<TrainerWorkoutTypeDto> result = workoutTypeDao.findWorkoutTypesWithPeopleCount();

        assertNotNull(result);

        Long expectedCount = entityManager.createQuery("SELECT COUNT(w) FROM WorkoutType w", Long.class).getSingleResult();

        assertEquals(expectedCount, Long.valueOf(result.size()));
    }

    @Test
    void findPage_ShouldReturnCorrectAmountOfItemsAndApplyOffset() {
        WorkoutType workoutType3 = new WorkoutType();
        workoutType3.setTitle("Crossfit");
        entityManager.persist(workoutType1);
        entityManager.persist(workoutType2);
        entityManager.persist(workoutType3);
        entityManager.flush();
        entityManager.clear();

        int pageSize = 2;

        List<WorkoutType> firstPageResult = workoutTypeDao.findPage(1, pageSize);
        List<WorkoutType> secondPageResult = workoutTypeDao.findPage(2, pageSize);

        assertNotNull(firstPageResult);
        assertEquals(2, firstPageResult.size());

        assertNotNull(secondPageResult);
        assertTrue(secondPageResult.size() >= 1);
        assertNotEquals(firstPageResult.get(0).getId(), secondPageResult.get(0).getId());
    }

    @Test
    void count_ShouldReturnTotalAmountOfWorkoutTypesInDatabase() {
        entityManager.persist(workoutType1);
        entityManager.flush();

        Long resultCount = workoutTypeDao.count();

        Long realCountInDb = entityManager.createQuery("SELECT COUNT(w) FROM WorkoutType w", Long.class).getSingleResult();

        assertNotNull(resultCount);
        assertEquals(realCountInDb, resultCount);
    }
}
