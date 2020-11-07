package fi.op.junction.advisor.model.db;

public class SpendingPlan {
    private int id;
    private String nameEng;
    private String nameFin;
    private double plan;

    public SpendingPlan() {
    }

    public SpendingPlan(int id, String nameEng, double plan) {
        this.id = id;
        this.nameEng = nameEng;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameFin() {
        return nameFin;
    }

    public void setNameFin(String nameFin) {
        this.nameFin = nameFin;
    }

    public double getPlan() {
        return plan;
    }

    public void setPlan(double plan) {
        this.plan = plan;
    }
}
