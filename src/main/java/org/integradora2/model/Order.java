package org.integradora2.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public record Order(List<? extends Searchable<?>> products, LocalDate date, String name, double totalPrice) implements Searchable<Order> {}
