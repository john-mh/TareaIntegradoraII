package org.integradora2.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class MercadoLibre {
    private final List<Product> products;
    private final List<Order> orders;
    private final SearchEngine searchEngine;

    public MercadoLibre() {
        products = new LinkedList<>();
        orders = new LinkedList<>();
        searchEngine = new SearchEngine();
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addProduct(String name, String description, double price, int timesBought, int availableQuantity, int type) {
        products.add(new Product(name, description, price, timesBought, availableQuantity, type));
    }

    public void addOrder(List<Integer> productsIndexes, LocalDate date, String name, double totalPrice) {
        for(Integer i : productsIndexes) {
            increaseTimesBought(i, 1);
            decreaseProductQuantity(i, 1);
        }
        orders.add(new Order(searchEngine.collectMatches(products, productsIndexes), date, name, totalPrice));
    }

    public void deleteProduct(int index) {
        products.remove(index);
    }

    public void deleteOrder(int index) {
        orders.remove(index);
    }

    public void increaseTimesBought(int index, int quantity) {
        products.get(index).setTimesBought(products.get(index).getTimesBought() + quantity);
    }

    public void increaseProductQuantity(int index, int quantity) {
        products.get(index).setAvailableQuantity(products.get(index).getAvailableQuantity() + quantity);
    }

    public void decreaseProductQuantity(int index, int quantity) {
        products.get(index).setAvailableQuantity(products.get(index).getAvailableQuantity() - quantity);
    }

    public List<Product> searchProductByName(String query) {
        return searchEngine.searchingString(products, query, Product::getName);
    }

    public List<Product> searchProductByCategory(String query) {
        return searchEngine.searchingString(products, query, Product::getCategory);
    }

    public List<Product> searchProductByPrice(double query) {
        return searchEngine.searchingDouble(products, query, Product::getPrice);
    }

    public List<Product> searchProductByTimesBought(int query) {
        return searchEngine.searchingInt(products, query, Product::getTimesBought);
    }

    public List<Product> searchProductByRangePrice(double min, double max) {
        return searchEngine.searchingByRange(products, min, max, Product::getPrice);
    }

    public List<Order> searchOrderByName(String query) {
        return searchEngine.searchingString(orders, query, Order::name);
    }

    public List<Order> searchOrderByTotalPrice(double query) {
        return searchEngine.searchingDouble(orders, query, Order::totalPrice);
    }

    public List<Order> searchOrderByDate(LocalDate query) {
        return searchEngine.searchingDate(orders, query, Order::date);
    }

}
