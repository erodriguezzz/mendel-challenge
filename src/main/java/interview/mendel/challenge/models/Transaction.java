package interview.mendel.challenge.models;

import java.util.Objects;

public class Transaction {

    private Long id;
    private String type;
    private Double amount;
    private Long parent_id;

    public Transaction() {
    }

    public Transaction(Long id, String type, Double amount, Long parent_id) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.parent_id = parent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(parent_id, that.parent_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, amount, parent_id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", parentId=" + parent_id +
                '}';
    }
}
