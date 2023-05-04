package org.integradora2.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public record Order(List<Product> products, LocalDate date, String name, double totalPrice) implements Searchable<Order> {}
