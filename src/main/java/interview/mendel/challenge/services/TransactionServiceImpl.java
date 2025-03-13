package interview.mendel.challenge.services;

import interview.mendel.challenge.models.TransactionDto;
import interview.mendel.challenge.exceptions.ParentTransactionNotFoundException;
import interview.mendel.challenge.persistance.TransactionDao;
import interview.mendel.challenge.interfaces.TransactionService;
import interview.mendel.challenge.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<Long> getTransactionsByType(String type, Pageable pageable) {
        return transactionRepository.findByType(type, pageable).stream().map(Transaction::getId).toList();
    }

    @Override
    public Double getSumOfTransactions(Long id) {
        Transaction txOpt = transactionRepository.findWithChildrenById(id).orElseThrow(() -> new ParentTransactionNotFoundException(id.toString()));
        Set<Long> visited = new HashSet<>();
        return sumTransactionsRecursively(txOpt, visited);
    }

    private double sumTransactionsRecursively(Transaction transaction, Set<Long> visited) {
        // Avoid cycles in tree structure
        if (!visited.add(transaction.getId())) {
            return 0.0;
        }

        // All transactions are brought to memory. Could be optimized to fetch only the necessary data
        double sum = transaction.getAmount();
        List<Transaction> children = transaction.getChildren();

        return sum + children.stream()
                .mapToDouble(child -> sumTransactionsRecursively(child, visited))
                .sum();
    }

    @Override
    public Optional<Transaction> updateTransaction(TransactionDto newTx, Long id) {
        Optional<Transaction> oldTx = transactionRepository.findById(id);
        if (oldTx.isEmpty()) {
            return Optional.empty();
        }
        Transaction parent = null;
        if (newTx.getParent_id() != null){
            parent = transactionRepository.findById(newTx.getParent_id()).orElseThrow(() -> new ParentTransactionNotFoundException(newTx.getParent_id().toString()));
        }
        oldTx.get().setAmount(newTx.getAmount());
        oldTx.get().setType(newTx.getType());
        oldTx.get().setParent(parent);
        if(parent != null){
            parent.addChild(oldTx.get());
        }
        transactionRepository.save(oldTx.get());
        return oldTx;
    }

}
