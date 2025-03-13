package interview.mendel.challenge.interfaces;

import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.models.TransactionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    /**
     * Get a transaction by its id
     * @param id
     * @return The corresponding transaction to that id
     */
    Optional<Transaction> getTransactionById(Long id);

    /**
     * Get a list of transactions by type
     * @param type The type of the transaction
     * @param pageable The pagination object
     * @return
     */
    List<Long> getTransactionsByType(String type, Pageable pageable);

    /**
     * Get the sum of transactions by id
     * @param id: the id of the target transaction
     * @return The sum of the
     */
    Double getSumOfTransactions(Long id);

    /**
     * Update a transaction by its id
     * @param tx: the updated body of the transaction
     * @param id: the id of the target transaction
     * @return
     */
    Optional<Transaction> updateTransaction(TransactionDto tx, Long id);
}
