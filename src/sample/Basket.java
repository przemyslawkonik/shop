package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class Basket {
    private ObservableList<Product> products;
    private double totalValue;
    private String name;


    //konstruktor
    public Basket(Basket basket) {
        products = basket.getProducts();
        name = basket.getName();
    }

    //konstruktor
    public Basket(ObservableList<Product> products) {
        this.products = products;
    }

    //konstruktor
    public Basket() {
        products = FXCollections.observableArrayList();
    }

    public void addProduct(Product product, int quantity) {
        int index = 0;
        boolean found = false;

        //przeszukanie listy
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName() == product.getName()) {
                found = true;
                index = i;
                break;
            }
        }
        //jesli ten produkt jest na liscie to zwieksz jego ilosc o podana
        if (found)
            products.get(index).increaseQuantity(quantity);
        else {  //jesli go nie ma to go dodaj
            Product p = new Product(product);
            p.setQuantity(quantity);
            products.add(p);
        }
    }

    public void removeProduct(Product product, int quantity) {
        int index = 0;
        boolean found = false;

        //przeszukanie listy
        for(int i=0; i<products.size(); i++) {
            if(products.get(i).getName() == product.getName()) {
                found = true;
                index = i;
                break;
            }
        }
        //jesli ten produkt jest na liscie to zmniejsz jego wartosc o podana
        if(found) {
            products.get(index).decreaseQuantity(quantity);
            //jesli ilosc produktu wynosi 0 to usun go z listy
            if(products.get(index).getQuantity() == 0)
                products.remove(index);
        }
    }

    public boolean save() throws IOException {
        String path = (getClass().getResource("/assets/saved_baskets/")).getPath();
        path += name + ".txt";
        File file = new File(path);

        if(file.exists()) {
            //plik o podanej nazwie istnieje
            return false;
        } else {
            file.createNewFile();
            //zapis danych
            PrintWriter pw = new PrintWriter(path);
            for (Product p : products) {
                pw.println(p.getName());
                pw.println(p.getQuantity());
                pw.println(p.getPrice());
            }
            pw.close();
            return true;
        }
    }

    private void calculateTotalValue() {
        totalValue = 0;
        for(Product p : products) {
            totalValue += p.getValue();
        }
        //zaokraglenie do 2 miejsc po przecinku
        totalValue *= 1000;
        totalValue = Math.round(totalValue);
        totalValue /= 1000;
    }

    public double getTotalValue() {
        calculateTotalValue();
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

}