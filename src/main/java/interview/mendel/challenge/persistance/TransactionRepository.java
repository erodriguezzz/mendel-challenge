package interview.mendel.challenge.persistance;

import interview.mendel.challenge.interfaces.TransactionDao;
import interview.mendel.challenge.models.Transaction;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Repository
public class TransactionRepository implements TransactionDao {

    private static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    private Map<Long, List<Transaction>> txs = new HashMap<>();

    @PostConstruct
    private void init(){
        List<Transaction> transactions = List.of(
        new Transaction(1L, "cars", 2.0, null),
        new Transaction(2L, "shopping", 2.0, 1L),
        new Transaction(3L, "food", 2.0, 2L),
        new Transaction(4L, "cars", 2.0, 2L),
        new Transaction(5L, "shopping", 2.0, 3L),
        new Transaction(6L, "food", 2.0, 3L),
        new Transaction(7L, "cars", 2.0, null),
        new Transaction(8L, "shopping", 2.0, 5L),
        new Transaction(9L, "food", 2.0, 6L),
        new Transaction(10L, "cars", 2.0, 1L),
        new Transaction(11L, "shopping", 2.0, 8L),
        new Transaction(12L, "food", 2.0, 9L),
        new Transaction(13L, "cars", 2.0, 10L),
        new Transaction(14L, "shopping", 2.0, null),
        new Transaction(15L, "food", 2.0, 12L),
        new Transaction(16L, "cars", 2.0, 13L),
        new Transaction(17L, "shopping", 2.0, 14L),
        new Transaction(18L, "food", 2.0, 1L),
        new Transaction(19L, "cars", 2.0, null),
        new Transaction(20L, "shopping", 2.0, null),
        new Transaction(21L, "food", 2.0, 18L),
        new Transaction(22L, "cars", 2.0, 19L),
        new Transaction(23L, "shopping", 2.0, 20L),
        new Transaction(24L, "food", 2.0, 21L),
        new Transaction(25L, "cars", 2.0, 22L),
        new Transaction(26L, "shopping", 2.0, 23L),
        new Transaction(27L, "food", 2.0, 24L),
        new Transaction(28L, "cars", 2.0, 25L),
        new Transaction(29L, "shopping", 2.0, 26L),
        new Transaction(30L, "food", 2.0, 27L));

        // Add transaction to the map where the key is the parent id
        transactions.forEach(tx -> {
            if (txs.containsKey(tx.getParent_id())) {
                txs.get(tx.getParent_id()).add(tx);
            } else {
                txs.put(tx.getParent_id(), new ArrayList<>(List.of(tx)));
            }
        });

        log.info("TransactionRepository was populated...");
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return txs.values().stream().flatMap(Collection::stream)
                .filter(tx -> Objects.equals(tx.getId(), id)).findFirst();
    }

    @Override
    public Stream<Transaction> getTransactionsByType(String type) {
        return txs.values().stream().flatMap(Collection::stream)
                .filter(tx -> Objects.equals(tx.getType(), type));
    }

    @Override
    public Stream<Transaction> getChildRelatedTransactions(Long id) {
        return Stream.concat(
                getTransactionById(id).stream(),
                txs.getOrDefault(id, List.of()).stream().flatMap(t -> getChildRelatedTransactions(t.getId()))
        );
    }

    @Override
    public Optional<Transaction> updateTransaction(Transaction newTxInfo, Transaction oldTxInfo) {
        txs.get(oldTxInfo.getParent_id()).remove(oldTxInfo);
        oldTxInfo.setType(newTxInfo.getType());
        oldTxInfo.setAmount(newTxInfo.getAmount());
        oldTxInfo.setParent_id(newTxInfo.getParent_id());
        txs.get(newTxInfo.getParent_id()).add(oldTxInfo);
        return Optional.of(oldTxInfo);
    }

}
