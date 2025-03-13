package interview.mendel.challenge.models;

public class Transaction {

    private Long id;
    private String type;
    private Double amount;
    private Long parentId;

    public Transaction(Long id, String type, Double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Transaction fromTransactionDto(TransactionDto tx, Long id) {
        Transaction transaction = new Transaction(id, tx.getType(), tx.getAmount());
        transaction.setParentId(tx.getParent_id());
        return transaction;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getParent_id() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
