/*Projektissamme Palvelut on jaettu palveluihin ja laitteisiin
* Molemmille on omat luokkansa. Molempien hallinta on jaettu kolmen
* view controllerin välillä. 
* Emme olleet täysi varmoja että mitä pitäisi palauttaa joten olen lisännyt tähän
* samaan tiedostoon myös luokan Laite. Sekä sen alapuolelle versiot niistä
* kolmesta view controllerista joista olen pyyhkinyt pois kaiken mikä ei
* liity palveluihin tai laitteisiin. Eli ne eivät ole toimivia. 
* Jos palautus on puutteellinen niin laita viestiä niin voin palauttaa 
* vaikka koko projektin.
*/

package varausjarjestelma;

//Luokka palvelulle
public class Palvelu {

    private int palveluID;
    private int ToimipisteID;
    private String nimi;
    private double paivaHinta;

    public Palvelu() {
    }

    public Palvelu(String nimi, double paivaHinta) {
        this.nimi = nimi;
        this.paivaHinta = paivaHinta;
    }
    
    //Getterit ja setterit
    public int getPalveluID() {
        return palveluID;
    }

    public int getToimipisteID() {
        return ToimipisteID;
    }

    public String getNimi() {
        return nimi;
    }

    public double getPaivaHinta() {
        return paivaHinta;
    }

    public void setPalveluID(int palveluID) {
        this.palveluID = palveluID;
    }

    public void setToimipisteID(int ToimipisteID) {
        this.ToimipisteID = ToimipisteID;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setPaivaHinta(double paivaHinta) {
        this.paivaHinta = paivaHinta;
    }

}
