package interview.mendel.challenge.persistance;

import interview.mendel.challenge.exceptions.ParentTransactionNotFoundException;
import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.models.TransactionDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository txRepo;

    private List<Transaction> txs;

    @BeforeEach
    void setUp() {
        txs = new ArrayList<>();

        // Tree scenario
        Transaction parent = new Transaction(1L,"deposit", 100.0);
        Transaction child1 = new Transaction(2L,"withdrawal", 50.0);
        Transaction child2 = new Transaction(3L,"deposit", 30.0);

        child1.setParentId(1L);
        child2.setParentId(1L);

        txs.add(parent);
        txs.add(child1);
        txs.add(child2);

        // Cycle scenario
        Transaction parent2 = new Transaction(4L, "deposit", 100.0);
        Transaction child3 = new Transaction(5L, "withdrawal", 50.0);
        Transaction child4 = new Transaction(6L, "withdrawal", 60.0);

        child3.setParentId(parent2.getId());
        child4.setParentId(child3.getId());
        parent2.setParentId(child4.getId());

        txs.add(parent2);
        txs.add(child3);
        txs.add(child4);

        txRepo.setTxs(txs);
    }

    @AfterEach
    void tearDown() {
        txs.clear();
    }

    @Test
    @DisplayName("Should find transaction by ID")
    void testFindById() {
        Optional<Transaction> found = txRepo.findById(txs.get(0).getId());
        assertTrue(found.isPresent());
        assertEquals("deposit", found.get().getType());
        assertThat(found.get().getAmount()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Should not find transaction by ID")
    void testFindByIdFail() {
        Optional<Transaction> found = txRepo.findById(1000L);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should find transactions by type")
    void testFindByType() {
        List<Transaction> deposits = txRepo.findByType("deposit");
        assertEquals(3, deposits.size()); // parent and child2
    }

    @Test
    @DisplayName("Should find no transactions when type does not exist")
    void testFindByTypeFail() {
        List<Transaction> deposits = txRepo.findByType("trade");
        assertTrue(deposits.isEmpty());
    }

    @Test
    @DisplayName("Should find the sum of transitive transactions")
    void testTransitiveTransactionsAmountSum() {
        Optional<Double> found = txRepo.findTransitiveTransactionSum(txs.get(0).getId());

        assertTrue(found.isPresent());
        assertEquals(180.0, found.get());
    }

    @Test
    @DisplayName("Should find the sum of transitive transactions in a cycle")
    void testTransitiveTransactionsAmountSumInCycle() {
        Optional<Double> found = txRepo.findTransitiveTransactionSum(txs.get(3).getId());

        assertTrue(found.isPresent());
        assertEquals(210.0, found.get());
    }

}