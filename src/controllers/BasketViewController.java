package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Basket;
import sample.Product;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;


public class BasketViewController implements Initializable {
    @FXML private TableView<Product> basketTableView;
    @FXML private TableColumn<Product, String> basketColumnName;
    @FXML private TableColumn<Product, Double> basketColumnPrice;
    @FXML private TableColumn<Product, Integer> basketColumnQuantity;
    @FXML private TableColumn<Product, Double> basketColumnValue;
    @FXML private ChoiceBox<Integer> basketChoiceBox;
    @FXML private Label totalValue;
    @FXML private TextField basketNameField;
    private MainViewController mainViewController;
    private Basket currentBasket;


    public BasketViewController() {
        currentBasket = new Basket();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        basketTableView.setItems(currentBasket.getProducts());
        //ustawienie kolumn tabeli
        basketColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        basketColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        basketColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        basketColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        //ustawienie choiceboxa
        basketChoiceBox.setItems(getChoiceBoxData());
        basketChoiceBox.getSelectionModel().select(0);

        //ustawienie lacznej wartosci produktow
        totalValue.setText(""+currentBasket.getTotalValue());
    }

    public void init(MainViewController main) {
        mainViewController = main;
    }

    //obsluga usuniecia produktu z koszyka
    public void handleRemoveProduct(ActionEvent event) {
        Product p = new Product(basketTableView.getSelectionModel().getSelectedItem());
        int q = basketChoiceBox.getSelectionModel().getSelectedItem();

        currentBasket.removeProduct(p,q);
        basketTableView.refresh();
        totalValue.setText(""+currentBasket.getTotalValue());
    }

    //obluga wyczyszenia koszyka
    public void handleClear(ActionEvent event) {
        currentBasket.getProducts().clear();
        basketTableView.refresh();
        totalValue.setText(""+currentBasket.getTotalValue());
    }

    //obsluga zapisania koszyka
    public void handleSaveBasket(ActionEvent event) throws IOException {
        //jesli nie podano nazwy koszyka
        if (basketNameField.getText().isEmpty()) {
            basketNameField.setStyle("-fx-prompt-text-fill: red");
        }
        //jesli koszyk jest pusty
        else if (currentBasket.getProducts().isEmpty()) {
            basketNameField.setStyle(null);
            displayCommunicate("Twój koszyk jest pusty", Color.RED);
        }
        //proba zapisu
        else {
            currentBasket.setName(basketNameField.getText());
            boolean result = currentBasket.save();
            if (result) displayCommunicate("Twój koszyk został zapisany", Color.GREEN);
            else displayCommunicate("Koszyk o podanej nazwie już istnieje", Color.RED);
        }
    }

    public ObservableList<Integer> getChoiceBoxData() {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        final int size = 10;
        for(int i=0; i<size; i++)
            list.add(i+1);

        return list;
    }

    private void displayCommunicate(String message, Color color) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/fxml/alertBoxView.fxml"));
        Scene scene = new Scene(loader.load());
        AlertBoxViewController alertBoxViewController = loader.getController();
        alertBoxViewController.getLabel().setText(message);

        if(color.equals(Color.GREEN))
            alertBoxViewController.getLabel().setStyle("-fx-text-fill: green");
        if(color.equals(Color.RED))
            alertBoxViewController.getLabel().setStyle("-fx-text-fill: red");

        Stage stage = new Stage();
        alertBoxViewController.getButton().setOnAction( e -> {
            stage.close();
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Basket getCurrentBasket() {
        return currentBasket;
    }

    public TableView<Product> getBasketTableView() {
        return basketTableView;
    }

    public Label getTotalValue() {
        return totalValue;
    }
}
