package interview.mendel.challenge.services;

import interview.mendel.challenge.persistance.TransactionDao;
import interview.mendel.challenge.models.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final TransactionDao transactionRepository;

    public DataLoader(TransactionDao transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Transaction> txs = List.of(
                new Transaction("cars", 2.0, null),
                new Transaction("shopping", 2.0, 1L),
                new Transaction("food", 2.0, 2L),
                new Transaction("cars", 2.0, 2L),
                new Transaction("shopping", 2.0, 3L),
                new Transaction("food", 2.0, 3L),
                new Transaction("cars", 2.0, null),
                new Transaction("shopping", 2.0, 5L),
                new Transaction("food", 2.0, 6L),
                new Transaction( "cars", 2.0, 1L),
                new Transaction( "shopping", 2.0, 8L),
                new Transaction( "food", 2.0, 9L),
                new Transaction( "cars", 2.0, 10L),
                new Transaction( "shopping", 2.0, null),
                new Transaction( "food", 2.0, 12L),
                new Transaction( "cars", 2.0, 13L),
                new Transaction( "shopping", 2.0, 14L),
                new Transaction( "food", 2.0, 1L),
                new Transaction( "cars", 2.0, null),
                new Transaction( "shopping", 2.0, null),
                new Transaction( "food", 2.0, 18L),
                new Transaction( "cars", 2.0, 19L),
                new Transaction( "shopping", 2.0, 20L),
                new Transaction( "food", 2.0, 21L),
                new Transaction( "cars", 2.0, 22L),
                new Transaction( "shopping", 2.0, 23L),
                new Transaction( "food", 2.0, 24L),
                new Transaction( "cars", 2.0, 25L),
                new Transaction( "shopping", 2.0, 26L),
                new Transaction( "food", 2.0, 27L));
        transactionRepository.saveAll(txs);
    }
}
