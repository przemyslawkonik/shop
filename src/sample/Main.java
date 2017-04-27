package sample;

import controllers.MainViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sun.swing.BakedArrayList;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Observable;
import java.util.Scanner;

public class Main extends Application {

    TableView<Product> productTable;
    TableView<Product> basketTable;
    Basket basket;
    Basket products;
    Button addButton;
    Button removeButton;

    @Override
    public void start(Stage primaryStage) throws Exception{

        /**********************************************************************************************/
        //produkty
        TableColumn<Product, String> productNameColumn = new TableColumn<>("Nazwa");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productNameColumn.setMinWidth(150);

        /*
        TableColumn<Product, Integer> productQuantityColumn = new TableColumn<>("Ilość");
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        productQuantityColumn.setMinWidth(150);
        */

        TableColumn<Product, Double> productPriceColumn = new TableColumn<>("Cena");
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPriceColumn.setMinWidth(150);

        products = new Basket(getProducts());

        productTable = new TableView<>();
        productTable.setItems(products.getProducts());
        //productTable.setItems(getProducts());
        productTable.getColumns().addAll(productNameColumn, productPriceColumn);
        /*********************************************************************************************/
        //koszyk
        TableColumn<Product, String> basketNameColumn = new TableColumn<>("Nazwa");
        basketNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        basketNameColumn.setMinWidth(150);

        TableColumn<Product, Integer> basketQuantityColumn = new TableColumn<>("Ilość");
        basketQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        basketQuantityColumn.setMinWidth(150);

        TableColumn<Product, Double> basketPriceColumn = new TableColumn<>("Cena");
        basketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        basketPriceColumn.setMinWidth(150);

        TableColumn<Product, Double> basketValueColumn = new TableColumn<>("Wartość");
        basketValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        basketValueColumn.setMinWidth(150);

        //testy koszyka

        basket = new Basket();
        /*
        basket.addProduct(new Product("Jabłko", 5));
        basket.addProduct(new Product("Jabłko", 5));
        basket.addProduct(new Product("maslo", 4.23));
        basket.addProduct(new Product("pieczywo", 3.33));
        basket.addProduct(new Product("Jabłko", 5));
        basket.removeProduct(new Product("Jabłko", 5));
        basket.removeProduct(new Product("maslo", 4.23));
        */

        basketTable = new TableView<>();
        basketTable.setItems(basket.getProducts());
        basketTable.getColumns().addAll(basketNameColumn, basketQuantityColumn, basketPriceColumn, basketValueColumn);
        /************************************************************************************************/
        //przyciski
        addButton = new Button("Dodaj do koszyka");
        addButton.setOnAction( e -> {
            Product p = productTable.getSelectionModel().getSelectedItem();
            basket.addProduct(p,1);
            //basketTable.setItems(basket.getProductsList());
            basketTable.refresh();
            productTable.refresh();
        });

        removeButton = new Button("Usuń z koszyka");
        removeButton.setOnAction( e -> {
            Product p = basketTable.getSelectionModel().getSelectedItem();
            basket.removeProduct(p, 1);
            //products.addProduct(p);
            //basketTable.setItems(basket.getProductsList());
            basketTable.refresh();
            productTable.refresh();
        });



        /****************************************************************************************************/
        HBox layout = new HBox();
        layout.getChildren().addAll(productTable, basketTable, addButton, removeButton);
        Scene scene = new Scene(layout);



        //Parent root = FXMLLoader.load(getClass().getResource("/assets/fxml/MainView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/fxml/mainView.fxml"));
        //MainViewController main = loader.getController();
        //main.mainDisplay();
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(scene);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public ObservableList<Product> getProducts() {
        //deklaracje
        Scanner scanner;
        ObservableList<Product> products;

        //inicjalizacja
        scanner = new Scanner(getClass().getResourceAsStream("/assets/product_database.txt"));
        products = FXCollections.observableArrayList();

        //wypelnienie listy
        while(scanner.hasNext()) {
            String name = scanner.next();
            int quantity = scanner.nextInt();
            double price = scanner.nextDouble();
            products.add(new Product(name, quantity, price));
        }

        //zamkniecie strumieni
        scanner.close();

        //zwracamy liste
        return products;
    }

}


