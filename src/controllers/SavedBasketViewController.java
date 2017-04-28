package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Basket;
import sample.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class SavedBasketViewController implements Initializable {
    @FXML private TableView<Basket> savedBasketTableView;
    @FXML private TableColumn<Basket, String> nameColumn;
    @FXML private TableColumn<Basket, Integer> quantityColumn;
    @FXML private TableColumn<Basket, Double> valueColumn;
    private MainViewController mainViewController;
    private ObservableList<Basket> baskets;


    public SavedBasketViewController() {
        baskets = loadSavedBaskets();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        savedBasketTableView.setItems(baskets);

        //ustawienie kolumn
        nameColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
        quantityColumn.setCellValueFactory(c-> new SimpleObjectProperty<Integer>(c.getValue().getProducts().size()));
        valueColumn.setCellValueFactory(c-> new SimpleObjectProperty<Double>(c.getValue().getTotalValue()));
    }

    public void init(MainViewController main) {
        mainViewController = main;
    }

    //obsluga usuniecia zapisanego koszyka
    public void handleRemoveBasket(ActionEvent event) {
        //pobranie sciezki do wskazanego koszyka
        String path = (getClass().getResource("/assets/saved_baskets/")).getPath();
        path += savedBasketTableView.getSelectionModel().getSelectedItem().getName() + ".txt";
        File file = new File(path);


        //usuwanie pliku i elementu z tablicy
        file.delete();
        baskets.remove(savedBasketTableView.getSelectionModel().getSelectedItem());
        //odswiezenie widoku
        savedBasketTableView.refresh();
    }

    public TableView<Basket> getSavedBasketTableView() {
        return savedBasketTableView;
    }

    private ObservableList<Basket> loadSavedBaskets() {
        ObservableList<Basket> baskets = FXCollections.observableArrayList();

        //zaladowanie plikow do tablicy
        String path = (getClass().getResource("/assets/saved_baskets/")).getPath();
        File file = new File(path);
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

}
