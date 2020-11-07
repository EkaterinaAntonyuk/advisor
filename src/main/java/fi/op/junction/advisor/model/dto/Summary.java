package fi.op.junction.advisor.model.dto;

public class Summary {
    private double spent;
    private double planned;

    public Summary(double spent, double planned) {
        this.spent = spent;
        this.planned = planned;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public double getPlanned() {
        return planned;
    }

    public void setPlanned(double planned) {
        this.planned = planned;
    }
}
