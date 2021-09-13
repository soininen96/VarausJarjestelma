package varausjarjestelma;

public class Toimipiste {

    private int toimipisteID;
    private String katuosoite;
    private String postinumero;
    private String postitoimipaikka;

    public Toimipiste() {
    }

    public Toimipiste(String katuosoite, String postinumero, String postitoimipaikka) {
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.postitoimipaikka = postitoimipaikka;
    }

    public int getToimipisteID() {
        return toimipisteID;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public String getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }
    
    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    public void setPostitoimipaikka(String postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

}
