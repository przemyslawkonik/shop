package controllers;

import controllers.interfaces.Init;
import controllers.interfaces.Refresher;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Basket;
import model.Order;
import model.Product;
import tools.AlertBox;
import tools.Creator;
import tools.DataLoader;

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
    public void handleClear(ActionEvent event) throws IOException {
        if (!currentBasket.getProducts().isEmpty()) {
            AlertBox alertBox = new AlertBox();
            boolean result = alertBox.displayChoice("Wybierz", "Czy na pewno chcesz wyczyścić swój koszyk?");
            if (result) {
                currentBasket.getProducts().clear();
                mainViewController.refreshView();
            }
        }
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

    public void handleSubmitOrder(ActionEvent event) throws IOException {
        AlertBox alertBox = new AlertBox();
        boolean result = alertBox.displayChoice("Wybór", "Czy na pewno chcesz złożyć zamówienie?");
        if(result) {
            Basket basket = new Basket(currentBasket);
            Order order = new Order(basket);
            ObservableList<Order> orders = mainViewController.getOrderViewController().getOrders();

            //zapewnienie unikalnosci id zamowienia
            for(int i=0; i< orders.size(); i++) {
                if(order.getId() != orders.get(i).getId())
                    continue;
                else {
                    i=0;
                    order.generateId();
                }
            }
            //zapis jako plik
            order.save();
            //dodanie zamowienia do tabeli
            orders.add(order);
            mainViewController.refreshView();
            alertBox.displayInfo("Komunikat", "Twoje zamówienie zostało zrealizowane");
        }
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

