package interview.mendel.challenge.persistance;

import interview.mendel.challenge.models.Transaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
class TransactionDaoTest {

    @Autowired
    private TransactionDao transactionDao;

    private List<Transaction> txs;

    @BeforeEach
    void setUp() {

        txs = new ArrayList<>();

        Transaction parent = new Transaction("deposit", 100.0);
        Transaction child1 = new Transaction("withdrawal", 50.0);
        Transaction child2 = new Transaction("deposit", 30.0);

        child1.setParent(parent);
        child2.setParent(parent);

        transactionDao.save(parent);
        transactionDao.save(child1);
        transactionDao.save(child2);

        txs.add(parent);
        txs.add(child1);
        txs.add(child2);
    }

    @AfterEach
    void tearDown() {
        transactionDao.deleteAll();
    }

    @Test
    @DisplayName("Debe encontrar una transacción por ID")
    void testFindById() {
        Optional<Transaction> found = transactionDao.findById(txs.get(0).getId());
        assertTrue(found.isPresent());
        assertEquals("deposit", found.get().getType());
        assertThat(found.get().getAmount()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Debe fallar al buscar una transacción por ID inexistente")
    void testFindByIdFail() {
        Optional<Transaction> found = transactionDao.findById(1000L);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Debe encontrar transacciones por tipo")
    void testFindByType() {
        Pageable pageable = Pageable.unpaged();
        List<Transaction> deposits = transactionDao.findByType("deposit", pageable);
        assertEquals(2, deposits.size()); // parent and child2
    }

    @Test
    @DisplayName("Debe encontrar una lista vacía al buscar transacciones por tipo inexistente")
    void testFindByTypeFail() {
        Pageable pageable = Pageable.unpaged();
        List<Transaction> deposits = transactionDao.findByType("trade", pageable);
        assertTrue(deposits.isEmpty());
    }

    @Test
    @DisplayName("Debe obtener una transacción con sus hijos cargados")
    void testFindWithChildrenById() {
        Optional<Transaction> found = transactionDao.findWithChildrenById(txs.getFirst().getId());

        assertTrue(found.isPresent());
        assertEquals("deposit", found.get().getType());
    }

}