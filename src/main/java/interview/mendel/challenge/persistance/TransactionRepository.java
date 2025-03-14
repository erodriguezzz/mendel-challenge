package interview.mendel.challenge.persistance;

import interview.mendel.challenge.exceptions.ParentTransactionNotFoundException;
import interview.mendel.challenge.interfaces.TransactionDao;
import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.models.TransactionDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransactionRepository implements TransactionDao {

    private Map<Long, Transaction> txs = new HashMap<>();

    public void setTxs(List<Transaction> txs) {
        txs.forEach(tx -> this.txs.put(tx.getId(), tx));
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        if(txs.containsKey(id)) {
            return Optional.of(txs.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> findByType(String type) {
        List<Transaction> txsIds = new ArrayList<>();
        txs.forEach((k, v) -> {
            if(v.getType().equals(type)) {
                txsIds.add(v);
            }
        });
        return txsIds;
    }

    @Override
    public Optional<Double> findTransitiveTransactionSum(Long id) {
        if(!txs.containsKey(id)) {
            return Optional.empty();
        }
        Set<Long> visited = new HashSet<>();
        return Optional.of(findTransitiveTransactionSumRecursively(txs.get(id), visited));
    }

    private double findTransitiveTransactionSumRecursively(Transaction transaction, Set<Long> visited) {
        // Avoid cycles in tree structure
        if (!visited.add(transaction.getId())) {
            return 0.0;
        }

        // All transactions are brought to memory. Could be optimized to fetch only the necessary data
        double sum = transaction.getAmount();

        return sum + txs.values().stream()
                .filter(tx -> Objects.equals(tx.getParent_id(), transaction.getId()))
                .mapToDouble(tx -> findTransitiveTransactionSumRecursively(tx, visited))
                .sum();
    }

    @Override
    public Optional<Transaction> saveTransaction(Transaction tx, Long id) {
        txs.put(id, tx);
        return Optional.of(txs.get(id));
    }
}
