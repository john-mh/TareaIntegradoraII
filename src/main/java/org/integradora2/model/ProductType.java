package org.integradora2.model;

/**
 * This enum represents the different types of products that can be searched.
 */
public enum ProductType {

    BOOKS("Books"), ELECTRONICS("Electronics"), CLOTHES_AND_ACCESSORIES("Clothes & Accessories"), FOOD_AND_BEVERAGES("Food & Beverages"), SPORTS("Sports"), TOYS_AND_GAMES("Toys & Games"), BEAUTY_AND_PERSONAL_CARE("Beauty & Personal Care"), STATIONERY("Stationery");

    private final String category;

    ProductType(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

}
