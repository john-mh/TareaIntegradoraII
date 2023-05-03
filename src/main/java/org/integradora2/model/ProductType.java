package org.integradora2.model;

public enum ProductType {

    BOOKS("Books"), ELECTRONICS("Electronics"), CLOTHES_AND_ACCESSORIES("Clothes & Accessories"), FOOD_AND_BEVERAGES("Food & Beverages"), SPORTS("Sports"), TOYS_AND_GAMES("Toys & Games"), BEAUTY_AND_PERSONAL_CARE("Beauty & Personal Care"), STATIONERY("Stationery");

    private String category;

    ProductType(String category) {
    }

    public String getCategory() {
        return this.category;
    }

}
