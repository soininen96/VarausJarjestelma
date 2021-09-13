package varausjarjestelma;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Varausjarjestelma extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("VarausJarjestelmaView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Varausjärjestelmä");
        stage.show();

    }
    
        public static void main(String[] args) throws SQLException {
        launch(args);
    }


}
