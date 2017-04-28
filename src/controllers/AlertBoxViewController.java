package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


public class AlertBoxViewController implements Initializable {
    @FXML private Button button;
    @FXML private Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Button getButton() {
        return button;
    }

    public Label getLabel() {
        return label;
    }
}
