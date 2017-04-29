package controllers;

import interfaces.InitController;
import interfaces.Refresher;
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


public class BasketViewController implements Initializable, Refresher, InitController {
    @FXML private TableView<Product> basketTableView;
    @FXML private ChoiceBox<Integer> basketChoiceBox;
    @FXML private Label totalValue;
    @FXML private TextField basketNameField;
    private MainViewController mainViewController;
    private Basket currentBasket;

    //konstruktor
    public BasketViewController() {
        currentBasket = new Basket();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initBasketTableView();
        initBasketChoiceBox();
        totalValue.setText("0");
    }

    //obsluga usuniecia produktu z koszyka
    public void handleRemoveProduct(ActionEvent event) {
        Product p = new Product(basketTableView.getSelectionModel().getSelectedItem());
        int q = basketChoiceBox.getSelectionModel().getSelectedItem();

        currentBasket.removeProduct(p,q);
        mainViewController.refreshView();
    }

    //obluga wyczyszenia koszyka
    public void handleClear(ActionEvent event) {
        currentBasket.getProducts().clear();
        mainViewController.refreshView();
    }

    //obsluga zapisania koszyka
    public void handleSaveBasket(ActionEvent event) throws IOException {
        AlertBox alertBox = new AlertBox();
        //jesli nie podano nazwy koszyka
        if (basketNameField.getText().isEmpty()) {
            alertBox.displayInfo("Błąd", "Podaj nazwę koszyka!");
        }
        //jesli koszyk jest pusty
        else if (currentBasket.getProducts().isEmpty()) {
            alertBox.displayInfo("Błąd", "Twój koszyk jest pusty!");
        } else {
            //pobranie aktualnych zapisanych koszykow
            ObservableList<Basket> baskets = mainViewController.getSavedBasketViewController().getSavedBaskets();
            String name = basketNameField.getText();
            boolean exists = false;
            int index = 0;
            //sprawdzamy czy koszyk o podanej nazwie istnieje
            for (int i = 0; i < baskets.size(); i++) {
                if (baskets.get(i).getName().equals(name)) {
                    exists = true;
                    index = i;
                    break;
                }
            }
            currentBasket.setName(name);
            boolean choice = false;
            //jesli koszyk o podanej nazwie istnieje, uzytkownik dokonuje wyboru
            if (exists) {
                choice = alertBox.displayChoice("Wybór", "Koszyk o podanej nazwie już istnieje. Czy chcesz go nadpisać?");
                if (choice) {
                    alertBox.displayInfo("Komunikat", "Twój koszyk został zapisany");
                    //zaaktualizowanie listy zapisanych koszykow
                    mainViewController.getSavedBasketViewController().getSavedBaskets().set(index, new Basket(currentBasket));
                }
            } else {
                alertBox.displayInfo("Komunikat", "Twój koszyk został zapisany");
                //zaaktualizowanie listy zapisanych koszykow
                mainViewController.getSavedBasketViewController().getSavedBaskets().add(new Basket(currentBasket));
            }
            //zapis
            currentBasket.save();
            //odswiezenie widoku
            mainViewController.refreshView();
        }
    }

    public Basket getCurrentBasket() {
        return currentBasket;
    }

    @Override
    public void refreshView() {
        basketTableView.refresh();
        totalValue.setText(""+currentBasket.getTotalValue());
        basketNameField.setText("");
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    private void initBasketTableView() {
        basketTableView.setItems(currentBasket.getProducts());

        TableColumn<Product, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> price = new TableColumn<>("Cena (zł)");
        price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        price.setMinWidth(120);
        price.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Integer> quantity = new TableColumn<>("Ilość");
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        quantity.setMinWidth(120);
        quantity.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> value = new TableColumn<>("Wartość (zł)");
        value.setCellValueFactory(new PropertyValueFactory<Product, Double>("value"));
        value.setMinWidth(120);
        value.setStyle("-fx-alignment: CENTER");

        basketTableView.getColumns().addAll(name, price, quantity, value);
    }

    private void initBasketChoiceBox() {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        final int size = 10;
        for(int i=0; i<size; i++)
            list.add(i+1);

        basketChoiceBox.setItems(list);
        basketChoiceBox.getSelectionModel().select(0);
    }
}
