package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Product;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ProductViewController implements Initializable {
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, String> productColumnName;
    @FXML private TableColumn<Product, Double> productColumnPrice;
    @FXML private ChoiceBox<Integer> productChoiceBox;
    private MainViewController mainViewController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //pobranie bazy produktow
        productTableView.setItems(getProductsData());
        //ustawienie kolumn tabeli
        productColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //ustawienie choiceboxa
        productChoiceBox.setItems(getChoiceBoxData());
        productChoiceBox.getSelectionModel().select(0);
    }

    public void init(MainViewController main) {
        mainViewController = main;
    }

    //obsluga dodania produktu do koszyka
    public void handleAddToBasket(ActionEvent event) {
        //zapisanie zaznaczonego produktu
        Product p = new Product(productTableView.getSelectionModel().getSelectedItem());
        //zapisanie podanej ilosci
        int q = productChoiceBox.getSelectionModel().getSelectedItem();

        //dodanie produktu
        mainViewController.getBasketViewController().getCurrentBasket().addProduct(p,q);
        //odswiezenie danych w tabeli
        mainViewController.getBasketViewController().getBasketTableView().refresh();
        //wypisanie calkowitej wartosci koszyka
        mainViewController.getBasketViewController().getTotalValue().setText(""+mainViewController.getBasketViewController().getCurrentBasket().getTotalValue());
    }

    public ObservableList<Product> getProductsData() {
        Scanner scanner;
        ObservableList<Product> products;

        scanner = new Scanner(getClass().getResourceAsStream("/assets/product_database.txt"));
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

    public ObservableList<Integer> getChoiceBoxData() {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        final int size = 10;
        for(int i=0; i<size; i++)
            list.add(i+1);

        return list;
    }
}
