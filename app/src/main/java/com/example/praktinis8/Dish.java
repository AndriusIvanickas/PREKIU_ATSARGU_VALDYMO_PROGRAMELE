package com.example.praktinis8;

public class Dish {
    private int imageResource;
    private String name;
    private String description;
    private String price;

    public Dish(int imageResource, String name, String description, String price) {
        this.imageResource = imageResource;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
