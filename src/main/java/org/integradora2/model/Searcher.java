package org.integradora2.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.Comparator;

@FunctionalInterface
public interface Searcher<T extends Searchable<T>> {

    @NotNull
    <U, V extends Iterable<V>> V search(@NotNull List<T> searchable, U query, Function<T, U> fieldGetter);

    static <T extends Searchable<T>> List<T> searchingInt(@NotNull List<T> list, Integer value, Function<T, Integer> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, value, fieldGetter, Comparator.comparingInt(Integer::intValue));
        if(indexes.isEmpty())
            return null;
        else
            return collectMatches(list, indexes);
    }

    static <T extends Searchable<T>> List<T> searchingDouble(@NotNull List<T> list, double value, Function<T, Double> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, value, fieldGetter, Comparator.comparingDouble(Double::doubleValue));
        if(indexes.isEmpty())
            return null;
        else
            return collectMatches(list, indexes);
    }

    static <T extends Searchable<T>> List<T> searchingString(@NotNull List<T> list, String query, Function<T, String> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, query, fieldGetter, String::compareToIgnoreCase);
        if(indexes.isEmpty())
            return null;
        else
            return collectMatches(list, indexes);
    }

    static <T extends Searchable<T>> List<T> searchingDate(@NotNull List<T> list, LocalDate value, Function<T, LocalDate> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, value, fieldGetter, Comparator.naturalOrder());
        if(indexes.isEmpty())
            return null;
        else
            return collectMatches(list, indexes);
    }

    static <T extends Searchable<T>> List<T> searchingByRange(@NotNull List<T> list, double lowerBound, double upperBound, Function<T, Double> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearchInRange(list, fieldGetter, lowerBound, upperBound);
        if(indexes.isEmpty())
            return null;
        else
            return collectMatches(list, indexes);
    }

    static <T, U> List<Integer> binarySearch(List<T> list, U target, Function<T, U> keyExtractor, Comparator<? super U> comparator) {
        List<Integer> indexes = new ArrayList<>();
        int left = 0, right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            U midValue = keyExtractor.apply(list.get(mid));
            int cmp = comparator.compare(midValue, target);

            if (cmp == 0) {
                indexes.add(mid);
                int i = mid - 1;
                while (i >= left && comparator.compare(keyExtractor.apply(list.get(i)), target) == 0) {
                    indexes.add(i);
                    i--;
                }
                i = mid + 1;
                while (i <= right && comparator.compare(keyExtractor.apply(list.get(i)), target) == 0) {
                    indexes.add(i);
                    i++;
                }
                return indexes;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return indexes;
    }

    static <T> List<Integer> binarySearchInRange(List<T> list, Function<T, Double> fieldGetter, double low, double high) {
        List<Integer> result = new ArrayList<>();
        int left = 0, right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            double value = fieldGetter.apply(list.get(mid));

            if (value >= low && value <= high) {
                result.add(mid);
            }
            if (value < low) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }


    static <T> List<T> collectMatches(List<T> list, List<Integer> indexes){
        List<T> matches = new ArrayList<>();
        for (Integer index : indexes) {
            matches.add(list.get(index));
        }
        return matches;
    }
}
