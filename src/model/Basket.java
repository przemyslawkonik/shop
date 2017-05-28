package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;


public class Basket {
    private ObservableList<Product> products;
    private double value;
    private String name;


    //konstruktor
    public Basket(Basket basket) {
        products = FXCollections.observableArrayList();
        setProducts(basket.getProducts());
        name = basket.getName();
    }

    //konstruktor
    public Basket(String name, ObservableList<Product> products) {
        this.products = FXCollections.observableArrayList();
        this.name = name;
        setProducts(products);
    }

    //konstruktor
    public Basket() {
        products = FXCollections.observableArrayList();
        name = "Bez nazwy";
    }

    public void add(Product p) {
        int index = 0;
        boolean found = false;

        //przeszukanie listy
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(p.getName())) {
                found = true;
                index = i;
                break;
            }
        }
        //jesli ten produkt jest na liscie to zwieksz jego ilosc o podana
        if (found) {
            int q = p.getQuantity();
            products.get(index).increaseQuantity(q);
        }
        //jesli go nie ma to go dodaj
        else {
            products.add(p);
        }
    }

    public void add(ObservableList<Product> products) {
        //petla zewnetrzna
        for(Iterator<Product> iterator = products.iterator(); iterator.hasNext(); ) {
            Product p = iterator.next();
            //petla wewnetrzna
            for (Product main : this.products) {
                //jesli produkt sie powtarza to zwieksz jego wartosc i usun go ze zmiennej ktora jest argumentem tej funkcji
                if (main.getName().equals(p.getName())) {
                    main.increaseQuantity(p.getQuantity());
                    iterator.remove();
                }
            }
        }
        //dodaj pozostale na liscie produkty
        for (int i = 0; i < products.size(); i++) {
            this.products.add(products.get(i));
        }
    }

    public void remove(Product p) {
        int index = 0;
        boolean found = false;

        //przeszukanie listy
        for(int i=0; i<products.size(); i++) {
            if(products.get(i).getName().equals(p.getName())) {
                found = true;
                index = i;
                break;
            }
        }
        //jesli ten produkt jest na liscie to zmniejsz jego wartosc o podana
        if(found) {
            int q = p.getQuantity();
            products.get(index).decreaseQuantity(q);
            //jesli ilosc produktu wynosi 0 to usun go z listy
            if(products.get(index).getQuantity() == 0)
                products.remove(index);
        }
    }

    public boolean save() throws IOException {
        String path = "resources/database/baskets/"+name+".txt";
        File file = new File(path);
        PrintWriter pw;

        if (file.exists())
            return false;
        else {
            file.createNewFile();
            pw = new PrintWriter(path);
            //zapis danych
            for (Product p : products) {
                pw.println(p.getName());
                pw.println(p.getQuantity());
                pw.println(p.getPrice());
            }
            pw.close();
            return true;
        }
    }

    public void overwrite() throws IOException {
        String path = "resources/database/baskets/"+name+".txt";
        PrintWriter pw;

        //zapis danych
        pw = new PrintWriter(new FileOutputStream(path, false));
        for (Product p : products) {
            pw.println(p.getName());
            pw.println(p.getQuantity());
            pw.println(p.getPrice());
        }
        pw.close();
    }

    public void delete() {
        String path = "resources/database/baskets/";
        path += name + ".txt";
        File file = new File(path);
        file.delete();
    }

    private void calculateValue() {
        value = 0;
        for(Product p : products) {
            value += p.getValue();
        }
        //zaokraglenie do 2 miejsc po przecinku
        value *= 1000;
        value = Math.round(value);
        value /= 1000;
    }

    public double getValue() {
        calculateValue();
        return value;
    }

    public void setValue(double totalValue) {
        this.value = totalValue;
    }

    public void setProducts(ObservableList<Product> products) {
        if(!this.products.isEmpty())
            this.products.clear();
        for(int i=0; i<products.size(); i++)
            this.products.add(new Product(products.get(i)));
    }

    public ObservableList<Product> getProducts() { return products; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

}
