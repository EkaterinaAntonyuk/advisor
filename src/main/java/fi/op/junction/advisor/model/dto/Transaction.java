package fi.op.junction.advisor.model.dto;

import java.sql.Timestamp;

public class Transaction {
    private Timestamp tstamp;
    private double rahamaara;
    private String name;

    public Timestamp getTstamp() {
        return tstamp;
    }

    public void setTstamp(Timestamp tstamp) {
        this.tstamp = tstamp;
    }

    public double getRahamaara() {
        return rahamaara;
    }

    public void setRahamaara(double rahamaara) {
        this.rahamaara = rahamaara;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
