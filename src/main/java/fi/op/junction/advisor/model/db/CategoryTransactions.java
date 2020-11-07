package fi.op.junction.advisor.model.db;

import java.sql.Timestamp;

public class CategoryTransactions {
    private String category;
    private double sum;

    public CategoryTransactions() {
    }

    public CategoryTransactions(String category, double sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
