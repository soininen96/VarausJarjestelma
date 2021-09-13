/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patri
 */
public class LaskuViewController implements Initializable {

    @FXML
    private Text txtKohde;
    @FXML
    private Text txtHuoneNumero;
    @FXML
    private Text txtHuonePaivaHinta;
    private HBox hboxPalvelut;
    private HBox hboxLaitteet;
    @FXML
    private Text txtAikavali;
    @FXML
    private Text txtSumma;
    @FXML
    private Text txtErapvm;
    @FXML
    private Text txtViitenumero;
    @FXML
    private Text txtOsoite;
    @FXML
    private Button btnLahetaLasku;

    private int asiakasID;
    private int varausID;
    private int toimitilaID;
    private int toimipisteID;

    private double summa;
    private double palveluidenSumma;
    private double laitteidenSumma;
    private long paivat;

    private String laskutustapa;
    private String osoite;
    private String postitoimipaikka;
    private int postinumero;
    private String sposti;

    ArrayList<Palvelu> laskunPalvelut;
    ArrayList<Laite> laskunLaitteet;

    LocalDate alkupvm;
    LocalDate loppupvm;
    LocalDate erapvm;
    @FXML
    private Text txtLaskutettava;
    @FXML
    private Text txtPalvelut;
    @FXML
    private Text txtLaitteet;
    
    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            asiakasID = VarausViewController.getAsiakasID();
            varausID = VarausViewController.getVarausID();
            toimitilaID = VarausViewController.getToimitilaID();
            toimipisteID = VarausViewController.getToimipisteID();
            laskunPalvelut = VarausViewController.getValitutPalvelut();
            laskunLaitteet = VarausViewController.getValitutLaitteet();
            alkupvm = VarausViewController.getAlkupvm();
            loppupvm = VarausViewController.getLoppupvm();
            laskutustapa = VarausViewController.getLaskutustapa();

            Connection conn = DatabaseConnection.openConnection();
            DatabaseConnection.useDatabase(conn);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT Osoite, Postitoimipaikka FROM Toimipiste WHERE ToimipisteID = " + toimipisteID + ";"
            );
            while (rs.next()) {
                txtKohde.setText(rs.getString("Osoite") + ", " + rs.getString("Postitoimipaikka"));
            }

            ResultSet rs1 = stmt.executeQuery(
                    "SELECT Huonenro, Paivavuokra FROM Toimitilat WHERE ToimitilaID = " + toimitilaID + ";"
            );
            while (rs1.next()) {
                txtHuoneNumero.setText(rs1.getString("Huonenro"));
                txtHuonePaivaHinta.setText(String.valueOf(rs1.getDouble("PaivaVuokra")));
            }

            for (int i = 0; i < laskunPalvelut.size(); i++) {
                txtPalvelut.setText(txtPalvelut.getText() + laskunPalvelut.get(i).getNimi() + ", " + String.valueOf(laskunPalvelut.get(i).getPaivaHinta()) + " ,");
            }

            for (int i = 0; i < laskunLaitteet.size(); i++) {
                txtLaitteet.setText(txtLaitteet.getText() + laskunLaitteet.get(i).getNimi() + ", " + String.valueOf(laskunLaitteet.get(i).getPaivaHinta()) + " ,");
            }

            txtAikavali.setText(String.valueOf(alkupvm) + " - " + String.valueOf(loppupvm));
            paivat = loppupvm.toEpochDay() - alkupvm.toEpochDay();

            for (int i = 0; i < laskunPalvelut.size(); i++) {
                palveluidenSumma = palveluidenSumma + laskunPalvelut.get(i).getPaivaHinta();
            }

            for (int i = 0; i < laskunLaitteet.size(); i++) {
                laitteidenSumma = laitteidenSumma + laskunLaitteet.get(i).getPaivaHinta();
            }
            
            double huonehinta = Double.valueOf(txtHuonePaivaHinta.getText());
            summa = summa + (huonehinta + palveluidenSumma + laitteidenSumma);
            summa = summa * paivat;
            
            txtSumma.setText(String.valueOf(df.format(summa)));

            erapvm = loppupvm.plusMonths(1);
            txtErapvm.setText(String.valueOf(erapvm));

            txtViitenumero.setText(String.valueOf(asiakasID) + String.valueOf(varausID) + String.valueOf(toimitilaID) + String.valueOf(toimipisteID));

            if (laskutustapa.equals("Paperi")) {
                ResultSet rs2 = stmt.executeQuery(
                        "SELECT Nimi, Osoite, Postinumero, Postitoimipaikka FROM Asiakkaat WHERE AsiakasID = " + asiakasID + ";"
                );
                while (rs2.next()) {
                    txtOsoite.setText(rs2.getString("Osoite") + ", " + String.valueOf(rs2.getInt("Postinumero")) + ", " + rs2.getString("Postitoimipaikka"));
                    osoite = rs2.getString("Osoite");
                    postinumero = rs2.getInt("Postinumero");
                    postitoimipaikka = rs2.getString("Postitoimipaikka");
                    txtLaskutettava.setText(rs2.getString("Nimi"));
                }
            }
            if (laskutustapa.equals("Sähkö")) {
                ResultSet rs2 = stmt.executeQuery(
                        "SELECT Nimi, Email FROM Asiakkaat WHERE AsiakasID = " + asiakasID + ";"
                );
                while (rs2.next()) {
                    txtOsoite.setText(rs2.getString("Email"));
                    sposti = rs2.getString("Email");
                    txtLaskutettava.setText(rs2.getString("Nimi"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LaskuViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnLahetaLaskuPressed(ActionEvent event) throws SQLException, IOException {

        Connection conn = DatabaseConnection.openConnection();
        DatabaseConnection.useDatabase(conn);
        if (laskutustapa.equals("Paperi")) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Lasku(EraPvm, Summa, AsiakasID, Osoite, Postitoimipaikka, Postinumero, VarausID, Viitenumero) VALUES(?, ?, ?, ?, ?, ?, ?, ?);"
            );
            ps.setDate(1, Date.valueOf(erapvm));
            ps.setDouble(2, summa);
            ps.setInt(3, asiakasID);
            ps.setString(4, osoite);
            ps.setString(5, postitoimipaikka);
            ps.setInt(6, postinumero);
            ps.setInt(7, varausID);
            ps.setInt(8, Integer.valueOf(txtViitenumero.getText()));
            ps.execute();
        } else if (laskutustapa.equals("Sähkö")) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Lasku(EraPvm, Summa, AsiakasID, VarausID, Viitenumero, Sposti) VALUES(?, ?, ?, ?, ?, ?);"
            );
            ps.setDate(1, Date.valueOf(erapvm));
            ps.setDouble(2, summa);
            ps.setInt(3, asiakasID);
            ps.setInt(4, varausID);
            ps.setInt(5, Integer.valueOf(txtViitenumero.getText()));
            ps.setString(6, sposti);
            ps.execute();
        }

        DatabaseConnection.closeConnection(conn);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Lasku lähetetty");
        Optional<ButtonType> vastaus = alert.showAndWait();
        if (vastaus.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("VarausJarjestelmaView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Varausjärjestelmä");
            stage.show();

        }
    }

}
