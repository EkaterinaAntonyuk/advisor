package fi.op.junction.advisor.model.dto;

public class Suggestion {
    private int id;
    private String nameEng;
    private double currentUserAverage;
    private double allUsersAverage;

    public Suggestion(int id, String nameEng, double currentUserAverage, double allUsersAverage) {
        this.id = id;
        this.nameEng = nameEng;
        this.currentUserAverage = currentUserAverage;
        this.allUsersAverage = allUsersAverage;
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

    public double getCurrentUserAverage() {
        return currentUserAverage;
    }

    public void setCurrentUserAverage(double currentUserAverage) {
        this.currentUserAverage = currentUserAverage;
    }

    public double getAllUsersAverage() {
        return allUsersAverage;
    }

    public void setAllUsersAverage(double allUsersAverage) {
        this.allUsersAverage = allUsersAverage;
    }
}
