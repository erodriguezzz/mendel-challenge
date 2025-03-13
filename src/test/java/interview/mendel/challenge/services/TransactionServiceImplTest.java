package interview.mendel.challenge.services;

import interview.mendel.challenge.exceptions.ParentTransactionNotFoundException;
import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.models.TransactionDto;
import interview.mendel.challenge.persistance.TransactionDao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionDao txRepo;

    @InjectMocks
    private TransactionServiceImpl txService;

    private Transaction pt;
    private Transaction ct1;
    private Transaction ct2;

    private Transaction pt2;
    private Transaction ct3;
    private Transaction ct4;

    @BeforeEach
    void setUp() {
        pt = new Transaction("deposit", 100.0);
        pt.setId(1L);
        ct1 = new Transaction("withdraw", 50.0);
        ct1.setId(2L);
        ct2 = new Transaction("withdraw", 60.0);
        ct2.setId(3L);

        ct2.setParent(pt);
        ct1.setParent(pt);

        pt.setChildren(List.of(ct1, ct2));

        pt2 = new Transaction("deposit", 100.0);
        pt2.setId(4L);
        ct3 = new Transaction("withdraw", 50.0);
        ct3.setId(5L);
        ct4 = new Transaction("withdraw", 60.0);
        ct4.setId(6L);

        ct3.setParent(pt2);
        ct4.setParent(ct3);
        pt2.setParent(ct4);

        ct3.setChildren(List.of(ct4));
        pt2.setChildren(List.of(ct3));
        ct4.setChildren(List.of(pt2));
    }

    @Test
    @DisplayName("Should update a bastard transaction without changing its parent")
    void shouldUpdateBastardTransactionSuccessfully() {
        when(txRepo.findById(1L)).thenReturn(Optional.of(pt));
        TransactionDto newTx = new TransactionDto("deposit", 200.0, null);

        Optional<Transaction> updatedTx = txService.updateTransaction(newTx, pt.getId());
        assertTrue(updatedTx.isPresent());
        assertEquals(200.0, updatedTx.get().getAmount());
        assertNull(updatedTx.get().getParent());
    }

    @Test
    @DisplayName("Should update a fathered transaction successfully")
    void shouldUpdateFatheredTransactionSuccessfully() {
        when(txRepo.findById(3L)).thenReturn(Optional.of(ct2));
        when(txRepo.findById(2L)).thenReturn(Optional.of(ct1));
        TransactionDto newTx = new TransactionDto("deposit", 200.0, ct1);

        assertEquals(60.0, ct2.getAmount());
        assertEquals(0, ct1.getChildren().size());
        assertEquals(pt, ct2.getParent());

        Optional<Transaction> updatedTx = txService.updateTransaction(newTx, ct2.getId());

        assertTrue(updatedTx.isPresent());
        assertEquals(200.0, updatedTx.get().getAmount());
        assertEquals("deposit", updatedTx.get().getType());
        assertEquals(1, ct1.getChildren().size());
        assertEquals(ct1, updatedTx.get().getParent());
    }

    @Test
    @DisplayName("Should throw exception when parent is not found by repository")
    void shouldThrowExceptionWhenParentNotFound() {

        TransactionDto newTx = new TransactionDto("deposit", 200.0, pt);
        when(txRepo.findById(2L)).thenReturn(Optional.of(ct1));

        assertThrows(ParentTransactionNotFoundException.class, () -> txService.updateTransaction(newTx, 2L));
    }

    @Test
    @DisplayName("Should return the sum of transactions' amounts transitively connected in the tree")
    void shouldReturnSumOfTransactionsAmounts() {
        when(txRepo.findWithChildrenById(1L)).thenReturn(Optional.of(pt));
        Double sum = txService.getSumOfTransactions(1L);
        assertEquals(ct1.getAmount() + ct2.getAmount() + pt.getAmount(), sum);
    }

    @Test
    @DisplayName("Should return the sum of transactions' amounts transitively connected in spite of being in a cycle")
    void shouldReturnSumOfTransactionsAmountsInTreeLoop() {
        when(txRepo.findWithChildrenById(4L)).thenReturn(Optional.of(pt2));
        Double sum = txService.getSumOfTransactions(4L);
        assertEquals(ct3.getAmount() + ct4.getAmount() + pt2.getAmount(), sum);
    }
}