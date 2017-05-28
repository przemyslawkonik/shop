package gui.controllers;

import application.Order;
import application.Product;
import gui.controllers.creator.Creator;
import gui.controllers.interfaces.Init;
import gui.controllers.interfaces.Refresher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import tools.DataLoader;

import java.net.URL;
import java.util.ResourceBundle;


public class OrderViewController implements Initializable, Refresher, Init{
    @FXML private TableView<Order> orderTable;
    @FXML private TableView<Product> overviewOrderTable;
    @FXML private Label orderIdField;
    private MainViewController mainViewController;
    private ObservableList<Order> orders;


    //konstruktor
    public OrderViewController() {
        orders = DataLoader.loadOrderData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    @Override
    public void refreshView() {
        orderTable.refresh();
        overviewOrderTable.refresh();
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    @Override
    public void initView() {
        orderTable.setItems(orders);
        orderTable.getColumns().setAll(Creator.createOrderColumns());
        addOrderTableListener();

        overviewOrderTable.getColumns().setAll(Creator.createBasketColumns());
    }

    private void addOrderTableListener() {
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                overviewOrderTable.setItems(newValue.getBasket().getProducts());
                orderIdField.setText(""+newValue.getId());
                mainViewController.refreshView();
            } //else {
                //orderIdField.setText("");
                //overviewOrderTable.getItems().clear();
            //}
        });
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }


}
