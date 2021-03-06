package tools;

import controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import java.io.IOException;


public class AlertBox {

    public void displayInfo(String title, String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/infoBoxView.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choiceBoxView.fxml"));
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