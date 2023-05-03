package org.integradora2.model;

import org.jetbrains.annotations.NotNull;

public class Product implements Searchable<Product>, Comparable<Product>{
    private ProductType type;
    private final String name;
    private String description;
    private double price;
    private int timesBought;
    private int availableQuantity;

    public Product(String name, double price, int type) {
        this.name = name;
        this.price = price;
        setCategory(type);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return type.getCategory();
    }
    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getTimesBought() {
        return timesBought;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimesBought(int timesBought) {
        this.timesBought = timesBought;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(int type) {
        switch (type) {
            case 1 -> this.type = ProductType.BOOKS;
            case 2 -> this.type = ProductType.ELECTRONICS;
            case 3 -> this.type = ProductType.CLOTHES_AND_ACCESSORIES;
            case 4 -> this.type = ProductType.FOOD_AND_BEVERAGES;
            case 5 -> this.type = ProductType.SPORTS;
            case 6 -> this.type = ProductType.TOYS_AND_GAMES;
            case 7 -> this.type = ProductType.BEAUTY_AND_PERSONAL_CARE;
            case 8 -> this.type = ProductType.STATIONERY;

        }
    }

    @Override
    public int compareTo(@NotNull Product o) {
        return 0;
    }
}
