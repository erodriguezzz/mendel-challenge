package interview.mendel.challenge.services;

import interview.mendel.challenge.models.Transaction;
import interview.mendel.challenge.persistance.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public DataLoader(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Transaction> txs = List.of(
                new Transaction(1L, "cars", 2.0),
                new Transaction(2L, "shopping", 2.0),
                new Transaction(3L, "food", 2.0),
                new Transaction(4L, "cars", 2.0),
                new Transaction(5L, "shopping", 2.0),
                new Transaction(6L, "food", 2.0),
                new Transaction(7L, "cars", 2.0),
                new Transaction(8L, "shopping", 2.0),
                new Transaction(9L, "food", 2.0),
                new Transaction(10L, "cars", 2.0),
                new Transaction(11L, "shopping", 2.0),
                new Transaction(12L, "food", 2.0),
                new Transaction(13L, "cars", 2.0),
                new Transaction(14L, "shopping", 2.0),
                new Transaction(15L, "food", 2.0),
                new Transaction(16L, "cars", 2.0),
                new Transaction(17L, "shopping", 2.0),
                new Transaction(18L, "food", 2.0),
                new Transaction(19L, "cars", 2.0),
                new Transaction(20L, "shopping", 2.0),
                new Transaction(21L, "food", 2.0),
                new Transaction(22L, "cars", 2.0),
                new Transaction(23L, "shopping", 2.0),
                new Transaction(24L, "food", 2.0),
                new Transaction(25L, "cars", 2.0),
                new Transaction(26L, "shopping", 2.0),
                new Transaction(27L, "food", 2.0),
                new Transaction(28L, "cars", 2.0),
                new Transaction(29L, "shopping", 2.0),
                new Transaction(30L, "food", 2.0)
        );

        txs.get(1).setParentId(1L);
        txs.get(2).setParentId(2L);
        txs.get(3).setParentId(2L);
        txs.get(4).setParentId(3L);
        txs.get(5).setParentId(3L);
        txs.get(7).setParentId(5L);
        txs.get(8).setParentId(6L);
        txs.get(9).setParentId(1L);
        txs.get(10).setParentId(8L);
        txs.get(11).setParentId(9L);
        txs.get(12).setParentId(10L);
        txs.get(14).setParentId(12L);
        txs.get(15).setParentId(13L);
        txs.get(16).setParentId(14L);
        txs.get(17).setParentId(1L);
        txs.get(20).setParentId(18L);
        txs.get(21).setParentId(19L);
        txs.get(22).setParentId(20L);
        txs.get(23).setParentId(21L);
        txs.get(24).setParentId(22L);
        txs.get(25).setParentId(23L);
        txs.get(26).setParentId(24L);
        txs.get(27).setParentId(25L);
        txs.get(28).setParentId(26L);
        txs.get(29).setParentId(27L);

        transactionRepository.setTxs(txs);
    }

}
