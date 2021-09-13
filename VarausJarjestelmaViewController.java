package varausjarjestelma;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patri
 */
public class VarausJarjestelmaViewController implements Initializable {

    @FXML
    private ComboBox<String> cboToimipisteet;
    @FXML
    private Button btnToimiPisteLisaa;
    @FXML
    private Button btnToimiPisteMuokkaa;
    @FXML
    private Button btnToimiPistePoista;
    @FXML
    private Button btnToimiPisteTallenna;
    @FXML
    private Button btnToimiPistePeru;
    @FXML
    private TextField txtPisteKatuOsoite;
    @FXML
    private TextField txtPistePostiNumero;
    @FXML
    private TextField txtPistePostitoimipaikka;
    @FXML
    private ComboBox<String> cboToimistoTilat;
    @FXML
    private ComboBox<String> cboPalvelut;
    @FXML
    private Tab tabToimiPisteet;
    @FXML
    private Tab tabAsiakkaat;
    @FXML
    private GridPane asiakas_box;

    ArrayList<Toimipiste> toimipisteet = new ArrayList<>();
    ArrayList<ToimistoTila> toimistotilat = new ArrayList<>();
    ArrayList<Palvelu> palvelut = new ArrayList<>();
    ArrayList<Laite> laitteet = new ArrayList<>();
    ArrayList<Varaus> varatutToimitilat = new ArrayList<>();
    ArrayList<Asiakas> asiakkaat = new ArrayList<>();

    private boolean lisaaPainettu;
    private boolean muokkaaPainettu;
    private static int valittuToimipisteID;
    private static int valittuToimitilaID;
    private int valittuPalveluID;
    private int valittuLaiteID;

    @FXML
    private ComboBox<String> cboLaitteet;
    @FXML
    private TextField txtTilaPaivaVuokra;
    @FXML
    private TextField txtTilaKoko;
    @FXML
    private Button btnToimistoTilaLisaa;
    @FXML
    private Button btnToimistoTilaMuokkaa;
    @FXML
    private Button btnToimistoTilaPoista;
    @FXML
    private Button btnTilaLisaaAsiakas;
    @FXML
    private TextField txtPalveluNimi;
    @FXML
    private TextField txtPalveluPaivaHinta;
    @FXML
    private Button btnPalveluLisaa;
    @FXML
    private Button btnPalveluMuokkaa;
    @FXML
    private Button btnPalveluPoista;
    @FXML
    private Button btnPalveluTallenna;
    @FXML
    private Button btnPalveluPeru;
    @FXML
    private TextField txtLaiteNimi;
    @FXML
    private TextField txtLaitePaivaHinta;
    @FXML
    private Button btnLaiteLisaa;
    @FXML
    private Button btnLaiteMuokkaa;
    @FXML
    private Button btnLaitePoista;
    @FXML
    private Button btnLaiteTallenna;
    @FXML
    private Button btnLaitePeru;
    @FXML
    private TextField txtTilaHuoneenNumero;
    @FXML
    private Button btnToimiTilaTallenna;
    @FXML
    private Button btnToimiTilaPeru;
    @FXML
    private Button A_lisaa;
    @FXML
    private TextField A_nimi;
    @FXML
    private TextField A_email;
    @FXML
    private TextField A_osoite;
    @FXML
    private TextField A_postinumero;
    @FXML
    private TextField A_postipaikka;
    @FXML
    private TextField A_puhelin;
    @FXML
    private ComboBox A_laskutus;
    @FXML

    private TextField AM_nimi;
    @FXML
    private TextField AM_email;
    @FXML
    private TextField AM_osoite;
    @FXML
    private TextField AM_postinumero;
    @FXML
    private TextField AM_postipaikka;
    @FXML
    private TextField AM_puhelin;
    @FXML
    private ComboBox AM_laskutus;
    @FXML
    private VBox A_lisaa_uusi;
    @FXML
    private VBox A_muokkaa;
    @FXML
    private Button AM_laheta;
    @FXML
    private Button AM_peruuta;
    
    @FXML
    private Text txtPalveluVaroitus;
    @FXML
    private Ellipse circlePalveluVaroitus;
    @FXML
    private Text txtPalveluHintaVaroitus;
    @FXML
    private Ellipse circlePalveluHintaVaroitus;
    @FXML
    private Text txtLaiteVaroitus;
    @FXML
    private Ellipse circleLaiteVaroitus;
    @FXML
    private Text txtKatuOsoiteVaroitus;
    @FXML
    private Ellipse circleKatuOsoiteVaroitus;
    @FXML
    private Text txtPostinumeroVaroitus;
    @FXML
    private Ellipse circlePostinumeroVaroitus;
    @FXML
    private Text txtPostitoimipaikkaVaroitus;
    @FXML
    private Ellipse circlePostitoimipaikkaVaroitus;
    @FXML
    private Text txtHuonenroVaroitus;
    @FXML
    private Ellipse circleHuonenroVaroitus;
    @FXML
    private Text txtPaivaVuokraVaroitus;
    @FXML
    private Ellipse circlePaivaVuokraVaroitus;
    @FXML
    private Text txtKokoVaroitus;
    @FXML
    private Ellipse circleKokoVaroitus;

    private boolean onHyva;
    @FXML
    private Text txtLaiteHintaVaroitus;
    @FXML
    private Ellipse circleLaiteHintaVaroitus;

    NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
    @FXML
    private Text txtSamaPisteVaroitus;
    @FXML
    private Text txtSamapalveluVaroitus;
    @FXML
    private Text txtSamalaiteVaroitus;
    @FXML
    private Text txtSamahuoneVaroitus;
    @FXML
    private Label txtVuokralainenOtsikko;
    @FXML
    private Text txtVuokralainen;
    @FXML
    private Label txtVarausValiOtsikko;
    @FXML
    private Text txtVarausVali;

    public void PaivitaAsiakasTab() {
        asiakas_box.getChildren().clear();
        A_nimi.clear();
        A_email.clear();
        A_osoite.clear();
        A_postinumero.clear();
        A_postipaikka.clear();
        A_puhelin.clear();
        A_laskutus.setValue(null);

        try {
            Connection conn = DatabaseConnection.openConnection();
            asiakasTable(haeKaikkiAsiakkaat(conn));
            DatabaseConnection.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(VarausJarjestelmaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LisaaAsiakas() throws SQLException {
        Connection c = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(c);
        A_laskutus.getItems().addAll(
                "Paperi",
                "Sähkö"
        );
        A_lisaa.setOnAction(event -> {
            if (A_nimi.getText().isEmpty() || A_email.getText().isEmpty() || A_osoite.getText().isEmpty() || A_postinumero.getText().isEmpty() || A_postipaikka.getText().isEmpty() || A_puhelin.getText().isEmpty()) {
                System.out.print("Kaikki tyhjää");
            } else if (A_laskutus.getValue() != "Paperi" && A_laskutus.getValue() != "Sähkö") {
                System.out.print("Kaikki vielä laskutustapa");
            } else {
                String Laskutus = "Sähkö";
                if (A_laskutus.getValue() == "Paperi") {
                    Laskutus = "Paperi";
                }
                System.out.print("Kaikki lisätty ja paperinen");
                try {
                    DatabaseConnection.useDatabase(c);
                    PreparedStatement ps = c.prepareStatement(
                            "INSERT INTO Asiakkaat (Nimi,Email,LaskutusTapa,Osoite,Postinumero,Postitoimipaikka,Puhelinumero) VALUES(?, ?, ?, ?, ?, ?, ?);"
                    );
                    ps.setString(1, A_nimi.getText().trim());
                    ps.setString(2, A_email.getText().trim());
                    ps.setString(3, Laskutus);
                    ps.setString(4, A_osoite.getText().trim());
                    ps.setInt(5, Integer.valueOf(A_postinumero.getText().trim()));
                    ps.setString(6, A_postipaikka.getText().trim());
                    ps.setString(7, A_puhelin.getText().trim());
                    ps.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(VarausJarjestelmaViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            PaivitaAsiakasTab();
        });

    }

    public void PoistaAsiakas(Connection c, int asiakasID) throws SQLException {
        try {
            DatabaseConnection.useDatabase(c);
            PreparedStatement ps = c.prepareStatement(
                    "DELETE FROM Asiakkaat WHERE AsiakasID = ?;"
            );
            ps.setInt(1, asiakasID);
            ps.execute();

        } catch (SQLException ex) {
            System.out.print("shit not working");
            Logger.getLogger(VarausJarjestelmaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PaivitaAsiakasTab();
    }

    public void Asiakas_muokkaus_container(Asiakas asiakas) {
        AM_nimi.setText(asiakas.getNimi());
        AM_email.setText(asiakas.getEmail());
        AM_osoite.setText(asiakas.getKatuosoite());
        AM_postinumero.setText("" + asiakas.getPostinumero());
        AM_postipaikka.setText(asiakas.getPostitoimipaikka());
        AM_puhelin.setText(asiakas.getPuhelinumero());
        AM_laskutus.setValue(asiakas.getLaskutustapa());
        AM_peruuta.setOnAction(event -> {
            A_muokkaa.setVisible(false);
            A_lisaa_uusi.setVisible(true);
        });

        AM_laheta.setOnAction(event -> {
            String Laskutus = "Sähkö";
            if (AM_laskutus.getValue() == "Paperi") {
                Laskutus = "Paperi";
            }
            MuokkaaAsiakas(new Asiakas(asiakas.getAsiakasID(), AM_nimi.getText(), AM_email.getText(), Laskutus, AM_osoite.getText(), Integer.valueOf(AM_postinumero.getText()), AM_postipaikka.getText(), AM_puhelin.getText()));
        });

    }

    public void MuokkaaAsiakas(Asiakas asiakas) {

        try {
            Connection conn = DatabaseConnection.openConnection();
            DatabaseConnection.useDatabase(conn);
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Asiakkaat SET Nimi = ?,Email = ?,LaskutusTapa = ?,Osoite = ?,Postinumero = ?,Postitoimipaikka = ?,Puhelinumero= ?  WHERE AsiakasID = ?;"
            );
            ps.setString(1, asiakas.getNimi());
            ps.setString(2, asiakas.getEmail());
            ps.setString(3, asiakas.getLaskutustapa());
            ps.setString(4, asiakas.getKatuosoite());
            ps.setInt(5, asiakas.getPostinumero());
            ps.setString(6, asiakas.getPostitoimipaikka());
            ps.setString(7, asiakas.getPuhelinumero());
            ps.setInt(8, asiakas.getAsiakasID());
            ps.execute();

            DatabaseConnection.closeConnection(conn);

        } catch (SQLException ex) {
            Logger.getLogger(VarausJarjestelmaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PaivitaAsiakasTab();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = DatabaseConnection.openConnection();
            LisaaAsiakas();
            haeKaikkiToimipisteet(conn);
            haeKaikkiToimitilat(conn);
            haeKaikkiPalvelut(conn);
            haeKaikkiLaitteet(conn);
            haeKaikkiAsiakkaat(conn);
            asiakkaat = haeKaikkiAsiakkaat(conn);
            haeVaratutToimitila(conn);
            asiakasTable(haeKaikkiAsiakkaat(conn));

            DatabaseConnection.closeConnection(conn);

            // postinumero kenttä ei ota vastaan muita kuin numeroita ja ei enempää kuin 5 numeroa
            txtPistePostiNumero.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txtPistePostiNumero.setText(oldValue);
                }
                if (newValue.length() > 5) {
                    txtPistePostiNumero.setText(oldValue);
                }
            });

            // osoite kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtPisteKatuOsoite.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtPisteKatuOsoite.setText(oldValue);
                }
            });

            // postitoimipaikka kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtPistePostitoimipaikka.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtPistePostitoimipaikka.setText(oldValue);
                }
            });

            // huonenumero kenttä ei ota vastaan muita kuin numeroita ja ei enempää kuin 3 numeroa
            txtTilaHuoneenNumero.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txtTilaHuoneenNumero.setText(oldValue);
                }
                if (newValue.length() > 3) {
                    txtTilaHuoneenNumero.setText(oldValue);
                }
            });

            // paivavuokra kenttä ei ota vastaan muita kuin numeroita ja yhden pilkun
            txtTilaPaivaVuokra.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d{0,10}([\\,\\.]\\d{0,10})?")) {
                    txtTilaPaivaVuokra.setText(oldValue);
                }
            });

            // tilan koko kenttä ei ota vastaan muita kuin numeroita ja yhden pilkun
            txtTilaKoko.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d{0,10}([\\,\\.]\\d{0,10})?")) {
                    txtTilaKoko.setText(oldValue);
                }
            });

            // laitteen päivähinta kenttä ei ota vastaan muita kuin numeroita ja yhden pilkun
            txtLaitePaivaHinta.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d{0,10}([\\,\\.]\\d{0,10})?")) {
                    txtLaitePaivaHinta.setText(oldValue);
                }
            });

            // laitteen nimi kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtLaiteNimi.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtLaiteNimi.setText(oldValue);
                }
            });

            // palvelun päivähinta kenttä ei ota vastaan muita kuin numeroita ja yhden pilkun
            txtPalveluPaivaHinta.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d{0,10}([\\,\\.]\\d{0,10})?")) {
                    txtPalveluPaivaHinta.setText(oldValue);
                }
            });

            // palvelun nimi kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtPalveluNimi.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtPalveluNimi.setText(oldValue);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(VarausJarjestelmaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void asiakasTable(ArrayList<Asiakas> asiakkaat) {
        A_muokkaa.setVisible(false);
        A_lisaa_uusi.setVisible(true);
        ArrayList<Label> A_otsikot = new ArrayList<Label>();

        A_otsikot.add(new Label("ID"));
        A_otsikot.add(new Label("Nimi"));
        A_otsikot.add(new Label("Email"));
        A_otsikot.add(new Label("LaskutusTapa"));
        A_otsikot.add(new Label("Osoite"));
        A_otsikot.add(new Label("Postinumero"));
        A_otsikot.add(new Label("Postitoimipaikka"));
        A_otsikot.add(new Label("Puhelinnumero"));
        for (int i = 0; i < 8; i++) {
            asiakas_box.add(A_otsikot.get(i), i, 0);
            GridPane.setHalignment(A_otsikot.get(i), HPos.CENTER);

        }
        try {
            for (int i = 0; i < asiakkaat.size(); i++) {
                ArrayList<Label> A_tietue = new ArrayList<Label>();
                A_tietue.add(new Label("" + asiakkaat.get(i).getAsiakasID()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getNimi()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getEmail()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getLaskutustapa()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getKatuosoite()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getPostinumero()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getPostitoimipaikka()));
                A_tietue.add(new Label("" + asiakkaat.get(i).getPuhelinumero()));
                Button muokkaa = new Button("Muokkaa");
                Button poista = new Button("Poista");
                VBox m_nappi = new VBox(muokkaa);
                VBox p_nappi = new VBox(poista);
                p_nappi.setAlignment(Pos.CENTER);
                m_nappi.setAlignment(Pos.CENTER);
                int id = asiakkaat.get(i).getAsiakasID();
                Asiakas asiakas = asiakkaat.get(i);
                muokkaa.setOnAction(event -> {
                    A_lisaa_uusi.setVisible(false);
                    A_muokkaa.setVisible(true);
                    Asiakas_muokkaus_container(asiakas);
                });
                poista.setOnAction(event -> {
                    try {
                        Connection conn = DatabaseConnection.openConnection();
                        PoistaAsiakas(conn, id);
                    } catch (SQLException ex) {
                        Logger.getLogger(VarausJarjestelmaViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                asiakas_box.add(m_nappi, 8, i + 1);
                asiakas_box.add(p_nappi, 9, i + 1);
                for (int j = 0; j < A_tietue.size(); j++) {

                    asiakas_box.add(A_tietue.get(j), j, i + 1);
                    GridPane.setHalignment(A_tietue.get(j), HPos.CENTER);
                }
            }
        } catch (NullPointerException n) {
            System.out.println("Asiakkaat tyhjänä");
        }
    }

    public ArrayList<Asiakas> haeKaikkiAsiakkaat(Connection c) throws SQLException {
        ArrayList<Asiakas> asiakkaat = new ArrayList<>();

        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        String query = "SELECT AsiakasID,Nimi,Email,LaskutusTapa,Osoite,Postinumero,Postitoimipaikka,Puhelinumero FROM Asiakkaat";
        //String query = "SELECT * FROM Asiakkaat";

        ResultSet rs = stmt.executeQuery(query);

        //System.out.print("" + rs.next());
        while (rs.next()) {

            Asiakas asiakas = new Asiakas(rs.getInt("AsiakasID"), rs.getString("Nimi"), rs.getString("Email"), rs.getString("LaskutusTapa"), rs.getString("Osoite"), rs.getInt("Postinumero"), rs.getString("Postitoimipaikka"), rs.getString("Puhelinumero"));

            asiakkaat.add(asiakas);
        }
        return asiakkaat;
    }

    public void haeKaikkiToimipisteet(Connection c) throws SQLException {

        toimipisteet.clear();
        cboToimipisteet.getItems().clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT Osoite, ToimipisteID, Postinumero, Postitoimipaikka FROM Toimipiste;"
        );

        while (rs.next()) {
            Toimipiste toimipiste = new Toimipiste();
            toimipiste.setKatuosoite(rs.getString("Osoite"));
            toimipiste.setToimipisteID(rs.getInt("ToimipisteID"));
            toimipiste.setPostinumero(Integer.toString(rs.getInt("Postinumero")));
            toimipiste.setPostitoimipaikka(rs.getString("Postitoimipaikka"));
            toimipisteet.add(toimipiste);
        }
        try {
            for (int i = 0; i < toimipisteet.size(); i++) {
                cboToimipisteet.getItems().add(toimipisteet.get(i).getPostitoimipaikka());
            }
        } catch (NullPointerException n) {
            System.out.println("Toimipisteet tyhjänä");
        }
    }

    public void haeKaikkiToimitilat(Connection c) throws SQLException {

        toimistotilat.clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT PaivaVuokra, Koko, ToimitilaID, ToimipisteID, Huonenro FROM Toimitilat;"
        );

        while (rs.next()) {
            ToimistoTila toimistotila = new ToimistoTila();
            toimistotila.setPaivaVuokra(rs.getFloat("PaivaVuokra"));
            toimistotila.setKoko(rs.getFloat("Koko"));
            toimistotila.setToimistotilaID(rs.getInt("ToimitilaID"));
            toimistotila.setToimipisteID(rs.getInt("ToimipisteID"));
            toimistotila.setHuonenro(rs.getString("Huonenro"));
            toimistotilat.add(toimistotila);
        }
    }

    public void haeKaikkiPalvelut(Connection c) throws SQLException {

        palvelut.clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT PalveluID, Nimi, PaivaHinta, ToimipisteID FROM Palvelut;"
        );

        while (rs.next()) {
            Palvelu palvelu = new Palvelu();
            palvelu.setPalveluID(rs.getInt("PalveluID"));
            palvelu.setNimi(rs.getString("Nimi"));
            palvelu.setPaivaHinta(rs.getDouble("PaivaHinta"));
            palvelu.setToimipisteID(rs.getInt("ToimipisteID"));
            palvelut.add(palvelu);
        }
    }

    public void haeKaikkiLaitteet(Connection c) throws SQLException {

        laitteet.clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT LaiteID, Nimi, PaivaHinta, ToimipisteID FROM Laitteet;"
        );

        while (rs.next()) {
            Laite laite = new Laite();
            laite.setLaiteID(rs.getInt("LaiteID"));
            laite.setNimi(rs.getString("Nimi"));
            laite.setPaivaHinta(rs.getDouble("PaivaHinta"));
            laite.setToimipisteID(rs.getInt("ToimipisteID"));
            laitteet.add(laite);
        }
    }

    public void haeVaratutToimitila(Connection c) throws SQLException {
        varatutToimitilat.clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT VarausID, AsiakasID, ToimitilaID, AlkuPvm, LoppuPvm FROM Varaus \n"
                + "WHERE Varaus.LoppuPvm > CURRENT_DATE;"
        );
        while (rs.next()) {
            Varaus varaus = new Varaus();
            varaus.setVarausID(rs.getInt("VarausID"));
            varaus.setAsiakasID(rs.getInt("AsiakasID"));
            varaus.setToimitilaID(rs.getInt("ToimitilaID"));
            varaus.setAlkupvm(rs.getDate("AlkuPvm"));
            varaus.setLoppupvm(rs.getDate("LoppuPvm"));
            varatutToimitilat.add(varaus);
        }
    }

    @FXML
    private void btnToimiPisteLisaaPressed(ActionEvent event) {
        txtPisteKatuOsoite.clear();
        txtPistePostiNumero.clear();
        txtPistePostitoimipaikka.clear();
        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboPalvelut.setDisable(true);
        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);
        txtPisteKatuOsoite.setEditable(true);
        txtPistePostiNumero.setEditable(true);
        txtPistePostitoimipaikka.setEditable(true);
        btnToimiPisteTallenna.setVisible(true);
        btnToimiPisteTallenna.setDisable(false);
        btnToimiPistePeru.setVisible(true);
        btnToimiPistePeru.setDisable(false);
        lisaaPainettu = true;

    }

    @FXML
    private void btnToimiPisteMuokkaaPressed(ActionEvent event) {
        muokkaaPainettu = true;
        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboPalvelut.setDisable(true);
        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);
        txtPisteKatuOsoite.setEditable(true);
        txtPistePostiNumero.setEditable(true);
        txtPistePostitoimipaikka.setEditable(true);
        btnToimiPisteTallenna.setVisible(true);
        btnToimiPisteTallenna.setDisable(false);
        btnToimiPistePeru.setVisible(true);
        btnToimiPistePeru.setDisable(false);
    }

    @FXML
    private void btnToimiPistePoistaPressed(ActionEvent event) throws SQLException {

        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        Alert toimipistePoisto = new Alert(Alert.AlertType.CONFIRMATION);
        toimipistePoisto.setTitle("Toimipisteen " + cboToimipisteet.getValue() + " poisto");
        toimipistePoisto.setHeaderText("Olet poistamassa toimipistettä " + cboToimipisteet.getValue() + "!");
        toimipistePoisto.setContentText("Oletko varma että haluat poistaa toimipisteen?");

        Optional<ButtonType> vastaus = toimipistePoisto.showAndWait();
        if (vastaus.get() == ButtonType.OK) {
            for (int i = 0; i < toimipisteet.size(); i++) {
                if (toimipisteet.get(i).getKatuosoite().equals(txtPisteKatuOsoite.getText())) {
                    toimipisteet.remove(i);
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Toimipiste WHERE Osoite = ?;"
            );
            ps.setString(1, txtPisteKatuOsoite.getText().trim());
            ps.execute();

            cboToimipisteet.getSelectionModel().clearSelection();
            cboToimipisteet.getItems().clear();
            haeKaikkiToimipisteet(conn);
            cboToimipisteet.getSelectionModel().selectFirst();
        }

    }

    @FXML
    private void btnToimiPisteTallennaPressed(ActionEvent event) throws SQLException {

        int toimipisteIndeksi = 0;
        onHyva = true;
        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);

        if (lisaaPainettu == true) {
            toimipisteIndeksi = cboToimipisteet.getItems().size();
            Toimipiste toimipiste = new Toimipiste();
            for (int i = 0; i < toimipisteet.size(); i++) {
                if (toimipisteet.get(i).getKatuosoite().equals(txtPisteKatuOsoite.getText().trim()) && toimipisteet.get(i).getPostitoimipaikka().equals(txtPistePostitoimipaikka.getText().trim())) {
                    txtSamaPisteVaroitus.setText("Toimipiste on jo olemassa!");
                    txtSamaPisteVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtSamaPisteVaroitus.setVisible(false);
                }
            }
            if (txtPisteKatuOsoite.getText().isEmpty()) {
                txtKatuOsoiteVaroitus.setText("Osoite on tyhjä!");
                txtKatuOsoiteVaroitus.setVisible(true);
                circleKatuOsoiteVaroitus.setVisible(true);
                onHyva = false;
            } else {
                toimipiste.setKatuosoite(txtPisteKatuOsoite.getText().trim());
                txtKatuOsoiteVaroitus.setVisible(false);
                circleKatuOsoiteVaroitus.setVisible(false);
            }
            if (txtPistePostiNumero.getText().isEmpty()) {
                txtPostinumeroVaroitus.setText("Postinumero on tyhjä!");
                txtPostinumeroVaroitus.setVisible(true);
                circlePostinumeroVaroitus.setVisible(true);
                onHyva = false;
            } else {
                toimipiste.setPostinumero(txtPistePostiNumero.getText().trim());
                txtPostinumeroVaroitus.setVisible(false);
                circlePostinumeroVaroitus.setVisible(false);
            }
            if (txtPistePostitoimipaikka.getText().isEmpty()) {
                txtPostitoimipaikkaVaroitus.setText("Postitoimipaikka on tyhjä!");
                txtPostitoimipaikkaVaroitus.setVisible(true);
                circlePostitoimipaikkaVaroitus.setVisible(true);
                onHyva = false;
            } else {
                toimipiste.setPostitoimipaikka(txtPistePostitoimipaikka.getText().trim());
                txtPostitoimipaikkaVaroitus.setVisible(false);
                circlePostitoimipaikkaVaroitus.setVisible(false);
            }
            if (onHyva == false) {
                return;
            } else {
                toimipisteet.add(toimipiste);

                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Toimipiste (Osoite, Postinumero, Postitoimipaikka) VALUES(?, ?, ?);"
                );
                ps.setString(1, toimipiste.getKatuosoite());
                ps.setInt(2, Integer.valueOf(toimipiste.getPostinumero()));
                ps.setString(3, toimipiste.getPostitoimipaikka());
                ps.execute();
                txtSamaPisteVaroitus.setVisible(false);
                onHyva = true;
                lisaaPainettu = false;
            }

        } else if (muokkaaPainettu == true) {
            toimipisteIndeksi = cboToimipisteet.getSelectionModel().getSelectedIndex();
            int toimipisteID = 0;
            for (int i = 0; i < toimipisteet.size(); i++) {
                if (toimipisteet.get(i).getPostitoimipaikka().equals(cboToimipisteet.getValue())) {

                    if (txtPisteKatuOsoite.getText().isEmpty()) {
                        txtKatuOsoiteVaroitus.setText("Osoite on tyhjä!");
                        txtKatuOsoiteVaroitus.setVisible(true);
                        circleKatuOsoiteVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        toimipisteet.get(i).setKatuosoite(txtPisteKatuOsoite.getText());
                        txtKatuOsoiteVaroitus.setVisible(false);
                        circleKatuOsoiteVaroitus.setVisible(false);
                    }
                    if (txtPistePostiNumero.getText().isEmpty()) {
                        txtPostinumeroVaroitus.setText("Postinumero on tyhjä!");
                        txtPostinumeroVaroitus.setVisible(true);
                        circlePostinumeroVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        toimipisteet.get(i).setPostinumero(txtPistePostiNumero.getText());
                        txtPostinumeroVaroitus.setVisible(false);
                        circlePostinumeroVaroitus.setVisible(false);
                    }
                    if (txtPistePostitoimipaikka.getText().isEmpty()) {
                        txtPostitoimipaikkaVaroitus.setText("Postitoimipaikka on tyhjä!");
                        txtPostitoimipaikkaVaroitus.setVisible(true);
                        circlePostitoimipaikkaVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        toimipisteet.get(i).setPostitoimipaikka(txtPistePostitoimipaikka.getText());
                        txtPostitoimipaikkaVaroitus.setVisible(false);
                        circlePostitoimipaikkaVaroitus.setVisible(false);
                    }
                    if (onHyva == false) {
                        return;
                    } else {
                        toimipisteID = toimipisteet.get(i).getToimipisteID();
                        break;
                    }
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Toimipiste SET Osoite = ?, Postinumero = ?, Postitoimipaikka = ? WHERE ToimipisteID = ?;"
            );
            ps.setString(1, txtPisteKatuOsoite.getText().trim());
            ps.setInt(2, Integer.valueOf(txtPistePostiNumero.getText().trim()));
            ps.setString(3, txtPistePostitoimipaikka.getText().trim());
            ps.setInt(4, toimipisteID);
            ps.execute();
            txtSamaPisteVaroitus.setVisible(false);
            onHyva = true;
            muokkaaPainettu = false;
        }

        lisaaPainettu = false;
        muokkaaPainettu = false;
        btnToimiPistePeru.setDisable(true);
        btnToimiPistePeru.setVisible(false);
        btnToimiPisteTallenna.setDisable(true);
        btnToimiPisteTallenna.setVisible(false);
        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboPalvelut.setDisable(false);
        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);
        txtPisteKatuOsoite.setEditable(false);
        txtPistePostiNumero.setEditable(false);
        txtPistePostitoimipaikka.setEditable(false);

        haeKaikkiToimipisteet(conn);
        cboToimipisteet.getSelectionModel().clearSelection();
        cboToimipisteet.getSelectionModel().select(toimipisteIndeksi);
    }

    @FXML
    private void btnToimiPistePeruPressed(ActionEvent event) {
        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboPalvelut.setDisable(false);
        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);
        txtPisteKatuOsoite.setEditable(false);
        txtPistePostiNumero.setEditable(false);
        txtPistePostitoimipaikka.setEditable(false);
        btnToimiPisteTallenna.setVisible(false);
        btnToimiPisteTallenna.setDisable(true);
        btnToimiPistePeru.setVisible(false);
        btnToimiPistePeru.setDisable(true);
        txtSamaPisteVaroitus.setVisible(false);
        lisaaPainettu = false;
        muokkaaPainettu = false;
    }

    @FXML
    private void cboToimiPisteValittu(ActionEvent event) throws InterruptedException, SQLException {
        cboToimistoTilat.getItems().clear();
        cboPalvelut.getItems().clear();
        cboLaitteet.getItems().clear();

        txtTilaHuoneenNumero.clear();
        txtTilaKoko.clear();
        txtTilaPaivaVuokra.clear();

        txtPalveluNimi.clear();
        txtPalveluPaivaHinta.clear();
        txtLaiteNimi.clear();
        txtLaitePaivaHinta.clear();

        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(false);
        btnPalveluLisaa.setDisable(false);
        btnLaiteLisaa.setDisable(false);

        try {
            for (int i = 0; i < toimipisteet.size(); i++) {
                if (cboToimipisteet.getValue().equals(toimipisteet.get(i).getPostitoimipaikka())) {
                    txtPisteKatuOsoite.setText(toimipisteet.get(i).getKatuosoite());
                    txtPistePostiNumero.setText(toimipisteet.get(i).getPostinumero());
                    txtPistePostitoimipaikka.setText(toimipisteet.get(i).getPostitoimipaikka());

                    btnToimiPisteMuokkaa.setDisable(false);
                    btnToimiPistePoista.setDisable(false);

                    valittuToimipisteID = toimipisteet.get(i).getToimipisteID();

                    for (int j = 0; j < toimistotilat.size(); j++) {
                        if (toimistotilat.get(j).getToimipisteID() == valittuToimipisteID) {
                            cboToimistoTilat.setDisable(false);
                            cboToimistoTilat.getItems().add(toimistotilat.get(j).getHuonenro() + " ");
                            btnToimistoTilaLisaa.setDisable(false);
                            for (int h = 0; h < varatutToimitilat.size(); h++) {
                                if (varatutToimitilat.get(h).getToimitilaID() == toimistotilat.get(j).getToimistotilaID()) {
                                    for (int g = 0; g < cboToimistoTilat.getItems().size(); g++) {
                                        if (cboToimistoTilat.getItems().get(g).startsWith(toimistotilat.get(j).getHuonenro() + " ")) {
                                            cboToimistoTilat.getItems().set(g, toimistotilat.get(j).getHuonenro() + " VARATTU " + varatutToimitilat.get(h).getLoppupvm().toLocalDate() + " asti");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for (int k = 0; k < palvelut.size(); k++) {
                        if (palvelut.get(k).getToimipisteID() == valittuToimipisteID) {
                            cboPalvelut.setDisable(false);
                            cboPalvelut.getItems().add(palvelut.get(k).getNimi());
                            btnPalveluLisaa.setDisable(false);
                        }
                    }
                    for (int l = 0; l < laitteet.size(); l++) {
                        if (laitteet.get(l).getToimipisteID() == valittuToimipisteID) {
                            cboLaitteet.setDisable(false);
                            cboLaitteet.getItems().add(laitteet.get(l).getNimi());
                            btnLaiteLisaa.setDisable(false);
                        }
                    }

                    break;
                }
            }
        } catch (NullPointerException n) {

        }
    }

    @FXML
    private void cboToimistoTilaValittu(ActionEvent event) {
        try {
            for (int i = 0; i < toimistotilat.size(); i++) {
                if (cboToimistoTilat.getValue().startsWith(toimistotilat.get(i).getHuonenro() + " ") && toimistotilat.get(i).getToimipisteID() == valittuToimipisteID) {
                    txtTilaPaivaVuokra.setText(String.valueOf(nf.format(toimistotilat.get(i).getPaivaVuokra())));
                    txtTilaKoko.setText(String.valueOf(nf.format(toimistotilat.get(i).getKoko())));
                    txtTilaHuoneenNumero.setText(toimistotilat.get(i).getHuonenro());

                    valittuToimitilaID = toimistotilat.get(i).getToimistotilaID();

                    if (cboToimistoTilat.getValue().contains("VARATTU")) {
                        for (int j = 0; j < varatutToimitilat.size(); j++) {
                            if (varatutToimitilat.get(j).getToimitilaID() == toimistotilat.get(i).getToimistotilaID()) {
                                for (int h = 0; h < asiakkaat.size(); h++) {
                                    if (asiakkaat.get(h).getAsiakasID() == varatutToimitilat.get(j).getAsiakasID()) {
                                        txtVuokralainen.setText(asiakkaat.get(h).getNimi());
                                    }
                                }
                                txtVarausVali.setText(String.valueOf(varatutToimitilat.get(j).getLoppupvm().toLocalDate()) + " asti");
                                txtVuokralainenOtsikko.setVisible(true);
                                txtVuokralainen.setVisible(true);
                                txtVarausValiOtsikko.setVisible(true);
                                txtVarausVali.setVisible(true);
                            }
                        }
                        btnToimistoTilaMuokkaa.setDisable(true);
                        btnToimistoTilaPoista.setDisable(true);
                        btnTilaLisaaAsiakas.setDisable(true);
                        break;
                    } else {
                        txtVuokralainenOtsikko.setVisible(false);
                        txtVuokralainen.setVisible(false);
                        txtVarausValiOtsikko.setVisible(false);
                        txtVarausVali.setVisible(false);
                        btnToimistoTilaMuokkaa.setDisable(false);
                        btnToimistoTilaPoista.setDisable(false);
                        btnTilaLisaaAsiakas.setDisable(false);
                        break;
                    }
                }
            }
        } catch (NullPointerException n) {
        }
    }

    @FXML
    private void cboPalveluValittu(ActionEvent event) {
        try {
            for (int i = 0; i < palvelut.size(); i++) {
                if (cboPalvelut.getValue().equals(palvelut.get(i).getNimi()) && palvelut.get(i).getToimipisteID() == valittuToimipisteID) {
                    txtPalveluNimi.setText(palvelut.get(i).getNimi());
                    txtPalveluPaivaHinta.setText(String.valueOf(nf.format(palvelut.get(i).getPaivaHinta())));

                    valittuPalveluID = palvelut.get(i).getPalveluID();

                    btnPalveluMuokkaa.setDisable(false);
                    btnPalveluPoista.setDisable(false);
                    break;
                }
            }
        } catch (NullPointerException n) {
        }
    }

    @FXML
    private void cboLaiteValittu(ActionEvent event) {

        try {
            for (int i = 0; i < laitteet.size(); i++) {
                if (cboLaitteet.getValue().equals(laitteet.get(i).getNimi()) && laitteet.get(i).getToimipisteID() == valittuToimipisteID) {
                    txtLaiteNimi.setText(laitteet.get(i).getNimi());
                    txtLaitePaivaHinta.setText(String.valueOf(nf.format(laitteet.get(i).getPaivaHinta())));

                    valittuLaiteID = laitteet.get(i).getLaiteID();

                    btnLaiteMuokkaa.setDisable(false);
                    btnLaitePoista.setDisable(false);
                    break;
                }
            }
        } catch (NullPointerException n) {
        }
    }

    @FXML
    private void btnToimistoTilaLisaaPressed(ActionEvent event) {

        lisaaPainettu = true;

        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboLaitteet.setDisable(true);
        cboPalvelut.setDisable(true);

        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);

        btnLaiteLisaa.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(true);
        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnTilaLisaaAsiakas.setDisable(true);

        txtTilaHuoneenNumero.setEditable(true);
        txtTilaPaivaVuokra.setEditable(true);
        txtTilaKoko.setEditable(true);

        btnToimiTilaTallenna.setVisible(true);
        btnToimiTilaTallenna.setDisable(false);
        btnToimiTilaPeru.setVisible(true);
        btnToimiTilaPeru.setDisable(false);

        txtVuokralainenOtsikko.setVisible(false);
        txtVuokralainen.setVisible(false);
        txtVarausValiOtsikko.setVisible(false);
        txtVarausVali.setVisible(false);
    }

    @FXML
    private void btnToimistoTilaMuokkaaPressed(ActionEvent event
    ) {

        muokkaaPainettu = true;

        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboLaitteet.setDisable(true);
        cboPalvelut.setDisable(true);

        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);

        btnLaiteLisaa.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(true);
        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnTilaLisaaAsiakas.setDisable(true);

        txtTilaHuoneenNumero.setEditable(true);
        txtTilaPaivaVuokra.setEditable(true);
        txtTilaKoko.setEditable(true);

        btnToimiTilaTallenna.setVisible(true);
        btnToimiTilaTallenna.setDisable(false);
        btnToimiTilaPeru.setVisible(true);
        btnToimiTilaPeru.setDisable(false);
    }

    @FXML
    private void btnToimistoTilaPoistaPressed(ActionEvent event) throws SQLException {

        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        Alert toimistotilaPoisto = new Alert(Alert.AlertType.CONFIRMATION);
        toimistotilaPoisto.setTitle("Toimistotilan " + cboToimistoTilat.getValue() + " poisto");
        toimistotilaPoisto.setHeaderText("Olet poistamassa toimistotilaa " + cboToimistoTilat.getValue() + "!");
        toimistotilaPoisto.setContentText("Oletko varma että haluat poistaa toimistotilan?");

        Optional<ButtonType> vastaus = toimistotilaPoisto.showAndWait();
        if (vastaus.get() == ButtonType.OK) {
            for (int i = 0; i < toimistotilat.size(); i++) {
                if (toimistotilat.get(i).getToimistotilaID() == valittuToimitilaID) {
                    toimistotilat.remove(i);
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Toimitilat WHERE ToimitilaID = ?;"
            );
            ps.setInt(1, valittuToimitilaID);
            ps.execute();
        }
        haeKaikkiToimitilat(conn);
        cboToimistoTilat.getItems().clear();
        for (int j = 0; j < toimistotilat.size(); j++) {
            if (toimistotilat.get(j).getToimipisteID() == valittuToimipisteID) {
                cboToimistoTilat.setDisable(false);
                cboToimistoTilat.getItems().add(toimistotilat.get(j).getHuonenro());
                btnToimistoTilaLisaa.setDisable(false);
            }
        }
        cboToimistoTilat.getSelectionModel().selectFirst();
    }

    @FXML
    private void btnToimiTilaTallennaPressed(ActionEvent event) throws SQLException {

        int toimipisteIndeksi = cboToimipisteet.getSelectionModel().getSelectedIndex();
        int toimistotilanIndeksi = 0;
        onHyva = true;
        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        if (lisaaPainettu == true) {
            toimistotilanIndeksi = cboToimistoTilat.getItems().size();
            ToimistoTila toimistotila = new ToimistoTila();

            for (int i = 0; i < cboToimistoTilat.getItems().size(); i++) {
                if (cboToimistoTilat.getItems().get(i).equals(txtTilaHuoneenNumero.getText().trim() + " ")) {
                    txtSamahuoneVaroitus.setText("Huone on jo olemassa!");
                    txtSamahuoneVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtSamahuoneVaroitus.setVisible(false);
                }
            }
            if (txtTilaPaivaVuokra.getText().isEmpty()) {
                txtPaivaVuokraVaroitus.setText("Vuokra on tyhjä!");
                txtPaivaVuokraVaroitus.setVisible(true);
                circlePaivaVuokraVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtTilaPaivaVuokra.setText(txtTilaPaivaVuokra.getText().replace(',', '.'));
                toimistotila.setPaivaVuokra(Double.valueOf(txtTilaPaivaVuokra.getText().trim()));
                txtPaivaVuokraVaroitus.setVisible(false);
                circlePaivaVuokraVaroitus.setVisible(false);
            }
            if (txtTilaKoko.getText().isEmpty()) {
                txtKokoVaroitus.setText("Koko on tyhjä!");
                txtKokoVaroitus.setVisible(true);
                circleKokoVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtTilaKoko.setText(txtTilaKoko.getText().replace(',', '.'));
                toimistotila.setKoko(Double.valueOf(txtTilaKoko.getText().trim()));
                txtKokoVaroitus.setVisible(false);
                circleKokoVaroitus.setVisible(false);
            }
            if (txtTilaHuoneenNumero.getText().isEmpty()) {
                txtHuonenroVaroitus.setText("Huoneen numero on tyhjä!");
                txtHuonenroVaroitus.setVisible(true);
                circleHuonenroVaroitus.setVisible(true);
                onHyva = false;
            } else {
                toimistotila.setHuonenro(txtTilaHuoneenNumero.getText().trim());
                txtKokoVaroitus.setVisible(false);
                circleKokoVaroitus.setVisible(false);
            }
            if (onHyva == false) {
                return;
            } else {
                toimistotila.setToimipisteID(valittuToimipisteID);
                toimistotilat.add(toimistotila);
            }
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Toimitilat (PaivaVuokra, Koko, ToimipisteID, Huonenro) VALUES(?, ?, ?, ?);"
            );
            ps.setDouble(1, toimistotila.getPaivaVuokra());
            ps.setDouble(2, toimistotila.getKoko());
            ps.setInt(3, toimistotila.getToimipisteID());
            ps.setString(4, toimistotila.getHuonenro());
            ps.execute();

            txtSamahuoneVaroitus.setVisible(false);
            onHyva = true;
            lisaaPainettu = false;

        } else if (muokkaaPainettu == true) {
            toimistotilanIndeksi = cboToimistoTilat.getSelectionModel().getSelectedIndex();
            int toimitilaID = 0;
            for (int i = 0; i < toimistotilat.size(); i++) {
                if (toimistotilat.get(i).getHuonenro().equals(cboToimistoTilat.getValue().trim()) && toimistotilat.get(i).getToimipisteID() == valittuToimipisteID) {

                    if (txtTilaHuoneenNumero.getText().isEmpty()) {
                        txtHuonenroVaroitus.setText("Huoneen numero on tyhjä!");
                        txtHuonenroVaroitus.setVisible(true);
                        circleHuonenroVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        toimistotilat.get(i).setHuonenro(txtTilaHuoneenNumero.getText().trim());
                        txtHuonenroVaroitus.setVisible(false);
                        circleHuonenroVaroitus.setVisible(false);
                    }
                    if (txtTilaKoko.getText().isEmpty()) {
                        txtKokoVaroitus.setText("Koko on tyhjä!");
                        txtKokoVaroitus.setVisible(true);
                        circleKokoVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        txtTilaKoko.setText(txtTilaKoko.getText().replace(',', '.'));
                        toimistotilat.get(i).setKoko(Double.valueOf(txtTilaKoko.getText().trim()));
                        txtKokoVaroitus.setVisible(false);
                        circleKokoVaroitus.setVisible(false);
                    }
                    if (txtTilaPaivaVuokra.getText().isEmpty()) {
                        txtPaivaVuokraVaroitus.setText("Vuokra on tyhjä!");
                        txtPaivaVuokraVaroitus.setVisible(true);
                        circlePaivaVuokraVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        txtTilaPaivaVuokra.setText(txtTilaPaivaVuokra.getText().replace(',', '.'));
                        toimistotilat.get(i).setPaivaVuokra(Double.valueOf(txtTilaPaivaVuokra.getText().trim()));
                        txtPaivaVuokraVaroitus.setVisible(false);
                        circlePaivaVuokraVaroitus.setVisible(false);
                    }
                    if (onHyva == false) {
                        return;
                    } else {
                        toimitilaID = toimistotilat.get(i).getToimistotilaID();
                        break;
                    }
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Toimitilat SET PaivaVuokra = ?, Koko = ?, Huonenro = ? WHERE ToimitilaID = ?;"
            );
            ps.setDouble(1, Double.valueOf(txtTilaPaivaVuokra.getText().trim()));
            ps.setDouble(2, Double.valueOf(txtTilaKoko.getText().trim()));
            ps.setString(3, txtTilaHuoneenNumero.getText().trim());
            ps.setInt(4, toimitilaID);
            ps.execute();

            onHyva = true;
            muokkaaPainettu = false;
        }

        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboLaitteet.setDisable(false);
        cboPalvelut.setDisable(false);

        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);

        btnPalveluLisaa.setDisable(false);
        btnPalveluMuokkaa.setDisable(false);
        btnPalveluPoista.setDisable(false);

        btnLaiteLisaa.setDisable(false);
        btnLaiteMuokkaa.setDisable(false);
        btnLaitePoista.setDisable(false);

        btnToimistoTilaLisaa.setDisable(false);
        btnToimistoTilaMuokkaa.setDisable(false);
        btnToimistoTilaPoista.setDisable(false);
        btnTilaLisaaAsiakas.setDisable(false);

        txtTilaHuoneenNumero.setEditable(false);
        txtTilaPaivaVuokra.setEditable(false);
        txtTilaKoko.setEditable(false);

        btnToimiTilaTallenna.setVisible(false);
        btnToimiTilaTallenna.setDisable(true);
        btnToimiTilaPeru.setVisible(false);
        btnToimiTilaPeru.setDisable(true);

        haeKaikkiToimitilat(conn);
        haeVaratutToimitila(conn);
        cboToimistoTilat.getItems().clear();
        cboToimipisteet.getSelectionModel().clearSelection();
        cboToimipisteet.getSelectionModel().select(toimipisteIndeksi);
        cboToimistoTilat.getSelectionModel().select(toimistotilanIndeksi);
    }

    @FXML
    private void btnToimiTilaPeruPressed(ActionEvent event) {

        lisaaPainettu = false;
        muokkaaPainettu = false;

        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboLaitteet.setDisable(false);
        cboPalvelut.setDisable(false);

        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);

        btnPalveluLisaa.setDisable(false);

        btnLaiteLisaa.setDisable(false);

        btnToimistoTilaLisaa.setDisable(false);

        txtTilaHuoneenNumero.setEditable(false);
        txtTilaPaivaVuokra.setEditable(false);
        txtTilaKoko.setEditable(false);

        btnToimiTilaTallenna.setVisible(false);
        btnToimiTilaTallenna.setDisable(true);
        btnToimiTilaPeru.setVisible(false);
        btnToimiTilaPeru.setDisable(true);

        txtSamahuoneVaroitus.setVisible(false);
    }

    @FXML
    private void btnTilaLisaaAsiakasPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VarausView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Varaus");
        stage.show();
    }

    @FXML
    private void btnPalveluLisaaPressed(ActionEvent event) {

        lisaaPainettu = true;

        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboLaitteet.setDisable(true);
        cboPalvelut.setDisable(true);

        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);

        btnLaiteLisaa.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(true);
        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnTilaLisaaAsiakas.setDisable(true);

        txtPalveluNimi.setEditable(true);
        txtPalveluPaivaHinta.setEditable(true);

        btnPalveluTallenna.setVisible(true);
        btnPalveluTallenna.setDisable(false);
        btnPalveluPeru.setVisible(true);
        btnPalveluPeru.setDisable(false);
    }

    @FXML
    private void btnPalveluMuokkaaPressed(ActionEvent event
    ) {

        muokkaaPainettu = true;

        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboLaitteet.setDisable(true);
        cboPalvelut.setDisable(true);

        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);

        btnLaiteLisaa.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(true);
        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnTilaLisaaAsiakas.setDisable(true);

        txtPalveluNimi.setEditable(true);
        txtPalveluPaivaHinta.setEditable(true);

        btnPalveluTallenna.setVisible(true);
        btnPalveluTallenna.setDisable(false);
        btnPalveluPeru.setVisible(true);
        btnPalveluPeru.setDisable(false);
    }

    @FXML
    private void btnPalveluPoistaPressed(ActionEvent event) throws SQLException {

        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        Alert palveluPoisto = new Alert(Alert.AlertType.CONFIRMATION);
        palveluPoisto.setTitle("Palvelun " + cboPalvelut.getValue() + " poisto");
        palveluPoisto.setHeaderText("Olet poistamassa palvelua " + cboPalvelut.getValue() + "!");
        palveluPoisto.setContentText("Oletko varma että haluat poistaa palvelun?");

        Optional<ButtonType> vastaus = palveluPoisto.showAndWait();
        if (vastaus.get() == ButtonType.OK) {
            for (int i = 0; i < palvelut.size(); i++) {
                if (palvelut.get(i).getPalveluID() == valittuPalveluID) {
                    palvelut.remove(i);
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Palvelut WHERE PalveluID = ?;"
            );
            ps.setInt(1, valittuPalveluID);
            ps.execute();
        }
        haeKaikkiPalvelut(conn);
        cboPalvelut.getItems().clear();
        for (int j = 0; j < palvelut.size(); j++) {
            if (palvelut.get(j).getToimipisteID() == valittuToimipisteID) {
                cboPalvelut.setDisable(false);
                cboPalvelut.getItems().add(palvelut.get(j).getNimi());
                btnPalveluLisaa.setDisable(false);
            }
        }
        cboPalvelut.getSelectionModel().selectFirst();
    }

    @FXML
    private void btnPalveluTallennaPressed(ActionEvent event) throws SQLException {

        int toimipisteIndeksi = cboToimipisteet.getSelectionModel().getSelectedIndex();
        int palvelunIndeksi = 0;
        onHyva = true;
        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        if (lisaaPainettu == true) {
            palvelunIndeksi = cboPalvelut.getItems().size();
            Palvelu palvelu = new Palvelu();

            for (int i = 0; i < cboPalvelut.getItems().size(); i++) {
                if (cboPalvelut.getItems().get(i).equals(txtPalveluNimi.getText().trim())) {
                    txtSamapalveluVaroitus.setText("Palvelu on jo olemassa!");
                    txtSamapalveluVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtSamapalveluVaroitus.setVisible(false);
                }
            }
            if (txtPalveluNimi.getText().isEmpty()) {
                txtPalveluVaroitus.setText("Nimi on tyhjä!");
                txtPalveluVaroitus.setVisible(true);
                circlePalveluVaroitus.setVisible(true);
                onHyva = false;
            } else {
                palvelu.setNimi(txtPalveluNimi.getText().trim());
                txtPalveluVaroitus.setVisible(false);
                circlePalveluVaroitus.setVisible(false);
            }
            if (txtPalveluPaivaHinta.getText().isEmpty()) {
                txtPalveluHintaVaroitus.setText("Hinta on tyhjä!");
                txtPalveluHintaVaroitus.setVisible(true);
                circlePalveluHintaVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtPalveluPaivaHinta.setText(txtPalveluPaivaHinta.getText().replace(',', '.'));
                palvelu.setPaivaHinta(Double.valueOf(txtPalveluPaivaHinta.getText().trim()));
                txtPalveluHintaVaroitus.setVisible(false);
                circlePalveluHintaVaroitus.setVisible(false);
            }
            if (onHyva == false) {
                return;
            } else {
                palvelu.setToimipisteID(valittuToimipisteID);
                palvelut.add(palvelu);
            }
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Palvelut (Nimi, PaivaHinta, ToimipisteID) VALUES(?, ?, ?);"
            );
            ps.setString(1, palvelu.getNimi());
            ps.setDouble(2, palvelu.getPaivaHinta());
            ps.setInt(3, palvelu.getToimipisteID());
            ps.execute();

            txtSamapalveluVaroitus.setVisible(false);
            onHyva = true;
            lisaaPainettu = false;

        } else if (muokkaaPainettu == true) {
            palvelunIndeksi = cboPalvelut.getSelectionModel().getSelectedIndex();
            int palveluID = 0;
            for (int i = 0; i < palvelut.size(); i++) {
                if (palvelut.get(i).getNimi().equals(cboPalvelut.getValue()) && palvelut.get(i).getToimipisteID() == valittuToimipisteID) {

                    if (txtPalveluNimi.getText().isEmpty()) {
                        txtPalveluVaroitus.setText("Nimi on tyhjä!");
                        txtPalveluVaroitus.setVisible(true);
                        circlePalveluVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        palvelut.get(i).setNimi(txtPalveluNimi.getText().trim());
                        txtPalveluVaroitus.setVisible(false);
                        circlePalveluVaroitus.setVisible(false);
                    }
                    if (txtPalveluPaivaHinta.getText().isEmpty()) {
                        txtPalveluHintaVaroitus.setText("Hinta on tyhjä!");
                        txtPalveluHintaVaroitus.setVisible(true);
                        circlePalveluHintaVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        txtPalveluPaivaHinta.setText(txtPalveluPaivaHinta.getText().replace(',', '.'));
                        palvelut.get(i).setPaivaHinta(Double.valueOf(txtPalveluPaivaHinta.getText().trim()));
                        txtPalveluHintaVaroitus.setVisible(false);
                        circlePalveluHintaVaroitus.setVisible(false);
                    }
                    if (onHyva == false) {
                        return;
                    } else {
                        palveluID = palvelut.get(i).getPalveluID();
                        break;
                    }
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Palvelut SET Nimi = ?, PaivaHinta = ? WHERE PalveluID = ?;"
            );
            ps.setString(1, txtPalveluNimi.getText().trim());
            ps.setDouble(2, Double.valueOf(txtPalveluPaivaHinta.getText().trim()));
            ps.setInt(3, palveluID);
            ps.execute();

            onHyva = true;
            muokkaaPainettu = false;
        }

        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboLaitteet.setDisable(false);
        cboPalvelut.setDisable(false);

        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(false);
        btnPalveluMuokkaa.setDisable(false);
        btnPalveluPoista.setDisable(false);

        btnLaiteLisaa.setDisable(false);
        btnLaiteMuokkaa.setDisable(false);
        btnLaitePoista.setDisable(false);

        btnToimistoTilaLisaa.setDisable(false);
        btnToimistoTilaMuokkaa.setDisable(false);
        btnToimistoTilaPoista.setDisable(false);
        btnTilaLisaaAsiakas.setDisable(false);

        txtPalveluNimi.setEditable(false);
        txtPalveluPaivaHinta.setEditable(false);

        btnPalveluTallenna.setVisible(false);
        btnPalveluTallenna.setDisable(true);
        btnPalveluPeru.setVisible(false);
        btnPalveluPeru.setDisable(true);

        haeKaikkiPalvelut(conn);
        cboPalvelut.getItems().clear();
        cboToimipisteet.getSelectionModel().clearSelection();
        cboToimipisteet.getSelectionModel().select(toimipisteIndeksi);
        cboPalvelut.getSelectionModel().select(palvelunIndeksi);
    }

    @FXML
    private void btnPalveluPeruPressed(ActionEvent event
    ) {

        muokkaaPainettu = false;
        lisaaPainettu = false;

        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboLaitteet.setDisable(false);
        cboPalvelut.setDisable(false);

        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);

        btnPalveluLisaa.setDisable(false);
        btnPalveluMuokkaa.setDisable(false);
        btnPalveluPoista.setDisable(false);

        btnLaiteLisaa.setDisable(false);
        btnLaiteMuokkaa.setDisable(false);
        btnLaitePoista.setDisable(false);

        btnToimistoTilaLisaa.setDisable(false);
        btnToimistoTilaMuokkaa.setDisable(false);
        btnToimistoTilaPoista.setDisable(false);
        btnTilaLisaaAsiakas.setDisable(false);

        txtPalveluNimi.setEditable(false);
        txtPalveluPaivaHinta.setEditable(false);

        btnPalveluTallenna.setVisible(false);
        btnPalveluTallenna.setDisable(true);
        btnPalveluPeru.setVisible(false);
        btnPalveluPeru.setDisable(true);

        txtSamapalveluVaroitus.setVisible(false);
    }

    @FXML
    private void btnLaiteLisaaPressed(ActionEvent event
    ) {

        lisaaPainettu = true;

        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboLaitteet.setDisable(true);
        cboPalvelut.setDisable(true);

        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);

        btnLaiteLisaa.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(true);
        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnTilaLisaaAsiakas.setDisable(true);

        txtLaiteNimi.setEditable(true);
        txtLaitePaivaHinta.setEditable(true);

        btnLaiteTallenna.setVisible(true);
        btnLaiteTallenna.setDisable(false);
        btnLaitePeru.setVisible(true);
        btnLaitePeru.setDisable(false);
    }

    @FXML
    private void btnLaiteMuokkaaPressed(ActionEvent event
    ) {

        muokkaaPainettu = true;

        tabAsiakkaat.setDisable(true);
        cboToimipisteet.setDisable(true);
        cboToimistoTilat.setDisable(true);
        cboLaitteet.setDisable(true);
        cboPalvelut.setDisable(true);

        btnToimiPisteLisaa.setDisable(true);
        btnToimiPisteMuokkaa.setDisable(true);
        btnToimiPistePoista.setDisable(true);

        btnPalveluLisaa.setDisable(true);
        btnPalveluMuokkaa.setDisable(true);
        btnPalveluPoista.setDisable(true);

        btnLaiteLisaa.setDisable(true);
        btnLaiteMuokkaa.setDisable(true);
        btnLaitePoista.setDisable(true);

        btnToimistoTilaLisaa.setDisable(true);
        btnToimistoTilaMuokkaa.setDisable(true);
        btnToimistoTilaPoista.setDisable(true);
        btnTilaLisaaAsiakas.setDisable(true);

        txtLaiteNimi.setEditable(true);
        txtLaitePaivaHinta.setEditable(true);

        btnLaiteTallenna.setVisible(true);
        btnLaiteTallenna.setDisable(false);
        btnLaitePeru.setVisible(true);
        btnLaitePeru.setDisable(false);
    }

    @FXML
    private void btnLaitePoistaPressed(ActionEvent event) throws SQLException {

        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        Alert laitePoisto = new Alert(Alert.AlertType.CONFIRMATION);
        laitePoisto.setTitle("Laitteen " + cboLaitteet.getValue() + " poisto");
        laitePoisto.setHeaderText("Olet poistamassa laitetta " + cboLaitteet.getValue() + "!");
        laitePoisto.setContentText("Oletko varma että haluat poistaa laitteen?");

        Optional<ButtonType> vastaus = laitePoisto.showAndWait();
        if (vastaus.get() == ButtonType.OK) {
            for (int i = 0; i < laitteet.size(); i++) {
                if (laitteet.get(i).getLaiteID() == valittuLaiteID) {
                    laitteet.remove(i);
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Laitteet WHERE LaiteID = ?;"
            );
            ps.setInt(1, valittuLaiteID);
            ps.execute();
        }
        haeKaikkiLaitteet(conn);
        cboLaitteet.getItems().clear();
        for (int j = 0; j < laitteet.size(); j++) {
            if (laitteet.get(j).getToimipisteID() == valittuToimipisteID) {
                cboLaitteet.setDisable(false);
                cboLaitteet.getItems().add(laitteet.get(j).getNimi());
                btnLaiteLisaa.setDisable(false);
            }
        }
        cboLaitteet.getSelectionModel().selectFirst();
    }

    @FXML
    private void btnLaiteTallennaPressed(ActionEvent event) throws SQLException {

        int toimipisteIndeksi = cboToimipisteet.getSelectionModel().getSelectedIndex();
        int laitteenIndeksi = 0;
        onHyva = true;
        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        if (lisaaPainettu == true) {
            laitteenIndeksi = cboLaitteet.getItems().size();
            Laite laite = new Laite();

            for (int i = 0; i < cboLaitteet.getItems().size(); i++) {
                if (cboLaitteet.getItems().get(i).equals(txtLaiteNimi.getText().trim())) {
                    txtSamalaiteVaroitus.setText("Laite on jo olemassa!");
                    txtSamalaiteVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtSamalaiteVaroitus.setVisible(false);
                }
            }
            if (txtLaiteNimi.getText().isEmpty()) {
                txtLaiteVaroitus.setText("Nimi on tyhjä!");
                txtLaiteVaroitus.setVisible(true);
                circleLaiteVaroitus.setVisible(true);
                onHyva = false;
            } else {
                laite.setNimi(txtLaiteNimi.getText().trim());
                txtLaiteVaroitus.setVisible(false);
                circleLaiteVaroitus.setVisible(false);
            }
            if (txtLaitePaivaHinta.getText().isEmpty()) {
                txtLaiteHintaVaroitus.setText("Hinta on tyhjä!");
                txtLaiteHintaVaroitus.setVisible(true);
                circleLaiteHintaVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtLaitePaivaHinta.setText(txtLaitePaivaHinta.getText().replace(',', '.'));
                laite.setPaivaHinta(Double.valueOf(txtLaitePaivaHinta.getText().trim()));
                txtLaiteHintaVaroitus.setVisible(false);
                circleLaiteHintaVaroitus.setVisible(false);
            }
            if (onHyva == false) {
                return;
            } else {
                laite.setToimipisteID(valittuToimipisteID);
                laitteet.add(laite);
            }
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Laitteet (Nimi, PaivaHinta, ToimipisteID) VALUES(?, ?, ?);"
            );
            ps.setString(1, laite.getNimi());
            ps.setDouble(2, laite.getPaivaHinta());
            ps.setInt(3, laite.getToimipisteID());
            ps.execute();

            txtSamalaiteVaroitus.setVisible(false);
            onHyva = true;
            lisaaPainettu = false;

        } else if (muokkaaPainettu == true) {
            laitteenIndeksi = cboLaitteet.getSelectionModel().getSelectedIndex();
            int laiteID = 0;
            for (int i = 0; i < laitteet.size(); i++) {
                if (laitteet.get(i).getNimi().equals(cboLaitteet.getValue()) && laitteet.get(i).getToimipisteID() == valittuToimipisteID) {

                    if (txtLaiteNimi.getText().isEmpty()) {
                        txtLaiteVaroitus.setText("Nimi on tyhjä!");
                        txtLaiteVaroitus.setVisible(true);
                        circleLaiteVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        laitteet.get(i).setNimi(txtLaiteNimi.getText().trim());
                        txtLaiteVaroitus.setVisible(false);
                        circleLaiteVaroitus.setVisible(false);
                    }
                    if (txtLaitePaivaHinta.getText().isEmpty()) {
                        txtLaiteHintaVaroitus.setText("Hinta on tyhjä!");
                        txtLaiteHintaVaroitus.setVisible(true);
                        circleLaiteHintaVaroitus.setVisible(true);
                        onHyva = false;
                    } else {
                        txtLaitePaivaHinta.setText(txtLaitePaivaHinta.getText().replace(',', '.'));
                        laitteet.get(i).setPaivaHinta(Double.valueOf(txtLaitePaivaHinta.getText().trim()));
                        txtLaiteHintaVaroitus.setVisible(false);
                        circleLaiteHintaVaroitus.setVisible(false);
                    }
                    if (onHyva == false) {
                        return;
                    } else {
                        laiteID = laitteet.get(i).getLaiteID();
                        break;
                    }
                }
            }
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Laitteet SET Nimi = ?, PaivaHinta = ? WHERE LaiteID = ?;"
            );
            ps.setString(1, txtLaiteNimi.getText().trim());
            ps.setDouble(2, Double.valueOf(txtLaitePaivaHinta.getText().trim()));
            ps.setInt(3, laiteID);
            ps.execute();

            onHyva = true;
            muokkaaPainettu = false;
        }

        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboLaitteet.setDisable(false);
        cboPalvelut.setDisable(false);

        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);

        btnPalveluLisaa.setDisable(false);
        btnPalveluMuokkaa.setDisable(false);
        btnPalveluPoista.setDisable(false);

        btnLaiteLisaa.setDisable(false);
        btnLaiteMuokkaa.setDisable(false);
        btnLaitePoista.setDisable(false);

        btnToimistoTilaLisaa.setDisable(false);
        btnToimistoTilaMuokkaa.setDisable(false);
        btnToimistoTilaPoista.setDisable(false);
        btnTilaLisaaAsiakas.setDisable(false);

        txtLaiteNimi.setEditable(false);
        txtLaitePaivaHinta.setEditable(false);

        btnLaiteTallenna.setVisible(false);
        btnLaiteTallenna.setDisable(true);
        btnLaitePeru.setVisible(false);
        btnLaitePeru.setDisable(true);

        haeKaikkiLaitteet(conn);
        cboLaitteet.getItems().clear();
        cboToimipisteet.getSelectionModel().clearSelection();
        cboToimipisteet.getSelectionModel().select(toimipisteIndeksi);
        cboLaitteet.getSelectionModel().select(laitteenIndeksi);
    }

    @FXML
    private void btnLaitePeruPressed(ActionEvent event
    ) {

        muokkaaPainettu = false;
        lisaaPainettu = false;

        tabAsiakkaat.setDisable(false);
        cboToimipisteet.setDisable(false);
        cboToimistoTilat.setDisable(false);
        cboLaitteet.setDisable(false);
        cboPalvelut.setDisable(false);

        btnToimiPisteLisaa.setDisable(false);
        btnToimiPisteMuokkaa.setDisable(false);
        btnToimiPistePoista.setDisable(false);

        btnPalveluLisaa.setDisable(false);
        btnPalveluMuokkaa.setDisable(false);
        btnPalveluPoista.setDisable(false);

        btnLaiteLisaa.setDisable(false);
        btnLaiteMuokkaa.setDisable(false);
        btnLaitePoista.setDisable(false);

        btnToimistoTilaLisaa.setDisable(false);
        btnToimistoTilaMuokkaa.setDisable(false);
        btnToimistoTilaPoista.setDisable(false);
        btnTilaLisaaAsiakas.setDisable(false);

        txtLaiteNimi.setEditable(false);
        txtLaitePaivaHinta.setEditable(false);

        btnLaiteTallenna.setVisible(false);
        btnLaiteTallenna.setDisable(true);
        btnLaitePeru.setVisible(false);
        btnLaitePeru.setDisable(true);

        txtSamalaiteVaroitus.setVisible(false);
    }

    public static int getValittuToimipisteID() {
        return valittuToimipisteID;
    }

    public static int getValittuToimitilaID() {
        return valittuToimitilaID;
    }
}
