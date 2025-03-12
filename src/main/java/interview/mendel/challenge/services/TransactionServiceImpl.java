package interview.mendel.challenge.services;

import interview.mendel.challenge.models.TransactionDto;
import interview.mendel.challenge.exceptions.ParentTransactionNotFoundException;
import interview.mendel.challenge.persistance.TransactionDao;
import interview.mendel.challenge.interfaces.TransactionService;
import interview.mendel.challenge.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public List<Long> getTransactionsByType(String type) {
        return transactionRepository.findByType(type).stream().map(Transaction::getId).toList();
    }

    @Override
    public Double getSumOfTransactions(Long id) {
        Optional<Transaction> txOpt = transactionRepository.findWithChildrenById(id);
        Set<Long> visited = new HashSet<>();
        if (txOpt.isEmpty()) {
            return 0.0;
        }
        return sumTransactionsRecursively(txOpt.get(), visited);
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
        Transaction oldParent = oldTx.get().getParent();
        Optional<Transaction> parent = transactionRepository.findById(newTx.getParent_id());
        if (parent.isEmpty()) {
            throw new ParentTransactionNotFoundException(newTx.getParent_id().toString());
        }
        if(oldParent != null){
            oldParent.removeChild(oldTx.get());
        }
        oldTx.get().setAmount(newTx.getAmount());
        oldTx.get().setType(newTx.getType());
        oldTx.get().setParent(parent.get());
        parent.get().addChild(oldTx.get());
        transactionRepository.save(oldTx.get());
        return oldTx;
    }

}
