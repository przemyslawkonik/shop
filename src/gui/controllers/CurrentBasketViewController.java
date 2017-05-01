package gui.controllers;

import gui.controllers.creator.Creator;
import gui.controllers.interfaces.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import application.*;
import tools.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CurrentBasketViewController implements Initializable, Refresher, Init {
    @FXML private TableView<Product> basketTable;
    @FXML private ChoiceBox<Integer> basketChoiceBox;
    @FXML private Label basketValueField;
    @FXML private TextField basketNameField;
    private MainViewController mainViewController;
    private Basket currentBasket;

    //konstruktor
    public CurrentBasketViewController() {
        currentBasket = new Basket();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    //obsluga usuniecia produktu z koszyka
    public void handleRemoveProduct(ActionEvent event) {
        Product p = new Product(basketTable.getSelectionModel().getSelectedItem());
        int q = basketChoiceBox.getSelectionModel().getSelectedItem();
        p.setQuantity(q);

        currentBasket.remove(p);
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
            currentBasket.setName(basketNameField.getText());
            //proba zapisu
            boolean result = currentBasket.save();
            if (result) {
                //zaaktualizowanie listy zapisanych koszykow
                mainViewController.getSavedBasketViewController().getSavedBaskets().add(new Basket(currentBasket));
                alertBox.displayInfo("Komunikat", "Twój koszyk został zapisany");
            } else {
                boolean choice = alertBox.displayChoice("Wybór", "Koszyk o podanej nazwie już istnieje. Czy chcesz go nadpisać?");
                if (choice) {
                    //zaaktualizowanie listy zapisanych koszykow
                    ObservableList<Basket> baskets = mainViewController.getSavedBasketViewController().getSavedBaskets();
                    for (Basket b : baskets) {
                        if (b.getName().equals(currentBasket.getName())) {
                            b.setProducts(currentBasket.getProducts());
                        }
                    }
                    //nadpisanie pliku koszyka
                    currentBasket.overwrite();
                    alertBox.displayInfo("Komunikat", "Twój koszyk został zapisany");
                }
            }
        }
        mainViewController.refreshView();
    }

    public Basket getCurrentBasket() {
        return currentBasket;
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    @Override
    public void refreshView() {
        basketTable.refresh();
        basketValueField.setText(""+currentBasket.getValue());
        basketNameField.clear();
    }

    @Override
    public void initView() {
        basketTable.setItems(currentBasket.getProducts());
        basketTable.getColumns().setAll(Creator.createBasketColumns());

        basketChoiceBox.setItems(DataLoader.loadIntegerData());
        basketChoiceBox.getSelectionModel().select(0);

        basketValueField.setText(""+currentBasket.getValue());
    }

}

