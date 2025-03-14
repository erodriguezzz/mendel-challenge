package interview.mendel.challenge.controllers;

import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.interfaces.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class TransactionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private TransactionController transactionController;

    private List<Transaction> txs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        txs.clear();

        txs.addAll(List.of(
                new Transaction(1L,"deposit", 100.0),
                new Transaction(2L,"withdrawal", 50.0),
                new Transaction(3L,"deposit", 30.0)
        ));

        txs.get(1).setParentId(1L);
        txs.get(2).setParentId(1L);

    }

    @Test
    void testGetTransactionById_Success() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(txs.get(0)));

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(txs.get(0).getType()))
                .andExpect(jsonPath("$.amount").value(txs.get(0).getAmount()));

        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    void testGetTransactionByInvalidId_BadRequest() throws Exception {
        mockMvc.perform(get("/transactions/-1"))
                .andExpect(status().isBadRequest());
        verify(transactionService, times(0)).getTransactionById(-1L);
    }

    @Test
    void testGetTransactionsWithType_Success() throws Exception {
        when(transactionService.getTransactionsByType("deposit")).thenReturn(txs.stream().filter(tx -> tx.getType().equals("deposit")).map(Transaction::getId).toList());

        mockMvc.perform(get("/transactions/type/deposit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(1L))
                .andExpect(jsonPath("$[1]").value(3L));

        verify(transactionService, times(1)).getTransactionsByType("deposit");
    }

    @Test
    void testGetTransactionsWithType_NotFound() throws Exception {
        when(transactionService.getTransactionsByType("deposit")).thenReturn(List.of());

        mockMvc.perform(get("/transactions/type/deposit"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(transactionService, times(1)).getTransactionsByType("deposit");
    }

    @Test
    void testSumOfTransactions_Success() throws Exception {
        when(transactionService.getSumOfTransactions(1L)).thenReturn(Optional.of(txs.get(0).getAmount() + txs.get(1).getAmount() + txs.get(2).getAmount()));

        mockMvc.perform(get("/transactions/sum/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(180.0));

        verify(transactionService, times(1)).getSumOfTransactions(1L);
    }


}
