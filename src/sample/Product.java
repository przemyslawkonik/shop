package sample;


import javafx.scene.control.Button;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private double value;

    //konstruktor
    public Product(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        calculateValue();
    }

    //konstruktor
    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        calculateValue();
    }

    //prywatna
    private void calculateValue() {
        value = quantity * price;
        //zaokraglanie do 2 miejsc po przecinku
        value *= 1000;
        value = Math.round(value);
        value /= 1000;
    }

    public void increaseQuantity(int val) {
        quantity+=val;
        calculateValue();
    }

    public void decreaseQuantity(int val) {
        quantity-=val;
        if(quantity < 0)
            setQuantity(0);
        calculateValue();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
        calculateValue();
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateValue();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
