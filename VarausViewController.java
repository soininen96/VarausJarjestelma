package varausjarjestelma;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VarausViewController implements Initializable {

    @FXML
    private ComboBox<String> cboVanhaAsiakas;
    @FXML
    private Button btnUusiAsiakas;
    @FXML
    private TextField txtAsiakasNimi;
    @FXML
    private TextField txtAsiakasOsoite;
    @FXML
    private TextField txtAsiakasPostiNumero;
    @FXML
    private TextField txtAsiakasPostiPaikka;
    @FXML
    private TextField txtAsiakasSposti;
    @FXML
    private TextField txtAsiakasPuhNum;
    @FXML
    private RadioButton radSahkoinen;
    @FXML
    private RadioButton radPaperinen;
    @FXML
    private Button btnTallennaVaraus;
    @FXML
    private VBox vboxPalvelut;
    @FXML
    private VBox vboxLaitteet;

    ArrayList<Asiakas> asiakkaat = new ArrayList<>();
    ArrayList<Palvelu> palvelut = new ArrayList<>();
    ArrayList<Laite> laitteet = new ArrayList<>();

    static ArrayList<Palvelu> valitutPalvelut = new ArrayList<>();
    static ArrayList<Laite> valitutLaitteet = new ArrayList<>();

    private static int toimitilaID;
    private static int toimipisteID;
    private static int asiakasID;
    private static int varausID;

    private static String laskutustapa;

    private boolean uusiAsiakas;
    private boolean vanhaAsiakas;

    private boolean onHyva;

    @FXML
    private ToggleGroup radLaskutus;
    @FXML
    private DatePicker dateAlkupvm;
    @FXML
    private DatePicker dateLoppupvm;

    private static LocalDate aloituspvm;
    private static LocalDate lopetuspvm;

    ArrayList<CheckBox> palveluBoxit = new ArrayList<>();
    ArrayList<CheckBox> laiteBoxit = new ArrayList<>();
    @FXML
    private Button btnPeruutaVaraus;
    @FXML
    private Text txtNimiVaroitus;
    @FXML
    private Circle circleNimiVaroitus;
    @FXML
    private Text txtKatuosoiteVaroitus;
    @FXML
    private Circle circleKatuosoiteVaroitus;
    @FXML
    private Text txtPostinumeroVaroitus;
    @FXML
    private Circle circlePostinumeroVaroitus;
    @FXML
    private Text txtPostipaikkaVaroitus;
    @FXML
    private Circle circlePostipaikkaVaroitus;
    @FXML
    private Text txtSpostiVaroitus;
    @FXML
    private Circle circleSpostiVaroitus;
    @FXML
    private Text txtPuhnumVaroitus;
    @FXML
    private Circle circlePuhnumVaroitus;
    @FXML
    private Text txtLaskutusVaroitus;
    @FXML
    private Text txtKalenteriVaroitus;
    @FXML
    private Text txtSamaSpostiVaroitus;
    @FXML
    private Text txtSamaPuhnumVaroitus;
    @FXML
    private Text txtVanhaAsiakasVaroitus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = DatabaseConnection.openConnection();
            toimipisteID = VarausJarjestelmaViewController.getValittuToimipisteID();
            toimitilaID = VarausJarjestelmaViewController.getValittuToimitilaID();
            haeKaikkiAsiakkaat(conn);
            haePisteenPalvelut(conn);
            haePisteenLaitteet(conn);
            DatabaseConnection.closeConnection(conn);
            uusiAsiakas = false;
            vanhaAsiakas = false;

            dateAlkupvm.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0);
                    if (dateLoppupvm.getValue() != null) {
                        setDisable(empty || date.compareTo(dateLoppupvm.getValue()) > 1 && empty || date.compareTo(today) < 0);
                    }
                }
            });
            dateLoppupvm.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 1);
                }
            });

            // postinumero kenttä ei ota vastaan muita kuin numeroita ja ei enempää kuin 5 numeroa
            txtAsiakasPostiNumero.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txtAsiakasPostiNumero.setText(oldValue);
                }
                if (newValue.length() > 6) {
                    txtAsiakasPostiNumero.setText(oldValue);
                }
            });

            // puhelinnumero kenttä ei ota vastaan muita kuin numeroita ja ei enempää kuin 15 numeroa
            txtAsiakasPuhNum.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txtAsiakasPuhNum.setText(oldValue);
                }
                if (newValue.length() > 15) {
                    txtAsiakasPuhNum.setText(oldValue);
                }
            });

            // nimi kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtAsiakasNimi.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtAsiakasNimi.setText(oldValue);
                }
            });

            // katuosoite kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtAsiakasOsoite.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtAsiakasOsoite.setText(oldValue);
                }
            });

            // postitoimipaikka kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtAsiakasPostiPaikka.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtAsiakasPostiPaikka.setText(oldValue);
                }
            });

            // sposti kenttä ei ota vastaan enempää kuin 30 merkkiä
            txtAsiakasSposti.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (newValue.length() > 30) {
                    txtAsiakasSposti.setText(oldValue);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(VarausViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void haeKaikkiAsiakkaat(Connection c) throws SQLException {
        asiakkaat.clear();
        cboVanhaAsiakas.getItems().clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT AsiakasID,Nimi,Email,LaskutusTapa,Osoite,Postinumero,Postitoimipaikka,Puhelinumero FROM Asiakkaat"
        );

        while (rs.next()) {
            Asiakas asiakas = new Asiakas(rs.getInt("AsiakasID"), rs.getString("Nimi"), rs.getString("Email"), rs.getString("LaskutusTapa"), rs.getString("Osoite"), rs.getInt("Postinumero"), rs.getString("Postitoimipaikka"), rs.getString("Puhelinumero"));
            asiakkaat.add(asiakas);
        }
        try {
            for (int i = 0; i < asiakkaat.size(); i++) {
                cboVanhaAsiakas.getItems().add(asiakkaat.get(i).getNimi());
            }
        } catch (NullPointerException n) {
        }
    }

    public void haePisteenPalvelut(Connection c) throws SQLException {
        palvelut.clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT PalveluID, Nimi, PaivaHinta FROM Palvelut WHERE ToimipisteID = " + toimipisteID + ";"
        );
        while (rs.next()) {
            Palvelu palvelu = new Palvelu();
            palvelu.setPalveluID(rs.getInt("PalveluID"));
            palvelu.setNimi(rs.getString("Nimi"));
            palvelu.setPaivaHinta(rs.getDouble("PaivaHinta"));
            palvelut.add(palvelu);
        }
        try {
            for (int i = 0; i < palvelut.size(); i++) {
                palveluBoxit.add(new CheckBox(palvelut.get(i).getNimi() + " " + palvelut.get(i).getPaivaHinta() + "e"));
                vboxPalvelut.getChildren().add(palveluBoxit.get(i));
            }
        } catch (NullPointerException n) {
        }
    }

    public void haePisteenLaitteet(Connection c) throws SQLException {
        laitteet.clear();
        DatabaseConnection.useDatabase(c);
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT LaiteID, Nimi, PaivaHinta FROM Laitteet WHERE ToimipisteID = " + toimipisteID + ";"
        );
        while (rs.next()) {
            Laite laite = new Laite();
            laite.setLaiteID(rs.getInt("LaiteID"));
            laite.setNimi(rs.getString("Nimi"));
            laite.setPaivaHinta(rs.getDouble("PaivaHinta"));
            laitteet.add(laite);
        }
        try {
            for (int i = 0; i < laitteet.size(); i++) {
                laiteBoxit.add(new CheckBox(laitteet.get(i).getNimi() + " " + laitteet.get(i).getPaivaHinta() + "e"));
                vboxLaitteet.getChildren().add(laiteBoxit.get(i));
            }
        } catch (NullPointerException n) {
        }
    }

    @FXML
    private void cboVanhaAsiakasPressed(ActionEvent event) {
        uusiAsiakas = false;
        vanhaAsiakas = true;
        radPaperinen.setSelected(false);
        radSahkoinen.setSelected(false);
        radPaperinen.setDisable(true);
        radSahkoinen.setDisable(true);
        txtAsiakasNimi.setEditable(false);
        txtAsiakasOsoite.setEditable(false);
        txtAsiakasPostiNumero.setEditable(false);
        txtAsiakasPostiPaikka.setEditable(false);
        txtAsiakasPuhNum.setEditable(false);
        txtAsiakasSposti.setEditable(false);
        btnTallennaVaraus.setDisable(false);
        dateAlkupvm.setDisable(false);
        dateLoppupvm.setDisable(false);

        try {
            OUTER:
            for (int i = 0; i < asiakkaat.size(); i++) {
                if (cboVanhaAsiakas.getValue().equals(asiakkaat.get(i).getNimi())) {
                    asiakasID = asiakkaat.get(i).getAsiakasID();
                    txtAsiakasNimi.setText(asiakkaat.get(i).getNimi());
                    txtAsiakasOsoite.setText(asiakkaat.get(i).getKatuosoite());
                    txtAsiakasPostiNumero.setText(String.valueOf(asiakkaat.get(i).getPostinumero()));
                    txtAsiakasPostiPaikka.setText(asiakkaat.get(i).getPostitoimipaikka());
                    txtAsiakasSposti.setText(asiakkaat.get(i).getEmail());
                    txtAsiakasPuhNum.setText(asiakkaat.get(i).getPuhelinumero());
                    switch (asiakkaat.get(i).getLaskutustapa()) {
                        case "Sähkö":
                            radSahkoinen.setSelected(true);
                            break;
                        case "Paperi":
                            radPaperinen.setSelected(true);
                            break;
                        default:
                            break OUTER;
                    }
                }
            }
        } catch (NullPointerException n) {
        }
    }

    @FXML
    private void btnUusiAsiakasPressed(ActionEvent event) {
        btnTallennaVaraus.setDisable(false);
        vanhaAsiakas = false;
        uusiAsiakas = true;
        txtAsiakasNimi.clear();
        txtAsiakasOsoite.clear();
        txtAsiakasPostiNumero.clear();
        txtAsiakasPostiPaikka.clear();
        txtAsiakasPuhNum.clear();
        txtAsiakasSposti.clear();
        txtAsiakasNimi.setEditable(true);
        txtAsiakasOsoite.setEditable(true);
        txtAsiakasPostiNumero.setEditable(true);
        txtAsiakasPostiPaikka.setEditable(true);
        txtAsiakasPuhNum.setEditable(true);
        txtAsiakasSposti.setEditable(true);
        radPaperinen.setSelected(false);
        radSahkoinen.setSelected(false);
        radPaperinen.setDisable(false);
        radSahkoinen.setDisable(false);
        dateAlkupvm.setDisable(false);
        dateLoppupvm.setDisable(false);
    }

    @FXML
    private void btnTallennaVarausPressed(ActionEvent event) throws SQLException, IOException {
        onHyva = true;
        Connection conn = DatabaseConnection.openConnection();
        if (uusiAsiakas == true) {

            for (int i = 0; i < asiakkaat.size(); i++) {
                if (asiakkaat.get(i).getNimi().equals(txtAsiakasNimi.getText().trim())) {
                    txtVanhaAsiakasVaroitus.setText("Asiakas on jo olemassa!");
                    txtVanhaAsiakasVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtVanhaAsiakasVaroitus.setVisible(false);
                }
            }
            for (int i = 0; i < asiakkaat.size(); i++) {
                if (asiakkaat.get(i).getEmail().equals(txtAsiakasSposti.getText().trim())) {
                    txtSamaSpostiVaroitus.setText("Sähkoposti on jo käytössä");
                    txtSamaSpostiVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtSamaSpostiVaroitus.setVisible(false);
                }
            }

            for (int i = 0; i < asiakkaat.size(); i++) {
                if (asiakkaat.get(i).getPuhelinumero().equals(txtAsiakasPuhNum.getText().trim())) {
                    txtSamaPuhnumVaroitus.setText("Puhelinnumero on jo käytössä");
                    txtSamaPuhnumVaroitus.setVisible(true);
                    onHyva = false;
                    break;
                } else {
                    txtSamaPuhnumVaroitus.setVisible(false);
                }
            }

            if (!radPaperinen.isSelected() && !radSahkoinen.isSelected()) {
                txtLaskutusVaroitus.setText("Laskutustapa puuttuu!");
                txtLaskutusVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtLaskutusVaroitus.setVisible(false);
            }
            if (dateAlkupvm.getValue() == null || dateLoppupvm.getValue() == null) {
                txtKalenteriVaroitus.setText("Päivämäärät puuttuvat!");
                txtKalenteriVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtKalenteriVaroitus.setVisible(false);
            }
            if (txtAsiakasNimi.getText().isEmpty()) {
                txtNimiVaroitus.setText("Nimi tyhjä!");
                txtNimiVaroitus.setVisible(true);
                circleNimiVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtNimiVaroitus.setVisible(false);
                circleNimiVaroitus.setVisible(false);
            }
            if (txtAsiakasOsoite.getText().isEmpty()) {
                txtKatuosoiteVaroitus.setText("Katuosoite on tyhjä!");
                txtKatuosoiteVaroitus.setVisible(true);
                circleKatuosoiteVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtKatuosoiteVaroitus.setVisible(false);
                circleKatuosoiteVaroitus.setVisible(false);
            }
            if (txtAsiakasPostiNumero.getText().isEmpty()) {
                txtPostinumeroVaroitus.setText("Postinumero on tyhjä!");
                txtPostinumeroVaroitus.setVisible(true);
                circlePostinumeroVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtPostinumeroVaroitus.setVisible(false);
                circlePostinumeroVaroitus.setVisible(false);
            }
            if (txtAsiakasPostiPaikka.getText().isEmpty()) {
                txtPostipaikkaVaroitus.setText("Postitoimipaikka on tyhjä!");
                txtPostipaikkaVaroitus.setVisible(true);
                circlePostipaikkaVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtPostipaikkaVaroitus.setVisible(false);
                circlePostipaikkaVaroitus.setVisible(false);
            }
            if (txtAsiakasPuhNum.getText().isEmpty()) {
                txtPuhnumVaroitus.setText("Puhelinumero on tyhjä!");
                txtPuhnumVaroitus.setVisible(true);
                circlePuhnumVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtPuhnumVaroitus.setVisible(false);
                circlePuhnumVaroitus.setVisible(false);
            }
            if (txtAsiakasSposti.getText().isEmpty()) {
                txtSpostiVaroitus.setText("Sähköpostiosoite on tyhjä!");
                txtSpostiVaroitus.setVisible(true);
                circleSpostiVaroitus.setVisible(true);
                onHyva = false;
            } else {
                txtSpostiVaroitus.setVisible(false);
                circleSpostiVaroitus.setVisible(false);
            }
            if (onHyva == false) {
                return;
            } else {
                txtVanhaAsiakasVaroitus.setVisible(false);
                txtSamaPuhnumVaroitus.setVisible(false);
                txtSamaSpostiVaroitus.setVisible(false);
                DatabaseConnection.useDatabase(conn);
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Asiakkaat (Nimi,Email,LaskutusTapa,Osoite,Postinumero,Postitoimipaikka,Puhelinumero) VALUES(?, ?, ?, ?, ?, ?, ?);"
                );
                ps.setString(1, txtAsiakasNimi.getText().trim());
                ps.setString(2, txtAsiakasSposti.getText().trim());
                if (radPaperinen.isSelected()) {
                    ps.setString(3, "Paperi");
                    laskutustapa = "Paperi";
                } else if (radSahkoinen.isSelected()) {
                    ps.setString(3, "Sähkö");
                    laskutustapa = "Sähkö";
                }
                ps.setString(4, txtAsiakasOsoite.getText().trim());
                ps.setInt(5, Integer.valueOf(txtAsiakasPostiNumero.getText().trim()));
                ps.setString(6, txtAsiakasPostiPaikka.getText().trim());
                ps.setString(7, txtAsiakasPuhNum.getText().trim());
                ps.execute();

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT AsiakasID FROM Asiakkaat WHERE Email = '" + txtAsiakasSposti.getText().trim() + "';"
                );
                while (rs.next()) {
                    asiakasID = rs.getInt("AsiakasID");
                }

                PreparedStatement ps1 = conn.prepareStatement(
                        "INSERT INTO Varaus (AlkuPvm, LoppuPvm, AsiakasID, ToimitilaID) VALUES(?, ?, ?, ?);"
                );
                ps1.setDate(1, Date.valueOf(dateAlkupvm.getValue()));
                ps1.setDate(2, Date.valueOf(dateLoppupvm.getValue()));
                ps1.setInt(3, asiakasID);
                ps1.setInt(4, toimitilaID);
                ps1.execute();
            }

        } else if (vanhaAsiakas == true) {
            for (int i = 0; i < asiakkaat.size(); i++) {
                if (asiakkaat.get(i).getNimi().equals(txtAsiakasNimi.getText())) {
                    asiakasID = asiakkaat.get(i).getAsiakasID();
                    break;
                }
            }
            if (radPaperinen.isSelected()) {
                laskutustapa = "Paperi";
            } else if (radSahkoinen.isSelected()) {
                laskutustapa = "Sähkö";
            }
            if (dateAlkupvm.getValue() == null || dateLoppupvm.getValue() == null) {
                txtKalenteriVaroitus.setText("Päivämäärät puuttuvat!");
                txtKalenteriVaroitus.setVisible(true);
                return;
            } else {
                txtKalenteriVaroitus.setVisible(false);
            }

            DatabaseConnection.useDatabase(conn);
            PreparedStatement ps1 = conn.prepareStatement(
                    "INSERT INTO Varaus (AlkuPvm, LoppuPvm, AsiakasID, ToimitilaID) VALUES(?, ?, ?, ?);"
            );
            ps1.setDate(1, Date.valueOf(dateAlkupvm.getValue()));
            ps1.setDate(2, Date.valueOf(dateLoppupvm.getValue()));
            ps1.setInt(3, asiakasID);
            ps1.setInt(4, toimitilaID);
            ps1.execute();
        }

        aloituspvm = dateAlkupvm.getValue();
        lopetuspvm = dateLoppupvm.getValue();

        for (int i = 0; i < palveluBoxit.size(); i++) {
            if (palveluBoxit.get(i).isSelected()) {
                for (int j = 0; j < palvelut.size(); j++) {
                    if (palveluBoxit.get(i).getText().contains(palvelut.get(j).getNimi())) {
                        valitutPalvelut.add(palvelut.get(j));
                    }
                }
            }
        }

        for (int i = 0; i < laiteBoxit.size(); i++) {
            if (laiteBoxit.get(i).isSelected()) {
                for (int j = 0; j < laitteet.size(); j++) {
                    if (laiteBoxit.get(i).getText().contains(laitteet.get(j).getNimi())) {
                        valitutLaitteet.add(laitteet.get(j));
                    }
                }
            }
        }

        DatabaseConnection.useDatabase(conn);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT VarausID FROM Varaus WHERE AsiakasID = " + asiakasID + " AND ToimitilaID = " + toimitilaID + ";"
        );
        while (rs.next()) {
            varausID = rs.getInt("VarausID");
        }

        for (int i = 0; i < valitutPalvelut.size(); i++) {
            PreparedStatement ps1 = conn.prepareStatement(
                    "INSERT INTO VarauksenPalvelut (PalveluID, VarausID) VALUES(?, ?);"
            );
            ps1.setInt(1, valitutPalvelut.get(i).getPalveluID());
            ps1.setInt(2, varausID);
            ps1.execute();
        }

        for (int i = 0; i < valitutLaitteet.size(); i++) {
            PreparedStatement ps1 = conn.prepareStatement(
                    "INSERT INTO VarauksenLaitteet (LaiteID, VarausID) VALUES(?, ?);"
            );
            ps1.setInt(1, valitutLaitteet.get(i).getLaiteID());
            ps1.setInt(2, varausID);
            ps1.execute();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Varaus tallennettu");
        Optional<ButtonType> vastaus = alert.showAndWait();
        if (vastaus.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("LaskuView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Lasku");
            stage.show();
        }
    }

    public static int getAsiakasID() {
        return asiakasID;
    }

    public static int getVarausID() {
        return varausID;
    }

    public static int getToimipisteID() {
        return toimipisteID;
    }

    public static int getToimitilaID() {
        return toimitilaID;
    }

    public static ArrayList<Palvelu> getValitutPalvelut() {
        return valitutPalvelut;
    }

    public static ArrayList<Laite> getValitutLaitteet() {
        return valitutLaitteet;
    }

    public static LocalDate getAlkupvm() {
        return aloituspvm;
    }

    public static LocalDate getLoppupvm() {
        return lopetuspvm;
    }

    public static String getLaskutustapa() {
        return laskutustapa;
    }

    @FXML
    private void dateAlkupvmSelected(ActionEvent event) {

        dateLoppupvm.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate valittu = dateAlkupvm.getValue();

                setDisable(empty || date.compareTo(valittu) < 1);
            }
        });
    }

    @FXML
    private void btnPeruutaVarausPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VarausJarjestelmaView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Varausjärjestelmä");
        stage.show();
    }

}
