package fi.op.junction.advisor.model.dto;

public class Category {

    private int id;
    private String name;
    private double spent;
    private double planned;
    private Status status;

    public Category(int id, String name, double spent, double planned, Status status) {
        this.id = id;
        this.name = name;
        this.spent = spent;
        this.planned = planned;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
