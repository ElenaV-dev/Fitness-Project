package com.fitness.dao;

import com.fitness.config.DatabaseConfig;
import com.fitness.dao.impl.UserDaoImpl;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.model.User;
import com.fitness.model.UserRole;
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
@ContextConfiguration(classes = {DatabaseConfig.class, UserDaoImpl.class})
@Transactional
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    private User firstUser;
    private User secondUser;

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setFirstName("Ivan");
        firstUser.setLastName("Ivanov");
        firstUser.setEmail("ivan@dao-test.com");
        firstUser.setPassword("Secure123");
        firstUser.setRole(UserRole.CLIENT);

        secondUser = new User();
        secondUser.setFirstName("Petr");
        secondUser.setLastName("Petrov");
        secondUser.setEmail("petr@dao-test.com");
        secondUser.setPassword("Secure123");
        secondUser.setRole(UserRole.CLIENT);
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        entityManager.persist(firstUser);
        entityManager.flush();

        Optional<User> result = userDao.findById(firstUser.getId());

        assertTrue(result.isPresent());
        assertEquals(firstUser.getEmail(), result.get().getEmail());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenUserDoesNotExist() {
        Long nonExistentId = 999L;

        Optional<User> result = userDao.findById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnUserList_WhenUsersExist() {
        entityManager.persist(firstUser);
        entityManager.persist(secondUser);

        entityManager.flush();

        List<User> result = userDao.findAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(u -> u.getEmail().equals(firstUser.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getEmail().equals(secondUser.getEmail())));
        assertTrue(result.contains(firstUser));
        assertTrue(result.contains(secondUser));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoUsersExist() {
        List<User> result = userDao.findAll();

        assertNotNull(result);

        Long expectedCount = entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        assertEquals(expectedCount, Long.valueOf(result.size()));
    }

    @Test
    void save_ShouldPersistUserInDatabase() {
        userDao.save(firstUser);
        entityManager.flush();

        User foundUser = entityManager.find(User.class, firstUser.getId());

        assertNotNull(foundUser);
        assertEquals(firstUser.getFirstName(), foundUser.getFirstName());
    }

    @Test
    void update_ShouldUpdateUserInDatabase() {
        entityManager.persist(firstUser);
        entityManager.flush();

        firstUser.setFirstName("NewIvan");
        firstUser.setLastName("NewIvanov");

        userDao.update(firstUser);
        entityManager.flush();
        entityManager.clear();

        User updatedUser = entityManager.find(User.class, firstUser.getId());

        assertNotNull(updatedUser);
        assertEquals(firstUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(firstUser.getLastName(), updatedUser.getLastName());
    }

    @Test
    void delete_ShouldDeleteUserInDatabase() {
        entityManager.persist(firstUser);
        entityManager.flush();

        userDao.delete(firstUser);
        entityManager.flush();
        entityManager.clear();

        User deletedUser = entityManager.find(User.class, firstUser.getId());

        assertNull(deletedUser);
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        entityManager.persist(firstUser);
        entityManager.flush();

        assertTrue(userDao.existsByEmail(firstUser.getEmail()));
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenEmailDoesNotExists() {
        String nonExistentEmail = "notfound@mail.com";

        boolean result = userDao.existsByEmail(nonExistentEmail);

        assertFalse(result);
    }

    @Test
    void existsByEmailAndIdNot_ShouldReturnTrue_WhenEmailIsTakenByAnotherUser() {
        entityManager.persist(firstUser);
        entityManager.persist(secondUser);
        entityManager.flush();

        boolean result = userDao.existsByEmailAndIdNot(firstUser.getEmail(), secondUser.getId());

        assertTrue(result);
    }

    @Test
    void existsByEmailAndIdNot_ShouldReturnFalse_WhenEmailBelongsToSameUser() {
        entityManager.persist(firstUser);
        entityManager.flush();

        boolean result = userDao.existsByEmailAndIdNot(firstUser.getEmail(), firstUser.getId());

        assertFalse(result);
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {
        entityManager.persist(firstUser);
        entityManager.flush();

        Optional<User> result = userDao.findByEmail(firstUser.getEmail());

        assertTrue(result.isPresent());
        assertEquals(firstUser.getEmail(), result.get().getEmail());
    }

    @Test
    void findByEmail_ShouldReturnEmptyOptional_WhenUserDoesNotExist() {
        String nonExistentEmail = "notfound@mail.com";

        Optional<User> result = userDao.findByEmail(nonExistentEmail);

        assertFalse(result.isPresent());
    }
}
