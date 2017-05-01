package gui.controllers;

import gui.controllers.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import application.Product;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ProductViewController implements Initializable, Refresher, InitController {
    @FXML private TableView<Product> productTableView;
    @FXML private ChoiceBox<Integer> productChoiceBox;
    private MainViewController mainViewController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductTableView();
        initChoiceBox();
    }

    //obsluga dodania produktu do koszyka
    public void handleAddToBasket(ActionEvent event) {
        //zapisanie zaznaczonego produktu
        Product p = new Product(productTableView.getSelectionModel().getSelectedItem());
        //zapisanie podanej ilosci
        int q = productChoiceBox.getSelectionModel().getSelectedItem();

        //dodanie produktu
        mainViewController.getBasketViewController().getCurrentBasket().addProduct(p,q);
        //odswiezenie widoku
        mainViewController.refreshView();
    }

    private ObservableList<Product> getProductsData() {
        Scanner scanner;
        ObservableList<Product> products;
        scanner = new Scanner(getClass().getResourceAsStream("/database/products/product_database.txt"));
        products = FXCollections.observableArrayList();

        //wypelnienie listy
        while(scanner.hasNext()) {
            String name = scanner.next();
            int quantity = scanner.nextInt();
            double price = scanner.nextDouble();
            products.add(new Product(name, quantity, price));
        }
        scanner.close();
        return products;
    }

    @Override
    public void refreshView() {
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    private void initProductTableView() {
        productTableView.setItems(getProductsData());

        TableColumn<Product, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> price = new TableColumn<>("Cena (zł)");
        price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        price.setMinWidth(120);
        price.setStyle("-fx-alignment: CENTER");

        productTableView.getColumns().addAll(name, price);
    }

    private void initChoiceBox() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        final int size = 10;
        for(int i=0; i<size; i++)
            list.add(i+1);

        productChoiceBox.setItems(list);
        productChoiceBox.getSelectionModel().select(0);
    }
}
