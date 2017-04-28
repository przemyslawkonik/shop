package sample;

import controllers.AlertBoxViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class AlertBox {


    public void displayCommunicate(String title, String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/fxml/alertBoxView.fxml"));
        Parent root = loader.load();
        AlertBoxViewController alertBoxViewController = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle(title);
        alertBoxViewController.setMessage(message);

        stage.showAndWait();
    }
}
