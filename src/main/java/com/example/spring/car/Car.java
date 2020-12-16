package com.example.spring.car;

public final class Car {

    private String id;
    private String brand;
    private String category;
    private Integer releaseYear;

    public Car() {
    }

    public Car(String id, String brand, String category, Integer releaseYear) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.releaseYear = releaseYear;
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
