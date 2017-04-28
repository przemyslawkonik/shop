package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.AlertBox;
import sample.Basket;
import sample.Product;
import java.io.IOException;
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
        AlertBox alertBox = new AlertBox();
        //jesli nie podano nazwy koszyka
        if (basketNameField.getText().isEmpty()) {
            alertBox.displayCommunicate("Błąd", "Podaj nazwę koszyka!");
        }
        //jesli koszyk jest pusty
        else if (currentBasket.getProducts().isEmpty()) {
            basketNameField.setStyle(null);
            alertBox.displayCommunicate("Błąd", "Twój koszyk jest pusty!");
        }
        //proba zapisu
        else {
            currentBasket.setName(basketNameField.getText());
            boolean result = currentBasket.save();
            if (result) alertBox.displayCommunicate("Komunikat", "Twój koszyk został zapisany");
            else alertBox.displayCommunicate("Błąd", "Koszyk o podanej nazwie już istnieje!");
        }
    }

    private ObservableList<Integer> getChoiceBoxData() {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        final int size = 10;
        for(int i=0; i<size; i++)
            list.add(i+1);

        return list;
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
