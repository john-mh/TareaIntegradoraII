package org.integradora2.model;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents an order made by a user.
 * <p> It contains a list of products, the date of the order, the name of the user who made the order,
 * the total price of the order and the order's ID.
 * <p> It implements the {@link Searchable} interface to provide search functionality, as it can be searched by the {@link Searcher} functional interface.
 */
public record Order(List<Product> products, LocalDate date, String name, double totalPrice, int id) implements Searchable<Order> {

    /**
     *{@inheritDoc}
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append("\n")
                .append("Order by: ").append(name).append("\n")
                .append("Date: ").append(date).append("\n")
                .append(String.format("%-15s%-30s%-15s\n", "Product Type", "Product Name", "Price"));
        for (Product p : products) {
            sb.append(String.format("%-15s%-30s$%-15.2f\n", p.getCategory(), p.getName(), p.getPrice()));
        }
        sb.append(String.format("%-45s$%.2f\n", "Total Price:", totalPrice));
        return sb.toString();
    }

}
