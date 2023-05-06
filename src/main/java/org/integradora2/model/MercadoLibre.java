package org.integradora2.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class MercadoLibre {
    private final List<Product> products;
    private final List<Order> orders;

    public MercadoLibre() {
        products = new LinkedList<>();
        orders = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getCategory(int index){
        return Product.getCategoryByIndex(index);
    }

    public String displayProducts(){
        StringBuilder sb = displayProducts(new StringBuilder());
        return sb.toString();
    }

    private StringBuilder displayProducts(StringBuilder sb){
        String format = "%-5s | %-30s | %-20s | %-10s | %-15s | %-5s | %-10s\n";
        String header = String.format(format, "ID", "Name", "Description", "Price", "Type", "Qty", "Times Bought");
        int index = 0;
        sb.append(header);
        for (Product p : products) {
            sb.append(String.format("%-10d%-15s%-30s%-30s$%-10.2f%-10d%-10d\n", index, p.getName(), p.getDescription(), p.getCategory(), p.getPrice(), p.getTimesBought(), p.getAvailableQuantity()));
            index++;
        }
        return sb;
    }

    public String displayOrders(){
        StringBuilder sb = displayOrders(new StringBuilder());
        return sb.toString();
    }

    private StringBuilder displayOrders(StringBuilder sb){
        int index = 0;
        for(Order order : orders){
            sb.append("Order ID: ").append(index).append("\n")
                    .append("Order by: ").append(order.name()).append("\n")
                    .append("Date: ").append(order.date()).append("\n")
                    .append(String.format("%-15s%-30s%-15s\n", "Product Type", "Product Name", "Price"));
            for (Product p : products) {
                sb.append(String.format("%-15s%-30s$%-15.2f\n", p.getCategory(), p.getName(), p.getPrice()));
            }
            sb.append(String.format("%-45s$%.2f\n", "Total Price:", order.totalPrice()));
            index++;
        }
        return sb;
    }

    public void addProduct(String name, String description, double price, int timesBought, int availableQuantity, int type) {
        products.add(new Product(name, description, price, timesBought, availableQuantity, type));
    }

    public void addOrder(List<Integer> productsIndexes, LocalDate date, String name, double totalPrice) {
        for(Integer i : productsIndexes) {
            increaseTimesBought(i, 1);
            decreaseProductQuantity(i, 1);
        }
        orders.add(new Order(Searcher.collectMatches(products, productsIndexes), date, name, totalPrice));
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
        return Searcher.searchingString(products, query, Product::getName);
    }

    public List<Product> searchProductByCategory(String query) {
        return Searcher.searchingString(products, query, Product::getCategory);
    }

    public List<Product> searchProductByPrice(double query) {
        return Searcher.searchingDouble(products, query, Product::getPrice);
    }

    public List<Product> searchProductByTimesBought(int query) {
        return Searcher.searchingInt(products, query, Product::getTimesBought);
    }

    public List<Product> searchProductByRangePrice(double min, double max) {
        return Searcher.searchingByRange(products, min, max, Product::getPrice);
    }

    public List<Order> searchOrderByName(String query) {
        return Searcher.searchingString(orders, query, Order::name);
    }

    public List<Order> searchOrderByTotalPrice(double query) {
        return Searcher.searchingDouble(orders, query, Order::totalPrice);
    }

    public List<Order> searchOrderByDate(LocalDate query) {
        return Searcher.searchingDate(orders, query, Order::date);
    }
}
