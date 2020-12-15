package com.example.spring.car;

public final class Car {

    private String id;
    private String brand;
    private String category;

    public Car() {
    }

    public Car(String id, String brand, String category) {
        this.id = id;
        this.brand = brand;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
