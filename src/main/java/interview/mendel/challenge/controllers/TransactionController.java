package interview.mendel.challenge.controllers;

import interview.mendel.challenge.exceptions.TransactionNotFoundException;
import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService ts) {
        this.transactionService = ts;
        log.info("TransactionController initialized");
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        Optional<Transaction> tx = transactionService.getTransactionById(id);
        if (tx.isEmpty()) {
            throw new TransactionNotFoundException();
        }
        return tx.get();
    }

    @GetMapping("/type/{type}")
    public List<Long> getTransactionsWithType(@PathVariable String type) {
        return transactionService.getTransactionsByType(type);
    }

    @GetMapping("/sum/{id}")
    public Map<String, Double> getSumOfTransactions(@PathVariable Long id) {
        return Map.of("sum", transactionService.getSumOfTransactions(id));
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@RequestBody Transaction tx, @PathVariable Long id) {
        Optional<Transaction> updatedTx = transactionService.updateTransaction(tx, id);
        if (updatedTx.isEmpty()) {
            throw new TransactionNotFoundException();
        }
        return updatedTx.get();
    }

}
