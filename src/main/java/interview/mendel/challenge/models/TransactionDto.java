package interview.mendel.challenge.models;

import java.util.List;

public class TransactionDto {

    private String type;
    private Double amount;
    private Long parent_id;
    private List<Long> children;

    public TransactionDto(String type, Double amount, Transaction parent, List<Long> children) {
        this.type = type;
        this.amount = amount;
        this.parent_id = parent == null ? null : parent.getId();
        this.children = children;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

}
