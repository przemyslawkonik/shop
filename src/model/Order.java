package model;

import javafx.collections.ObservableList;
import model.enums.Status;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Order {
    private int id;
    private Date date;
    private Status status;
    private Basket basket;


    //konstruktor
    public Order(Basket basket) {
        this.basket = basket;
        status = Status.ZREALIZOWANE;
        date = new Date();
        generateId();
    }

    public Order(Basket basket, int id, String date, Status status) {
        this.basket = basket;
        this.id = id;
        this.status = status;

        try {
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            this.date = f.parse(date);
        } catch (ParseException e) {
        }
    }

    public void save() throws IOException {
        String path = "resources/database/orders/" + id + ".txt";
        File file = new File(path);
        PrintWriter pw;

        file.createNewFile();
        pw = new PrintWriter(path);
        //zapis danych
        pw.println(getDate());
        pw.println(getStatus());

        ObservableList<Product> products = basket.getProducts();
        for (Product p : products) {
            pw.println(p.getName());
            pw.println(p.getQuantity());
            pw.println(p.getPrice());
        }
        pw.close();
    }

    public void generateId() {
        Random rnd = new Random();
        //zakres
        int min = 100;
        int max = 999;
        id = rnd.nextInt(max-min-1)+min;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String s = f.format(date);
        return s;
    }

    public Status getStatus() {
        return status;
    }

    public Double getValue() {
        Double v = basket.getValue();
        return v;
    }

    public Basket getBasket() {
        return basket;
    }

}
