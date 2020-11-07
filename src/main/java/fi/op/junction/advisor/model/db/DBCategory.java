package fi.op.junction.advisor.model.db;

public class DBCategory {
    private int id;
    private String nameFin;
    private String nameEng;

    public DBCategory() {
    }

    public DBCategory(int id, String nameFin, String nameEng) {
        this.id = id;
        this.nameFin = nameFin;
        this.nameEng = nameEng;
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
}
