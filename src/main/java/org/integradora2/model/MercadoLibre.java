package org.integradora2.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Order> getOrders() {
        return orders;
    }

    public String getCategory(int index){
        return Product.getCategoryByIndex(index);
    }

    public double getProductPrice(int index){
        return products.get(index).getPrice();
    }

    public void setProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public void setOrders(List<Order> orders) {
        this.orders.addAll(orders);
    }

    public String displayProducts(){
        StringBuilder sb = displayProducts(new StringBuilder(), products);
        return sb.toString();
    }

    private StringBuilder displayProducts(StringBuilder sb, List<Product> products){
        String format = "%-5s | %-30s | %-20s | %-10s | %-15s | %-5s | %-10s\n";
        String header = String.format(format, "ID", "Name", "Description", "Price", "Type", "Qty", "Times Bought");
        sb.append(header);
        for (Product p : products) {
            sb.append(p.toString());
        }
        return sb;
    }


    public String displayOrders(){
        StringBuilder sb = displayOrders(new StringBuilder(), orders);
        return sb.toString();
    }

    private StringBuilder displayOrders(StringBuilder sb, List<Order> orders){
        for(Order order : orders){
            sb.append(order.toString());
        }
        return sb;
    }

    public String displaySortedProductList(List<Product> list, String sortType, String sortDirection){
        Comparator<Product> comparator = switch (sortType) {
            case "name" -> Comparator.comparing(Product::getName);
            case "price" -> Comparator.comparing(Product::getPrice);
            case "type" -> Comparator.comparing(Product::getCategory);
            case "timesBought" -> Comparator.comparing(Product::getTimesBought);
            default -> null;
        };

        list.sort(comparator);

        if (sortDirection.equals("desc")) {
            Collections.reverse(list);
        }
        return displayProducts(new StringBuilder(), list).toString();
    }

    public String displaySortedOrderList(List<Order> list, String sortType, String sortDirection){
        Comparator<Order> comparator = switch (sortType) {
            case "name" -> Comparator.comparing(Order::name);
            case "date" -> Comparator.comparing(Order::date);
            case "totalPrice" -> Comparator.comparing(Order::totalPrice);
            default -> null;
        };

        list.sort(comparator);

        if (sortDirection.equals("desc")) {
            Collections.reverse(list);
        }
        return displayOrders(new StringBuilder(), list).toString();
    }


    public void addProduct(String name, String description, double price, int timesBought, int availableQuantity, int type) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Empty name not allowed");
        }
        if(price < 0) {
            throw new IllegalArgumentException("Negative price not allowed");
        }
        if(timesBought < 0) {
            throw new IllegalArgumentException("Negative times bought not allowed");
        }
        if(availableQuantity < 0) {
            throw new IllegalArgumentException("Negative available quantity not allowed");
        }
        if(type < 0 || type > 8) {
            throw new IllegalArgumentException("Invalid type");
        }
        if(price > 10000) {
            throw new IllegalArgumentException("Price too high");
        }
        if(price == 0) {
            throw new IllegalArgumentException("Price cannot be 0");
        }
        products.add(new Product(name, description, price, timesBought, availableQuantity, type, products.size()));
    }

    public void addOrder(@NotNull List<Integer> productsIndexes, LocalDate date, String name, double totalPrice) {
        if(productsIndexes.isEmpty()) {
            throw new IllegalArgumentException("Empty order not allowed");
        }
        if(date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Future date not allowed");
        }
        if(date.isBefore(LocalDate.of(2000, 1, 1))) {
            throw new IllegalArgumentException("Date before 2000 not allowed");
        }
        if(totalPrice < 0) {
            throw new IllegalArgumentException("Negative total price not allowed");
        }
        if(name.isBlank()) {
            throw new IllegalArgumentException("Empty name not allowed");
        }
        for(Integer i : productsIndexes) {
            increaseTimesBought(i, 1);
            decreaseProductQuantity(i, 1);
        }
        orders.add(new Order(Searcher.collectMatches(products, productsIndexes), date, name, totalPrice, orders.size()));
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
        if (quantity < 0) throw new IllegalArgumentException("Negative quantity not allowed");
        products.get(index).setAvailableQuantity(products.get(index).getAvailableQuantity() + quantity);
    }

    public void decreaseProductQuantity(int index, int quantity) {
        if (products.get(index).getAvailableQuantity() - quantity < 0) throw new IllegalArgumentException("Not enough products for this action");
        if(quantity < 0) throw new IllegalArgumentException("Negative quantity not allowed");
        products.get(index).setAvailableQuantity(products.get(index).getAvailableQuantity() - quantity);
    }

    public List<Product> searchProductByName_Equals(String query) {
        if (query.isBlank()) throw new IllegalArgumentException("Empty query not allowed");
        return Searcher.searchingString(products, query, Product::getName);
    }

    public List<Product> searchProductByName_Contains(String query) {
        if (query.isBlank()) throw new IllegalArgumentException("Empty query not allowed");
        return Searcher.searchingByCondition(products, Product::getName, (s, s2) -> s.toLowerCase().contains(s2.toLowerCase()), query);
    }

    public List<Product> searchProductByCategory(String query) {
        return Searcher.searchingString(products, query, Product::getCategory);
    }

    public List<Product> searchProductByPrice(double query) {
        if(query < 0) throw new IllegalArgumentException("Negative price not allowed");
        if (query > 10000) throw new IllegalArgumentException("Price too high");
        return Searcher.searchingDouble(products, query, Product::getPrice);
    }

    public List<Product> searchProductByTimesBought(int query) {
        if(query < 0) throw new IllegalArgumentException("Negative times bought not allowed");
        return Searcher.searchingInt(products, query, Product::getTimesBought);
    }

    public List<Product> searchProductByPriceRange(double min, double max) {
        if(min < 0) throw new IllegalArgumentException("Negative price not allowed");
        if (max > 10000) throw new IllegalArgumentException("Price too high");
        if (min > max) {
            if(min > 10000)
                throw new IllegalArgumentException("Min price cannot be higher than max price and price cannot be higher than 10000");
            if(max < 0)
                throw new IllegalArgumentException("Max price cannot be lower than min price and price cannot be lower than 0");
            else
                throw new IllegalArgumentException("Min price cannot be higher than max price");
        }
        if (min == max) return searchProductByPrice(min);
        return Searcher.searchingByRange(products, min, max, Product::getPrice);
    }

    public List<Order> searchOrderByName(String query) {
        if (query.isBlank()) throw new IllegalArgumentException("Empty name not allowed");
        return Searcher.searchingString(orders, query, Order::name);
    }

    public List<Order> searchOrderByTotalPrice(double query) {
        if(query < 0) throw new IllegalArgumentException("Negative total price not allowed");
        return Searcher.searchingDouble(orders, query, Order::totalPrice);
    }

    public List<Order> searchOrderByDate(LocalDate query) {
        if(query.isAfter(LocalDate.now())) throw new IllegalArgumentException("Future date not allowed");
        if(query.isBefore(LocalDate.of(2000, 1, 1))) throw new IllegalArgumentException("Date before 2000 not allowed");
        return Searcher.searchingDate(orders, query, Order::date);
    }


}
