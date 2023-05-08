package org.integradora2.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *The FileManager class provides functionality to save and load data from a JSON file.
 *<p>The data consists of a list of Products and a list of Orders.
 */
public class FileManager {

    public static final String FILE_NAME = "MercadoLibre_DB.json";
    private static final String PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator;

    /**
     *Saves the list of products and orders to a JSON file.
     * @param products The list of products to save.
     * @param orders The list of orders to save.
     */
    public static void saveData(List<Product> products, List<Order> orders) {
        saveProducts(products);
        saveOrders(orders);
    }

    /**
     * Loads the list of products and orders from a JSON file.
     * @param products The list of products to load.
     * @param orders The list of orders to load.
     */
    public static void loadData(List<Product> products, List<Order> orders) {
        products.addAll(loadProducts());
        orders.addAll(loadOrders());
    }

    /**
     * Loads the list of products from the JSON file.
     * @return The list of products.
     */
    private static List<Product> loadProducts() {
        Path filePath = Paths.get(PATH + FILE_NAME);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            Type productListType = new TypeToken<List<Product>>() {}.getType();
            return new Gson().fromJson(reader, productListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves the list of products to the JSON file.
     * @param products The list of products to save.
     */
    private static void saveProducts(List<Product> products) {
        try (FileWriter writer = new FileWriter(PATH + FILE_NAME)) {
            new Gson().toJson(products, writer);
            System.out.println("Products data has been saved to " + FILE_NAME + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the list of orders from the JSON file.
     * @return The list of orders.
     */
    private static List<Order> loadOrders() {
        Path filePath = Paths.get(PATH + FILE_NAME);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            Type orderListType = new TypeToken<List<Order>>() {}.getType();
            return new Gson().fromJson(reader, orderListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

/**
     * Saves the list of orders to the JSON file.
     * @param orders The list of orders to save.
     */
    private static void saveOrders(List<Order> orders) {
        try (FileWriter writer = new FileWriter(PATH + FILE_NAME)) {
            new Gson().toJson(orders, writer);
            System.out.println("Orders data has been saved to " + FILE_NAME + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

