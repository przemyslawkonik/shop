package gui.controllers;

import gui.controllers.interfaces.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable, Refresher, Init{
    @FXML private ProductViewController productViewController;
    @FXML private CurrentBasketViewController currentBasketViewController;
    @FXML private SavedBasketViewController savedBasketViewController;
    @FXML private OrderViewController orderViewController;
    @FXML private Tab currentBasketTab;
    @FXML private Tab savedBaskedTab;
    @FXML private Tab orderTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        initController(this);
    }

    @Override
    public void initController(MainViewController main) {
        productViewController.initController(main);
        currentBasketViewController.initController(main);
        savedBasketViewController.initController(main);
        orderViewController.initController(main);
    }

    @Override
    public void initView() {
        //wyswietlanie wartosci koszyka
        currentBasketTab.setText("Koszyk ("+currentBasketViewController.getCurrentBasket().getValue()+"zł)");
        //wyswietlanie ilosci zapisanych koszykow
        savedBaskedTab.setText("Zapisane koszyki ("+savedBasketViewController.getSavedBaskets().size()+")");
        //wyswietlanie ilosci zamowien
        orderTab.setText("Lista zamówień ("+getOrderViewController().getOrders().size()+")");
    }

    @Override
    public void refreshView() {
        productViewController.refreshView();
        currentBasketViewController.refreshView();
        savedBasketViewController.refreshView();
        orderViewController.refreshView();

        currentBasketTab.setText("Koszyk ("+currentBasketViewController.getCurrentBasket().getValue()+"zł)");
        savedBaskedTab.setText("Zapisane koszyki ("+savedBasketViewController.getSavedBaskets().size()+")");
        orderTab.setText("Lista zamówień ("+getOrderViewController().getOrders().size()+")");
    }

    public ProductViewController getProductViewController() {
        return productViewController;
    }

    public CurrentBasketViewController getCurrentBasketViewController() {return currentBasketViewController; }

    public SavedBasketViewController getSavedBasketViewController() { return savedBasketViewController; }

    public OrderViewController getOrderViewController() { return orderViewController; }

}
