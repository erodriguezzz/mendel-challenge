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
                new Transaction("cars", 2.0),
                new Transaction("shopping", 2.0),
                new Transaction("food", 2.0),
                new Transaction("cars", 2.0),
                new Transaction("shopping", 2.0),
                new Transaction("food", 2.0),
                new Transaction("cars", 2.0),
                new Transaction("shopping", 2.0),
                new Transaction("food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0),
                new Transaction( "cars", 2.0),
                new Transaction( "shopping", 2.0),
                new Transaction( "food", 2.0));
        txs.get(1).setParent(txs.get(0));
        txs.get(2).setParent(txs.get(1));
        txs.get(3).setParent(txs.get(1));
        txs.get(4).setParent(txs.get(2));
        txs.get(5).setParent(txs.get(2));
        txs.get(7).setParent(txs.get(4));
        txs.get(8).setParent(txs.get(5));
        txs.get(9).setParent(txs.get(0));
        txs.get(10).setParent(txs.get(7));
        txs.get(11).setParent(txs.get(8));
        txs.get(12).setParent(txs.get(9));
        txs.get(14).setParent(txs.get(11));
        txs.get(15).setParent(txs.get(12));
        txs.get(16).setParent(txs.get(13));
        txs.get(17).setParent(txs.get(0));
        txs.get(20).setParent(txs.get(17));
        txs.get(21).setParent(txs.get(18));
        txs.get(22).setParent(txs.get(19));
        txs.get(23).setParent(txs.get(20));
        txs.get(24).setParent(txs.get(21));
        txs.get(25).setParent(txs.get(22));
        txs.get(26).setParent(txs.get(23));
        txs.get(27).setParent(txs.get(24));
        txs.get(28).setParent(txs.get(25));
        txs.get(29).setParent(txs.get(26));

        transactionRepository.saveAll(txs);
    }
}
