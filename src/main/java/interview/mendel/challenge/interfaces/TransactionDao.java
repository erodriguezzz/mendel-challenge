package interview.mendel.challenge.interfaces;

import interview.mendel.challenge.models.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionDao {

    /**
     * Get a transaction by its id
     * @param id
     * @return The corresponding transaction to that id
     */
    Optional<Transaction> getTransactionById(Long id);

    /**
     * Get a list of transactions by type
     * @param type: Type of the transaction
     * @return
     */
    Stream<Transaction> getTransactionsByType(String type);

    /**
     * Get a list of transitively connected transactions by the given id
     * @param id: the id of the target transaction. This is the ancestor of all other transactions to be returned
     * @return The list of transactions that are transitively connected to the target transaction,
     *         including the transaction itself.
     */
    Stream<Transaction> getChildRelatedTransactions(Long id);

    /**
     * Update a transaction with new information
     * @param newTxInfo: The new information to be updated
     * @param oldTxInfo: The old information to be updated
     * @return The updated transaction
     */
    Optional<Transaction> updateTransaction(Transaction newTxInfo, Transaction oldTxInfo);

}
