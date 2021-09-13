package varausjarjestelma;

public class ToimistoTila {

    private int toimistotilaID;
    private int toimipisteID;
    private double paivaVuokra;
    private double koko;
    private String huonenro;

    public ToimistoTila() {
    }
    
    public ToimistoTila(double paivaVuokra, double koko, String huonenro) {
        this.paivaVuokra = paivaVuokra;
        this.koko = koko;
        this.huonenro = huonenro;
    }

    public int getToimistotilaID() {
        return toimistotilaID;
    }

    public int getToimipisteID() {
        return toimipisteID;
    }

    public double getPaivaVuokra() {
        return paivaVuokra;
    }

    public double getKoko() {
        return koko;
    }

    public String getHuonenro() {
        return huonenro;
    }
    
    public void setPaivaVuokra(double paivaVuokra) {
        this.paivaVuokra = paivaVuokra;
    }

    public void setKoko(double koko) {
        this.koko = koko;
    }

    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }

    public void setToimistotilaID(int toimistotilaID) {
        this.toimistotilaID = toimistotilaID;
    }

    public void setHuonenro(String huonenro) {
        this.huonenro = huonenro;
    }
    
    

}
