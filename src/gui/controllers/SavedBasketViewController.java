package gui.controllers;

import gui.controllers.interfaces.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import application.*;
import tools.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class SavedBasketViewController implements Initializable, Refresher, InitController {
    @FXML private TableView<Basket> savedBasketTableView;
    @FXML private TableView<Product> inspectBasketTableView;
    @FXML private Label inspectBasketName;
    private MainViewController mainViewController;
    private ObservableList<Basket> savedBaskets;


    public SavedBasketViewController() {
        savedBaskets = loadSavedBaskets();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSavedBasketTableView();
        initInspectBasketTableView();
        addSavedBasketTableViewListener();
    }

    //obsluga usuniecia zapisanego koszyka
    public void handleRemoveBasket(ActionEvent event) throws IOException {
        //wykonaj tylko jesli sa zapisane jakies koszyki
        if (!savedBaskets.isEmpty()) {
            //pobranie sciezki do wybranego koszyka
            //String path = (getClass().getResource("/gui/assets/saved_baskets/")).getPath();
            String path = "resources/database/baskets/";
            path += savedBasketTableView.getSelectionModel().getSelectedItem().getName() + ".txt";
            File file = new File(path);

            //wyswietlenie okna z wyborem (tak lub nie) i zapamietanie decyzji uzytkownika
            AlertBox alertBox = new AlertBox();
            boolean result = alertBox.displayChoice("Wybierz", "Czy na pewno chcesz usunąć ten koszyk?");

            if (result) {
                //usuwanie pliku i elementu z tablicy
                file.delete();
                savedBaskets.remove(savedBasketTableView.getSelectionModel().getSelectedItem());
                //odswiezenie widoku
                mainViewController.refreshView();
            }
        }
    }

    public void handleMoveBasket(ActionEvent event) {
        //pobranie zaznaczonego koszyka
        Basket basket = new Basket(savedBasketTableView.getSelectionModel().getSelectedItem());
        //pobranie aktualnego koszyka
        Basket currentBasket = mainViewController.getBasketViewController().getCurrentBasket();

        //petla dodajaca produkty z zapisanego koszyka do aktualnego
        for(int i=0; i<basket.getProducts().size(); i++) {
            currentBasket.addProduct(basket.getProducts().get(i));
        }
        //odswiezenie widoku
        mainViewController.refreshView();
    }

    public ObservableList<Basket> getSavedBaskets() {
        return savedBaskets;
    }

    private ObservableList<Basket> loadSavedBaskets() {
        ObservableList<Basket> baskets = FXCollections.observableArrayList();

        //zaladowanie plikow do tablicy
        //String path = (getClass().getResource("/assets/saved_baskets/")).getPath();
        File file = new File("resources/database/baskets/");
        //String path = (getClass().getResource("/resources/database/baskets/")).getPath();
        //File file = new File(path);
        File[] files = file.listFiles();
        try {
            //petla zapisujaca koszyki
            for (int i = 0; i < files.length; i++) {
                ObservableList<Product> products = FXCollections.observableArrayList();
                String basketName;
                Scanner scanner = new Scanner(new File(files[i].getAbsolutePath()));

                //zapisywanie danych o produktach w poszczegolnym koszyku
                while (scanner.hasNext()) {
                    String name = scanner.next();
                    int quantity = scanner.nextInt();
                    Double price = Double.parseDouble(scanner.next());
                    products.add(new Product(name, quantity, price));
                }
                scanner.close();

                //zapisanie nazwy koszyka
                String s = files[i].getName();
                basketName = s.substring(0, s.length() - 4);

                //wlasciwe dodanie koszyka do listy
                baskets.add(new Basket(products));
                baskets.get(i).setName(basketName);

                //czyscimy liste produktow przed nastepna iteracja
                products.clear();
            }
        } catch (FileNotFoundException e) {
        }
        return baskets;
    }

    @Override
    public void refreshView() {
        savedBasketTableView.refresh();
        inspectBasketTableView.refresh();
    }

    @Override
    public void initController(MainViewController main) {
        mainViewController = main;
    }

    private void initSavedBasketTableView() {
        savedBasketTableView.setItems(savedBaskets);

        TableColumn<Basket, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Basket, Integer> quantity = new TableColumn<>("Ilość produktów");
        quantity.setCellValueFactory(c-> new SimpleObjectProperty<Integer>(c.getValue().getProducts().size()));
        quantity.setMinWidth(120);
        quantity.setStyle("-fx-alignment: CENTER");

        TableColumn<Basket, Double> value = new TableColumn<>("Wartość (zł)");
        value.setCellValueFactory(c-> new SimpleObjectProperty<Double>(c.getValue().getTotalValue()));
        value.setMinWidth(120);
        value.setStyle("-fx-alignment: CENTER");

        savedBasketTableView.getColumns().addAll(name, quantity, value);
    }

    private void initInspectBasketTableView() {
        TableColumn<Product, String> name = new TableColumn<>("Nazwa");
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        name.setMinWidth(120);
        name.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> price = new TableColumn<>("Cena (zł)");
        price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        price.setMinWidth(120);
        price.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Integer> quantity = new TableColumn<>("Ilość");
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        quantity.setMinWidth(120);
        quantity.setStyle("-fx-alignment: CENTER");

        TableColumn<Product, Double> value = new TableColumn<>("Wartość (zł)");
        value.setCellValueFactory(new PropertyValueFactory<Product, Double>("value"));
        value.setMinWidth(120);
        value.setStyle("-fx-alignment: CENTER");

        inspectBasketTableView.getColumns().addAll(name, price, quantity, value);
    }

    private void addSavedBasketTableViewListener() {
        savedBasketTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                inspectBasketTableView.setItems(newValue.getProducts());
                inspectBasketName.setText(newValue.getName());
                mainViewController.refreshView();
            } else {
                inspectBasketName.setText("");
                inspectBasketTableView.getItems().clear();
            }
        });
    }

}
