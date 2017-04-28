package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AlertBoxViewController implements Initializable {
    @FXML private Button button;
    @FXML private Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setMessage(String message) {
        label.setText(message);
    }

}
