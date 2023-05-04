package org.integradora2.model;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class SearchEngine {

    public <T extends Searchable<T>> List<T> searchingString(List<T> items, String query, Function<T, String> fieldGetter) {
        return Searcher.searchingString(items, query, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingInt(List<T> items, int value, Function<T, Integer> fieldGetter) {
        return Searcher.searchingInt(items, value, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingDouble(List<T> items, double value, Function<T, Double> fieldGetter) {
        return Searcher.searchingDouble(items, value, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingDate(List<T> orders, LocalDate value, Function<T, LocalDate> fieldGetter) {
        return Searcher.searchingDate(orders, value, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> collectMatches(List<T> list, List<Integer> indexes) {
        return Searcher.collectMatches(list, indexes);
    }
}