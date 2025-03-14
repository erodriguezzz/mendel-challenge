package interview.mendel.challenge.interfaces;

import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.models.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    /**
     * Get a transaction by its id
     * @param id
     * @return The corresponding transaction to that id
     */
    Optional<Transaction> findById(Long id);

    /**
     * Get a list of transactions by type
     * @param type: Type of the transaction
     * @return
     */
    List<Transaction> findByType(String type);

    /**
     * Get a list of transitively connected transactions by the given id
     * @param id: the id of the target transaction. This is the ancestor of all other transactions to be returned
     * @return The list of transactions that are transitively connected to the target transaction,
     *         including the transaction itself.
     */
    Optional<Double> findTransitiveTransactionSum(Long id);

    /**
     * Create a transaction
     * @param tx: the body of the new transaction
     * @return
     */
    Optional<Transaction> saveTransaction(Transaction tx, Long id);
}
