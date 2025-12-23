package ru.maximenko.upp;

public class Bike {

    private int id;
    private String model;
    private String brand;
    private double price;
    private boolean inStock;

    public Bike() {
    }

    public Bike(int id, String model, String brand, double price, boolean inStock) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.inStock = inStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
