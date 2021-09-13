package varausjarjestelma;

public class Lasku {

    private int laskuID;
    private int asiakasID;
    private int varausID;
    private String erapvm;
    private double summa;
    private int viitenumero;
    private String osoite;
    private String postinumero;
    private String postitoimipaikka;

    public Lasku() {
    }

    public Lasku(String erapvm, double summa, String osoite, String postinumero, String postitoimipaikka) {
        this.erapvm = erapvm;
        this.summa = summa;
        this.osoite = osoite;
        this.postinumero = postinumero;
        this.postitoimipaikka = postitoimipaikka;
    }

    public int getLaskuID() {
        return laskuID;
    }

    public int getAsiakasID() {
        return asiakasID;
    }

    public int getVarausID() {
        return varausID;
    }

    public String getErapvm() {
        return erapvm;
    }

    public double getSumma() {
        return summa;
    }

    public int getViitenumero() {
        return viitenumero;
    }

    public String getOsoite() {
        return osoite;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public String getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public void setAsiakasID(int asiakasID) {
        this.asiakasID = asiakasID;
    }

    public void setErapvm(String erapvm) {
        this.erapvm = erapvm;
    }

    public void setLaskuID(int laskuID) {
        this.laskuID = laskuID;
    }

    public void setOsoite(String osoite) {
        this.osoite = osoite;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    public void setPostitoimipaikka(String postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

    public void setViitenumero(int viitenumero) {
        this.viitenumero = viitenumero;
    }

}
