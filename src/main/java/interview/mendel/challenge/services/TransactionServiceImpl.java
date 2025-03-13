package interview.mendel.challenge.services;

import interview.mendel.challenge.models.TransactionDto;
import interview.mendel.challenge.exceptions.ParentTransactionNotFoundException;
import interview.mendel.challenge.interfaces.TransactionService;
import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.persistance.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
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

    @Override
    public Optional<Double> getSumOfTransactions(Long id) {
        return transactionRepository.findTransitiveTransactionSum(id);
    }

    @Override
    public Optional<Transaction> updateTransaction(TransactionDto newTx, Long id) {
        return transactionRepository.updateTransaction(Transaction.fromTransactionDto(newTx, id), id);
    }

}
