package gui.controllers;

import gui.controllers.creator.Creator;
import gui.controllers.interfaces.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import application.*;
import tools.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SavedBasketViewController implements Initializable, Refresher, Init {
    @FXML private TableView<Basket> savedBasketTable;
    @FXML private TableView<Product> overviewBasketTable;
    @FXML private Label overviewBasketNameField;
    private MainViewController mainViewController;
    private ObservableList<Basket> savedBaskets;


    public SavedBasketViewController() {
        savedBaskets = DataLoader.loadBasketData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    //obsluga usuniecia zapisanego koszyka
    public void handleRemoveBasket(ActionEvent event) throws IOException {
        //wykonaj tylko jesli sa zapisane jakies koszyki i jesli uzytkownik zaznaczyl jakis koszyk
        if (!savedBaskets.isEmpty() && !savedBasketTable.getSelectionModel().isEmpty()) {

            //wyswietlenie okna z wyborem (tak lub nie) i zapamietanie decyzji uzytkownika
            AlertBox alertBox = new AlertBox();
            boolean result = alertBox.displayChoice("Wybierz", "Czy na pewno chcesz usunąć ten koszyk?");

            if (result) {
                //usuniecie pliku
                savedBasketTable.getSelectionModel().getSelectedItem().delete();
                //usuniecie z widoku
                savedBaskets.remove(savedBasketTable.getSelectionModel().getSelectedItem());
                //odswiezenie widoku
                mainViewController.refreshView();
            }
        }
    }

    public void handleMoveBasket(ActionEvent event) {
        //pobranie zaznaczonego koszyka
        Basket basket = new Basket(savedBasketTable.getSelectionModel().getSelectedItem());
        //pobranie glownego koszyka
        Basket currentBasket = mainViewController.getCurrentBasketViewController().getCurrentBasket();
        //dodanie produktow z zaznaczonego zapisanego koszyka do glownego koszyka
        currentBasket.add(basket.getProducts());
        //odswiezenie widoku
        mainViewController.refreshView();
    }

    public ObservableList<Basket> getSavedBaskets() {
        return savedBaskets;
    }

    @Override
    public void refreshView() {
        savedBasketTable.refresh();
        overviewBasketTable.refresh();
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    @Override
    public void initView() {
        savedBasketTable.setItems(savedBaskets);
        savedBasketTable.getColumns().setAll(Creator.createSavedBasketColumns());

        overviewBasketTable.getColumns().setAll(Creator.createBasketColumns());

        addOverviewBasketTableListener();
    }

    private void addOverviewBasketTableListener() {
        savedBasketTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                overviewBasketTable.setItems(newValue.getProducts());
                overviewBasketNameField.setText(newValue.getName());
                mainViewController.refreshView();
            } else {
                overviewBasketNameField.setText("");
                overviewBasketTable.getItems().clear();
            }
        });
    }

}
