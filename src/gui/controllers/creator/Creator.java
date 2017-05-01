package gui.controllers.creator;

import application.Basket;
import application.Product;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class Creator {

    public static ObservableList<TableColumn<Product, ?>> createProductColumns() {
        ObservableList<TableColumn<Product, ?>> columns = FXCollections.observableArrayList();

        TableColumn<Product, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> price = new TableColumn<>("Cena (zł)");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setMinWidth(120);
        price.setStyle("-fx-alignment: CENTER");

        columns.addAll(name, price);
        return columns;
    }

    public static ObservableList<TableColumn<Product, ?>> createBasketColumns() {
        ObservableList<TableColumn<Product, ?>> columns = FXCollections.observableArrayList();

        TableColumn<Product, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> price = new TableColumn<>("Cena (zł)");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setMinWidth(120);
        price.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Integer> quantity = new TableColumn<>("Ilość");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantity.setMinWidth(120);
        quantity.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> value = new TableColumn<>("Wartość (zł)");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        value.setMinWidth(120);
        value.setStyle("-fx-alignment: CENTER");

        columns.addAll(name, price, quantity, value);
        return columns;
    }

    public static ObservableList<TableColumn<Basket, ?>> createSavedBasketColumns() {
        ObservableList<TableColumn<Basket, ?>> columns = FXCollections.observableArrayList();

        TableColumn<Basket, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Basket, Integer> quantity = new TableColumn<>("Ilość produktów");
        quantity.setCellValueFactory(c-> new SimpleObjectProperty<Integer>(c.getValue().getProducts().size()));
        quantity.setMinWidth(120);
        quantity.setStyle("-fx-alignment: CENTER");

        TableColumn<Basket, Double> value = new TableColumn<>("Wartość (zł)");
        value.setCellValueFactory(c-> new SimpleObjectProperty<Double>(c.getValue().getValue()));
        value.setMinWidth(120);
        value.setStyle("-fx-alignment: CENTER");

        columns.addAll(name, quantity, value);
        return columns;
    }
}
