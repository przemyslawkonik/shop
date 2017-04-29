package controllers;

import interfaces.Refresher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable, Refresher {
    @FXML private ProductViewController productViewController;
    @FXML private BasketViewController basketViewController;
    @FXML private SavedBasketViewController savedBasketViewController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productViewController.init(this);
        basketViewController.init(this);
        savedBasketViewController.init(this);
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
    }
}
