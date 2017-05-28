package tools;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.enums.Status;


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

    public static ObservableList<TableColumn<Order, ?>> createOrderColumns() {
        ObservableList<TableColumn<Order, ?>> columns = FXCollections.observableArrayList();

        TableColumn<Order, Integer> id = new TableColumn<>("Nr zamówienia");
        id.setCellValueFactory(c-> new SimpleObjectProperty<Integer>(c.getValue().getId()));
        id.setMinWidth(120);
        id.setStyle("-fx-alignment: CENTER");

        TableColumn<Order, String> date = new TableColumn<>("Data złożenia");
        date.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getDate()));
        date.setMinWidth(120);
        date.setStyle("-fx-alignment: CENTER");

        TableColumn<Order, Status> status = new TableColumn<>("Status");
        status.setCellValueFactory(c-> new SimpleObjectProperty<Status>(c.getValue().getStatus()));
        status.setMinWidth(120);
        status.setStyle("-fx-alignment: CENTER");

        TableColumn<Order, Double> value = new TableColumn<>("Wartość");
        value.setCellValueFactory(c-> new SimpleObjectProperty<Double>(c.getValue().getValue()));
        value.setMinWidth(120);
        value.setStyle("-fx-alignment: CENTER");

        columns.addAll(id, date, status, value);
        return columns;
    }
}