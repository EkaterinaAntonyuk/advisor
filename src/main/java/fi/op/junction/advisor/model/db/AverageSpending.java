package fi.op.junction.advisor.model.db;

public class AverageSpending {
    private int id;
    private String category;
    private double monthSum;

    public AverageSpending() {
    }

    public AverageSpending(String category, double monthSum) {
        this.category = category;
        this.monthSum = monthSum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMonthSum() {
        return monthSum;
    }

    public void setMonthSum(double monthSum) {
        this.monthSum = monthSum;
    }
}
