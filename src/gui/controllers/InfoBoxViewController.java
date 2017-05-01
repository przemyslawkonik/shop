package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.net.URL;
import java.util.ResourceBundle;


public class InfoBoxViewController implements Initializable {
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
