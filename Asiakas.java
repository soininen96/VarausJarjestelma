package varausjarjestelma;

public class Asiakas {

    private int asiakasID;
    private String nimi;
    private String email;
    private String laskutustapa;
    private String katuosoite;
    private int postinumero;
    private String postitoimipaikka;
    private String puhelinumero;

    public Asiakas(int id, String nimi, String email, String laskutustapa, String katuosoite, int postinumero, String postitoimipaikka, String puhelinnumero) {
        this.asiakasID = id;
        this.nimi = nimi;
        this.email = email;
        this.laskutustapa = laskutustapa;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.postitoimipaikka = postitoimipaikka;
        this.puhelinumero = puhelinnumero;
    }

    public int getAsiakasID() {
        return asiakasID;
    }

    public String getNimi() {
        return nimi;
    }

    public String getEmail() {
        return email;
    }

    public String getLaskutustapa() {
        return laskutustapa;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public int getPostinumero() {
        return postinumero;
    }

    public String getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public String getPuhelinumero() {
        return puhelinumero;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLaskutustapa(String laskutustapa) {
        this.laskutustapa = laskutustapa;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public void setPostinumero(int postinumero) {
        this.postinumero = postinumero;
    }

    public void setPostitoimipaikka(String postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

    public void setPuhelinumero(String puhelinumero) {
        this.puhelinumero = puhelinumero;
    }

}
