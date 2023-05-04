package org.integradora2.model;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class SearchEngine {

    public <T extends Searchable<T>> List<T> searchingString(List<T> searchable, String query, Function<T, String> fieldGetter) {
        return Searcher.searchingString(searchable, query, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingInt(List<T> searchable, int value, Function<T, Integer> fieldGetter) {
        return Searcher.searchingInt(searchable, value, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingDouble(List<T> searchable, double value, Function<T, Double> fieldGetter) {
        return Searcher.searchingDouble(searchable, value, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingDate(List<T> searchable, LocalDate value, Function<T, LocalDate> fieldGetter) {
        return Searcher.searchingDate(searchable, value, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> searchingByRange(List<T> searchable, double lowerBound, double upperBound, Function<T, Double> fieldGetter) {
        return Searcher.searchingByRange(searchable, lowerBound, upperBound, fieldGetter);
    }

    public <T extends Searchable<T>> List<T> collectMatches(List<T> searchable, List<Integer> indexes) {
        return Searcher.collectMatches(searchable, indexes);
    }
}