package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Basket;
import sample.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SavedBasketViewController {
    private MainViewController mainViewController;


    public void init(MainViewController main) {
        mainViewController = main;
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
