package controllers;

import interfaces.Refresher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable, Refresher {
    @FXML private ProductViewController productViewController;
    @FXML private BasketViewController basketViewController;
    @FXML private SavedBasketViewController savedBasketViewController;
    @FXML private Tab basketTab;
    @FXML private Tab savedBaskedTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productViewController.initController(this);
        basketViewController.initController(this);
        savedBasketViewController.initController(this);
        //wyswietlanie wartosci koszyka
        basketTab.setText("Koszyk ("+basketViewController.getCurrentBasket().getTotalValue()+"zł)");
        //wyswietlanie ilosci zapisanych koszykow
        savedBaskedTab.setText("Zapisane koszyki ("+savedBasketViewController.getSavedBaskets().size()+")");
    }

    public ProductViewController getProductViewController() {
        return productViewController;
    }

    public BasketViewController getBasketViewController() {
        return basketViewController;
    }

    public SavedBasketViewController getSavedBasketViewController() { return savedBasketViewController; }

    @Override
    public void refreshView() {
        productViewController.refreshView();
        basketViewController.refreshView();
        savedBasketViewController.refreshView();

        basketTab.setText("Koszyk ("+basketViewController.getCurrentBasket().getTotalValue()+"zł)");
        savedBaskedTab.setText("Zapisane koszyki ("+savedBasketViewController.getSavedBaskets().size()+")");
    }
}
