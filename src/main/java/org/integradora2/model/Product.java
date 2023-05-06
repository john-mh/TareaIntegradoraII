package org.integradora2.model;

/**
 * This class represents a product that can be searched.
 * <p> It implements the {@link Searchable} interface to provide search functionality, as it can be searched by the {@link Searcher} functional interface.
 */
public class Product implements Searchable<Product>{
    private ProductType type;
    private final String name;
    private final String description;
    private final double price;
    private int timesBought;
    private int availableQuantity;
    private final int id;

    /**
     * This constructor is used to create a new product.
     * @param name the name of the product
     * @param description the description of the product
     * @param price the price of the product
     * @param timesBought the number of times the product has been bought
     * @param availableQuantity the available quantity of the product
     * @param type the type of the product as an integer value (1-8)
     * @param id the unique identifier of the product
     */
    public Product(String name, String description, double price, int timesBought, int availableQuantity, int type, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.timesBought = timesBought;
        this.availableQuantity = availableQuantity;
        this.id = id;
        setCategory(type);
    }

    /**
     * Returns the category of the product associated with the given index.
     * @param index the index of the product category
     * @return the category of the product
     */
    public static String getCategoryByIndex(int index) {
        return ProductType.values()[index].getCategory();
    }

    /**
     * Returns the name of the product.
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the product.
     * @return the description of the product
     */
    public String getCategory() {
        return type.getCategory();
    }

    /**
     * Returns the price of the product.
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns how many times the product has been bought.
     * @return how many times the product has been bought
     */
    public int getTimesBought() {
        return timesBought;
    }

    /**
     * Returns the available quantity of the product.
     * @return the available quantity of the product
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the available quantity of the product.
     * @param availableQuantity the new available quantity of the product
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * Sets the number of times the product has been bought.
     * @param timesBought the new number of times the product has been bought
     */
    public void setTimesBought(int timesBought) {
        this.timesBought = timesBought;
    }

    /**
     * Sets the category of the product based on the given integer value.
     * @param type the integer value of the product category
     *            <p> 1 - Books
     *             <p> 2 - Electronics
     *             <p> 3 - Clothes and Accessories
     *             <p> 4 - Food and Beverages
     *             <p> 5 - Sports
     *             <p> 6 - Toys and Games
     *             <p> 7 - Beauty and Personal Care
     *             <p> 8 - Stationery
     *
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format("%-10d%-15s%-30s%-30s$%-10.2f%-10d%-10d\n", id, name, description, getCategory(), price, timesBought, availableQuantity);
    }

}
