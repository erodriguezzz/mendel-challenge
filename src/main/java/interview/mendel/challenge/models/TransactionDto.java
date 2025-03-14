package interview.mendel.challenge.models;

public class TransactionDto {

    private String type;
    private Double amount;
    private Long parent_id;

    public TransactionDto(String type, Double amount, Long parent_id) {
        this.type = type;
        this.amount = amount;
        this.parent_id = parent_id;
    }

    public static TransactionDto fromTransaction(Transaction tx) {
        return new TransactionDto( tx.getType(), tx.getAmount(), tx.getParent_id());
    }

    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getParent_id() {
        return parent_id;
    }

}
