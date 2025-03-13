package interview.mendel.challenge.persistance;

import interview.mendel.challenge.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

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
    List<Transaction> findByType(String type, Pageable pageable);

    /**
     * Get a list of transitively connected transactions by the given id
     * @param id: the id of the target transaction. This is the ancestor of all other transactions to be returned
     * @return The list of transactions that are transitively connected to the target transaction,
     *         including the transaction itself.
     */
    @EntityGraph(attributePaths = {"children"})
    Optional<Transaction> findWithChildrenById(Long id);

}
