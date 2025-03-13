package interview.mendel.challenge.controllers;

import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.interfaces.TransactionService;
import interview.mendel.challenge.models.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private List<Transaction> txs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        txs.addAll(List.of(
                new Transaction("deposit", 100.0),
                new Transaction("withdrawal", 50.0),
                new Transaction("deposit", 30.0)
        ));

        txs.get(0).setId(1L);
        txs.get(1).setId(2L);
        txs.get(2).setId(3L);

        txs.get(1).setParent(txs.get(0));
        txs.get(2).setParent(txs.get(0));

        txs.get(0).setChildren(List.of(txs.get(1), txs.get(2)));

    }

    @Test
    void testGetTransactionById_Success() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(txs.getFirst()));

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(txs.getFirst().getType()))
                .andExpect(jsonPath("$.amount").value(txs.getFirst().getAmount()));

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
    void testSumOfTransactions_Success() throws Exception {
        when(transactionService.getSumOfTransactions(1L)).thenReturn(txs.stream().map(Transaction::getAmount).reduce(Double::sum).orElse(0.0));

        mockMvc.perform(get("/transactions/sum/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(180.0));

        verify(transactionService, times(1)).getSumOfTransactions(1L);
    }



}
