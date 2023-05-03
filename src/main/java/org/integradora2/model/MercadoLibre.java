package org.integradora2.model;

import java.util.LinkedList;
import java.util.List;

public class MercadoLibre {
    private List<Product> products;
    private List<Order> orders;

    public MercadoLibre() {
        products = new LinkedList<>();
        orders = new LinkedList<>();
    }

}
