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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
        if(id == null || id < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"id\" cannot be empty");
        }
        Optional<Transaction> tx = transactionService.getTransactionById(id);
        if(tx.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        return ResponseEntity.ok(TransactionDto.fromTransaction(tx.get()));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Long>> getTransactionsWithType(@PathVariable String type) {
        if(type == null || type.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"type\" cannot be empty");
        }
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    @GetMapping("/sum/{id}")
    public ResponseEntity<?> getSumOfTransactions(@PathVariable Long id) {
        if(id == null || id < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"id\" cannot be empty");
        }
        Double sum = transactionService.getSumOfTransactions(id).orElseThrow(() -> new TransactionNotFoundException(id.toString()));
        return ResponseEntity.ok("{\"sum\":" + sum + "}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionDto tx, @PathVariable Long id) {
        if(id == null || id < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "\"id\" cannot be empty");
        }
        Optional<Transaction> updatedTx = transactionService.updateTransaction(tx, id);
        if (updatedTx.isEmpty()) {
            throw new TransactionNotFoundException(id.toString());
        }
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }

}
