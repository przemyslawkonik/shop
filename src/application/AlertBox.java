package application;

import gui.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class AlertBox {


    public void displayInfo(String title, String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/infoBoxView.fxml"));
        Parent root = loader.load();
        InfoBoxViewController infoBoxViewController = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle(title);
        infoBoxViewController.setMessage(message);

        stage.showAndWait();
    }

    public boolean displayChoice(String title, String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/choiceBoxView.fxml"));
        Parent root = loader.load();
        ChoiceBoxViewController choiceBoxViewController = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle(title);
        choiceBoxViewController.setMessage(message);

        stage.showAndWait();
        boolean result = choiceBoxViewController.getResult();
        return result;
    }
}
