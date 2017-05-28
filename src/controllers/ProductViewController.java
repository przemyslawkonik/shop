package controllers;

import controllers.interfaces.Init;
import controllers.interfaces.Refresher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import model.Product;
import tools.Creator;
import tools.DataLoader;

import java.net.URL;
import java.util.ResourceBundle;


public class ProductViewController implements Initializable, Refresher, Init {
    @FXML private TableView<Product> productTable;
    @FXML private ChoiceBox<Integer> productChoiceBox;
    private MainViewController mainViewController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    //obsluga dodania produktu do koszyka
    public void handleAddToBasket(ActionEvent event) {
        //zapisanie zaznaczonego produktu
        Product p = new Product(productTable.getSelectionModel().getSelectedItem());
        //zapisanie podanej ilosci
        int q = productChoiceBox.getSelectionModel().getSelectedItem();
        p.setQuantity(q);

        //dodanie produktu
        mainViewController.getCurrentBasketViewController().getCurrentBasket().add(p);
        //odswiezenie widoku
        mainViewController.refreshView();
    }

    @Override
    public void refreshView() {
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    @Override
    public void initView() {
        productTable.setItems(DataLoader.loadProductData());
        productTable.getColumns().setAll(Creator.createProductColumns());

        productChoiceBox.setItems(DataLoader.loadIntegerData());
        productChoiceBox.getSelectionModel().select(0);
    }
}
