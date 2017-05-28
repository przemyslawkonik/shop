package model;


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
    }

    //konstruktor
    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    private void calculateValue() {
        value = quantity * price;
        //zaokraglanie do 2 miejsc po przecinku
        value *= 1000;
        value = Math.round(value);
        value /= 1000;
    }

    public void increaseQuantity(int val) {
        quantity+=val;
    }

    public void decreaseQuantity(int val) {
        quantity-=val;
        if(quantity < 0)
            setQuantity(0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        calculateValue();
        return value;
    }

}
