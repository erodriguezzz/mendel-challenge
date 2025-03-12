package interview.mendel.challenge.services;

import interview.mendel.challenge.persistance.TransactionDao;
import interview.mendel.challenge.interfaces.TransactionService;
import interview.mendel.challenge.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionDao transactionRepository;

    public TransactionServiceImpl(TransactionDao transactionRepository) {
        this.transactionRepository = transactionRepository;
        log.info("TransactionService initialized");
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Long> getTransactionsByType(String type) {
        return transactionRepository.findByType(type).stream().map(Transaction::getId).toList();
    }

    /*
    @Override
    public Double getSumOfTransactions(Long id) {
        // Get the sum of all transactions' amounts that are transitively connected to the parameter id transaction
        // This is done by getting all transactions that are transitively connected to the parameter id transaction
        // and then summing their amounts
    }

    @Override
    public Optional<Transaction> updateTransaction(Transaction newTx, Long id) {
        Optional<Transaction> oldTx = transactionRepository.findById(id);
        if (oldTx.isEmpty()) {
            return Optional.empty();
        }
        return transactionRepository.updateTransaction(newTx, oldTx.get());
    }
    */

}
