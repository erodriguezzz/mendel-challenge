package interview.mendel.challenge.controllers;

import interview.mendel.challenge.exceptions.TransactionNotFoundException;
import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.interfaces.TransactionService;
import interview.mendel.challenge.models.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> tx = transactionService.getTransactionById(id);
        if (tx.isEmpty()) {
            throw new TransactionNotFoundException(id.toString());
        }
        Transaction t = tx.get();
        return ResponseEntity.ok(new TransactionDto(t.getType(), t.getAmount(), t.getParent()));
    }

    @GetMapping("/type/{type}")
    public List<Long> getTransactionsWithType(@PathVariable String type,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        if(type == null || type.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"type\" cannot be empty");
        }
        return transactionService.getTransactionsByType(type, Pageable.ofSize(size).withPage(page));
    }

    @GetMapping("/sum/{id}")
    public Map<String, Double> getSumOfTransactions(@PathVariable Long id) {
        if(id == null || id < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"id\" cannot be empty");
        }
        return Map.of("sum", transactionService.getSumOfTransactions(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody TransactionDto tx, @PathVariable Long id) {
        if(id == null || id < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"id\" cannot be empty");
        }
        Optional<Transaction> updatedTx = transactionService.updateTransaction(tx, id);
        if (updatedTx.isEmpty()) {
            throw new TransactionNotFoundException(id.toString());
        }
        return ResponseEntity.noContent().build();
    }

}
