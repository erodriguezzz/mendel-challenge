package interview.mendel.challenge.services;

import interview.mendel.challenge.interfaces.TransactionDao;
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
        return transactionRepository.getTransactionById(id);
    }

    @Override
    public List<Long> getTransactionsByType(String type) {
        return transactionRepository.getTransactionsByType(type).map(Transaction::getId).toList();
    }

    @Override
    public Double getSumOfTransactions(Long id) {
        return transactionRepository.getChildRelatedTransactions(id).mapToDouble(Transaction::getAmount).sum();
    }

    @Override
    public Optional<Transaction> updateTransaction(Transaction newTx, Long id) {
        Optional<Transaction> oldTx = transactionRepository.getTransactionById(id);
        if (oldTx.isEmpty()) {
            return Optional.empty();
        }
        return transactionRepository.updateTransaction(newTx, oldTx.get());
    }

}
