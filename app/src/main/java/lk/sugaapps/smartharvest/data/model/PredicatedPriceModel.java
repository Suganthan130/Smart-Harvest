package lk.sugaapps.smartharvest.data.model;

public class PredicatedPriceModel {
    private String date;
    private double Pettah;
    private double Dambulla;

    public PredicatedPriceModel() {} // Needed for Firestore

    public PredicatedPriceModel(String date, double Pettah, double Dambulla) {
        this.date = date;
        this.Pettah = Pettah;
        this.Dambulla = Dambulla;
    }

    public String getDate() { return date; }
    public double getPettah() { return Pettah; }
    public double getDambulla() { return Dambulla; }
}
