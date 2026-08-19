package com.fitness.dao;

import com.fitness.config.DatabaseConfig;
import com.fitness.dao.impl.PayCardDaoImpl;
import com.fitness.dao.interfaces.PayCardDao;
import com.fitness.model.PayCard;
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
@ContextConfiguration(classes = {DatabaseConfig.class, PayCardDaoImpl.class})
@Transactional
public class PayCardDaoImplTest {

    @Autowired
    private PayCardDao payCardDao;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private PayCard payCard1;
    private PayCard payCard2;

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

        payCard1 = new PayCard();
        payCard1.setUser(user1);
        payCard1.setCardNumber("1234-5678-9123-4567");
        payCard1.isBound();

        payCard2 = new PayCard();
        payCard2.setUser(user2);
        payCard2.setCardNumber("7891-2345-6789-1234");
        payCard1.isBound();
    }

    @Test
    void findById_ShouldReturnPayCard_WhenPayCardExists() {
        entityManager.persist(user1);
        entityManager.persist(payCard1);
        entityManager.flush();

        Optional<PayCard> result = payCardDao.findById(payCard1.getId());

        assertTrue(result.isPresent());
        assertEquals(payCard1.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenPayCardDoesNotExist() {
        Long nonExistentId = 999L;

        Optional<PayCard> result = payCardDao.findById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnPayCardList_WhenPayCardsExist() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(payCard1);
        entityManager.persist(payCard2);
        entityManager.flush();

        List<PayCard> result = payCardDao.findAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(p -> p.getCardNumber().equals(payCard1.getCardNumber())));
        assertTrue(result.stream().anyMatch(p -> p.getCardNumber().equals(payCard2.getCardNumber())));
        assertTrue(result.contains(payCard1));
        assertTrue(result.contains(payCard2));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoPayCardsExist() {
        List<PayCard> result = payCardDao.findAll();

        assertNotNull(result);

        Long expectedCount = entityManager.createQuery("SELECT COUNT(p) FROM PayCard p", Long.class).getSingleResult();
        assertEquals(expectedCount, Long.valueOf(result.size()));
    }

    @Test
    void save_ShouldPersistPayCardInDatabase() {
        entityManager.persist(user1);
        entityManager.flush();

        payCardDao.save(payCard1);
        entityManager.flush();

        PayCard foundPayCard = entityManager.find(PayCard.class, payCard1.getId());

        assertNotNull(foundPayCard);
        assertEquals(payCard1.getCardNumber(), foundPayCard.getCardNumber());
    }

    @Test
    void update_ShouldUpdatePayCardInDatabase() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(payCard1);
        entityManager.flush();

        payCard1.setUser(user2);

        payCardDao.update(payCard1);
        entityManager.flush();
        entityManager.clear();

        PayCard updatedPayCard = entityManager.find(PayCard.class, payCard1.getId());

        assertNotNull(updatedPayCard);
        assertEquals(user2.getId(), updatedPayCard.getUser().getId());
    }

    @Test
    void delete_ShouldDeletePayCardInDatabase() {
        entityManager.persist(user1);
        entityManager.persist(payCard1);
        entityManager.flush();

        payCardDao.delete(payCard1);
        entityManager.flush();
        entityManager.clear();

        PayCard deletedPayCard = entityManager.find(PayCard.class, payCard1.getId());

        assertNull(deletedPayCard);
    }
}
