package com.fitness.dao;

import com.fitness.config.DatabaseConfig;
import com.fitness.dao.impl.SubscriptionDaoImpl;
import com.fitness.dao.interfaces.SubscriptionDao;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class, SubscriptionDaoImpl.class})
@Transactional
public class SubscriptionDaoImplTest {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private Subscription subscription1;
    private Subscription subscription2;

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

        subscription1 = new Subscription();
        subscription1.setSubscriptionNumber("SUB-123456");
        subscription1.setPaid(true);
        subscription1.setType(SubscriptionType.MONTH);
        subscription1.setEndDate(LocalDate.now().plusMonths(1));

        subscription2 = new Subscription();
        subscription2.setSubscriptionNumber("SUB-789123");
        subscription2.setPaid(true);
        subscription2.setType(SubscriptionType.MONTH);
        subscription2.setEndDate(LocalDate.now().plusMonths(1));
    }

    @Test
    void findById_ShouldReturnSubscription_WhenSubscriptionExists() {
        entityManager.persist(user1);
        subscription1.setUser(user1);
        entityManager.persist(subscription1);
        entityManager.flush();

        Optional<Subscription> result = subscriptionDao.findById(subscription1.getId());

        assertTrue(result.isPresent());
        assertEquals(subscription1.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenSubscriptionDoesNotExist() {
        Long nonExistentId = 999L;

        Optional<Subscription> result = subscriptionDao.findById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnSubscriptionList_WhenSubscriptionExist() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        subscription1.setUser(user1);
        subscription2.setUser(user2);
        entityManager.persist(subscription1);
        entityManager.persist(subscription2);

        entityManager.flush();

        List<Subscription> result = subscriptionDao.findAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(s -> s.getSubscriptionNumber().equals(subscription1.getSubscriptionNumber())));
        assertTrue(result.stream().anyMatch(s -> s.getSubscriptionNumber().equals(subscription2.getSubscriptionNumber())));
        assertTrue(result.contains(subscription1));
        assertTrue(result.contains(subscription2));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoSubscriptionsExist() {
        List<Subscription> result = subscriptionDao.findAll();

        assertNotNull(result);

        Long expectedCount = entityManager.createQuery("SELECT COUNT(s) FROM Subscription s", Long.class).getSingleResult();
        assertEquals(expectedCount, Long.valueOf(result.size()));
    }

    @Test
    void save_ShouldPersistSubscriptionInDatabase() {
        entityManager.persist(user1);
        entityManager.flush();
        subscription1.setUser(user1);
        subscriptionDao.save(subscription1);
        entityManager.flush();

        Subscription foundSubscription = entityManager.find(Subscription.class, subscription1.getId());

        assertNotNull(foundSubscription);
        assertEquals(subscription1.getSubscriptionNumber(), foundSubscription.getSubscriptionNumber());
    }

    @Test
    void update_ShouldUpdateSubscriptionInDatabase() {
        entityManager.persist(user1);
        subscription1.setUser(user1);
        entityManager.persist(subscription1);
        entityManager.flush();

        subscription1.setSubscriptionNumber("SUB-UPDATED-999");
        subscription1.setPaid(false);

        subscriptionDao.update(subscription1);
        entityManager.flush();
        entityManager.clear();

        Subscription updatedSubscription = entityManager.find(Subscription.class, user1.getId());

        assertNotNull(updatedSubscription);
        assertEquals("SUB-UPDATED-999", updatedSubscription.getSubscriptionNumber());
        assertFalse(updatedSubscription.isPaid());
        assertEquals(user1.getId(), updatedSubscription.getUser().getId());
    }

    @Test
    void delete_ShouldDeleteSubscriptionInDatabase() {
        entityManager.persist(user1);
        subscription1.setUser(user1);
        entityManager.persist(subscription1);
        entityManager.flush();

        subscriptionDao.delete(subscription1);
        entityManager.flush();
        entityManager.clear();

        Subscription deletedSubscription = entityManager.find(Subscription.class, subscription1.getId());

        assertNull(deletedSubscription);
    }

    @Test
    void hasActiveSubscription_ShouldReturnTrue_WhenSubscriptionActive() {
        entityManager.persist(user1);
        subscription1.setUser(user1);
        entityManager.persist(subscription1);
        entityManager.flush();

        assertTrue(subscriptionDao.hasActiveSubscription(user1.getId()));
    }

    @Test
    void hasActiveSubscription_ShouldReturnFalse_WhenSubscriptionNoActive() {
        Long nonExistentUserId = 999L;

        boolean result = subscriptionDao.hasActiveSubscription(nonExistentUserId);

        assertFalse(result);
    }

    @Test
    void hasActiveSubscription_ShouldReturnFalse_WhenSubscriptionIsExpired() {
        entityManager.persist(user1);

        subscription1.setUser(user1);
        subscription1.setPaid(true);
        subscription1.setEndDate(LocalDate.now().minusDays(5));

        entityManager.persist(subscription1);
        entityManager.flush();

        boolean result = subscriptionDao.hasActiveSubscription(user1.getId());

        assertFalse(result);
    }

    @Test
    void existsBySubscriptionNumber_ShouldReturnTrue_WhenSubscriptionExists() {
        entityManager.persist(user1);
        subscription1.setUser(user1);
        entityManager.persist(subscription1);
        entityManager.flush();

        assertTrue(subscriptionDao.existsBySubscriptionNumber(subscription1.getSubscriptionNumber()));
    }

    @Test
    void existsBySubscriptionNumber_ShouldReturnFalse_WhenSubscriptionDoesNotExists() {
        String nonExistentNumber = "BUS-000000";

        boolean result = subscriptionDao.existsBySubscriptionNumber(nonExistentNumber);

        assertFalse(result);
    }
}

