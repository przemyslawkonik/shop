package tools;


import application.Basket;
import application.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataLoader {

    public static ObservableList<Product> loadProductData() {
        Scanner scanner = null;
        ObservableList<Product> products;

        try {
            scanner = new Scanner(new File("resources/database/products/product_database.txt"));
        } catch (FileNotFoundException e) {
        }
        products = FXCollections.observableArrayList();

        //wypelnienie listy
        while (scanner.hasNext()) {
            String name = scanner.next();
            int quantity = scanner.nextInt();
            double price = scanner.nextDouble();
            products.add(new Product(name, quantity, price));
        }
        scanner.close();
        return products;
    }

    public static ObservableList<Basket> loadBasketData() {
        ObservableList<Basket> baskets = FXCollections.observableArrayList();

        //zaladowanie plikow do tablicy
        File file = new File("resources/database/baskets/");
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
                baskets.add(new Basket(basketName, products));

                //czyscimy liste produktow przed nastepna iteracja
                products.clear();
            }
        } catch (FileNotFoundException e) {
        }
        return baskets;
    }

    public static ObservableList<Integer> loadIntegerData() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        final int size = 10;
        for (int i = 0; i < size; i++)
            list.add(i + 1);
        return list;
    }
}
