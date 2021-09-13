package varausjarjestelma;

//Luokka laitteelle
public class Laite {

    private int laiteID;
    private int toimipisteID;
    private String nimi;
    private double paivaHinta;

    public Laite() {
    }

    public Laite(String nimi, double paivaHinta) {
        this.nimi = nimi;
        this.paivaHinta = paivaHinta;
    }

    //getterit ja setterit
    public int getLaiteID() {
        return laiteID;
    }

    public int getToimipisteID() {
        return toimipisteID;
    }

    public String getNimi() {
        return nimi;
    }

    public double getPaivaHinta() {
        return paivaHinta;
    }

    public void setLaiteID(int laiteID) {
        this.laiteID = laiteID;
    }

    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setPaivaHinta(double paivaHinta) {
        this.paivaHinta = paivaHinta;
    }

}
