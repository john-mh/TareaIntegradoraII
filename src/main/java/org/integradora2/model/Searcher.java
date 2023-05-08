package org.integradora2.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.Comparator;

/**
 *Functional interface for searching a {@code Collection} of {@link Searchable} objects using a specified search query and field getter.
 * <p> The search results are returned as a {@link List} of {@link Searchable} objects.
 *
 *@param <T> the type of the objects being searched, must implement the {@link Searchable} interface
 */
@FunctionalInterface
public interface Searcher<T extends Searchable<T>> {

    /**
     * Searches a collection of searchable objects for matches to a query based on a given field, using the provided
     * field getter function. The query and field must be of the same type and they must implement the {@link Comparable} interface.
     *
     * @param searchable  the collection of searchable objects to search through
     * @param query       the query to search for
     * @param fieldGetter the function to extract the field to search on from each searchable object
     * @param <U>         the type of the query
     * @param <V>         the type of the searchable collection
     * @param <R>         the type of the resulting list of matches
     * @return A list of matches found in the {@link Searchable} Collection
     * @see java.util.Collection
     */
    <U extends Comparable<? super U>, V extends Iterable<T>, R extends List<T>> R search(@NotNull V searchable, U query, Function<T, U> fieldGetter);

    /**
     * Searches a list of searchable objects for matches to an integer value based on a given field, using the provided
     * field getter function.
     *
     * @param list        the list of searchable objects to search through
     * @param value       the integer value to search for
     * @param fieldGetter the function to extract the integer field to search on from each searchable object
     * @param <T>         the type of the searchable objects
     * @return A list of matches found in the {@link Searchable} Collection
     * @see java.util.Collection
     */
    static <T extends Searchable<T>> List<T> searchingInt(@NotNull List<T> list, Integer value, Function<T, Integer> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, value, fieldGetter, Comparator.comparingInt(Integer::intValue));
        return collectMatches(list, indexes);
    }

    /**
     * Searches a list of searchable objects for matches to a double value based on a given field, using the provided
     * field getter function.
     *
     * @param list        the list of searchable objects to search through
     * @param value       the double value to search for
     * @param fieldGetter the function to extract the double field to search on from each searchable object
     * @param <T>         the type of the searchable objects
     * @return A list of matches found in the {@link Searchable} Collection
     * @see java.util.Collection
     */
    static <T extends Searchable<T>> List<T> searchingDouble(@NotNull List<T> list, double value, Function<T, Double> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, value, fieldGetter, Comparator.comparingDouble(Double::doubleValue));
        return collectMatches(list, indexes);
    }

    /**
     * Searches a list of searchable objects for matches to a string value based on a given field, using the provided
     * field getter function. The search is case-insensitive.
     *
     * @param list        the list of searchable objects to search through
     * @param query       the string value to search for
     * @param fieldGetter the function to extract the string field to search on from each searchable object
     * @param <T>         the type of the searchable objects
     * @return A list of matches found in the {@link Searchable} Collection
     * @see java.util.Collection
     */
    static <T extends Searchable<T>> List<T> searchingString(@NotNull List<T> list, String query, Function<T, String> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, query, fieldGetter, String::compareToIgnoreCase);
        return collectMatches(list, indexes);
    }

    /**
     * Searches for all objects in the given list that have the specified date value using binary search.
     * The list is sorted in ascending order based on the value returned by the given field getter function.
     *
     * @param list        the list of searchable objects
     * @param value       the date value to search for
     * @param fieldGetter a function that returns the date value from a searchable object
     * @param <T>         the type of the searchable objects
     * @return a list of {@link Searchable} objects that have the specified date value
     */
    static <T extends Searchable<T>> List<T> searchingDate(@NotNull List<T> list, LocalDate value, Function<T, LocalDate> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearch(list, value, fieldGetter, Comparator.naturalOrder());
        return collectMatches(list, indexes);
    }

    /**
     * Searches for all objects in the given list that have a value within the specified range using binary search.
     * The list is sorted in ascending order based on the value returned by the given field getter function.
     *
     * @param list        the list of searchable objects
     * @param lowerBound  the lower bound of the range
     * @param upperBound  the upper bound of the range
     * @param fieldGetter a function that returns the value to search for from a searchable object
     * @param <T>         the type of the searchable objects
     * @return a list of {@link Searchable} objects that have a value within the specified range
     */
    static <T extends Searchable<T>> List<T> searchingByRange(@NotNull List<T> list, double lowerBound, double upperBound, Function<T, Double> fieldGetter) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearchInRange(list, fieldGetter, lowerBound, upperBound);
        return collectMatches(list, indexes);
    }

    static <T extends Searchable<T>, U extends Comparable<? super U>> List<T> searchingByCondition(@NotNull List<T> list, Function<T, U> fieldGetter, BiPredicate<U, U> condition, U target) {
        list.sort(Comparator.comparing(fieldGetter));
        List<Integer> indexes = binarySearchByCondition(list, fieldGetter, condition, target);
        return collectMatches(list, indexes);
    }

    /**
     * Performs a binary search on the given list using the given target value and key extractor function.
     * The list is assumed to be sorted in ascending order based on the value returned by the key extractor function.
     *
     * @param list         the list to search
     * @param target       the value to search for
     * @param keyExtractor a function that returns the value to compare from a list element
     * @param comparator   a comparator for the key value type
     * @param <T>          the type of the list elements
     * @param <U>          the type of the key value
     * @return a list of indexes of the elements that match the target value
     * @see java.util.Comparator
     * @see java.util.function.Function
     */
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

    /**
     * Performs a binary search on the given list using the given field getter function to retrieve the search value.
     * The list is assumed to be sorted in ascending order based on the values returned by the field getter function.
     *
     * @param list        the list to search
     * @param fieldGetter a function that returns the search value from a list element
     * @param low         the lower bound of the range to search for
     * @param high        the upper bound of the range to search for
     * @param <T>         the type of the list elements
     * @return a list of indexes of the elements that have a value within the specified range
     * @see java.util.function.Function
     */
    static <T> List<Integer> binarySearchInRange(List<T> list, Function<T, Double> fieldGetter, double low, double high) {
        List<Integer> result = new ArrayList<>();
        int left = 0, right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            double value = fieldGetter.apply(list.get(mid));

            if (value >= low && value <= high) {
                result.add(mid);
                int i = mid - 1;
                while (i >= left && fieldGetter.apply(list.get(i)) >= low) {
                    result.add(i);
                    i--;
                }
                i = mid + 1;
                while (i <= right && fieldGetter.apply(list.get(i)) <= high) {
                    result.add(i);
                    i++;
                }
                left = i;
                right = mid - 1;
            } else if (value < low) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    /**
     * This method performs a binary search in a sorted list based on a given condition.
     *
     * @param list The list to search through.
     * @param fieldGetter A function that returns the field to use for the comparison.
     * @param condition The condition to check for.
     * @param target The target value to compare the elements of the list to.
     * @param <T> The type of the elements in the list.
     * @param <U> The type of the field to compare.
     * @return A list of indexes in the original list that contain elements that meet the condition.
     * @see java.util.function.Function
     * @see java.util.function.BiPredicate
     */
    static <T, U extends Comparable<? super U>> List<Integer> binarySearchByCondition(List<T> list, Function<T, U> fieldGetter, BiPredicate<U, U> condition, U target){
        List<Integer> result = new ArrayList<>();
        int left = 0, right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            U value = fieldGetter.apply(list.get(mid));

            if (condition.test(value, target)) {
                int i = mid - 1;
                while (i >= 0 && condition.test(fieldGetter.apply(list.get(i)), target)) {
                    result.add(i);
                    i--;
                }

                result.add(mid);
                i = mid + 1;

                while (i < list.size() && condition.test(fieldGetter.apply(list.get(i)), target)) {
                    result.add(i);
                    i++;
                }
                break;
            } else if (value.compareTo(target) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    /**
     * Collects the list elements at the specified indexes from the given list.
     *
     * @param list    the list of elements to collect from
     * @param indexes a list of indexes of the elements to collect
     * @param <T>     the type of the list elements
     * @return a list of the elements at the specified indexes
     */
    static <T> List<T> collectMatches(List<T> list, List<Integer> indexes){
        if(indexes.isEmpty())
            return null;
        else {
            List<T> matches = new ArrayList<>();
            for (Integer index : indexes) {
                matches.add(list.get(index));
            }
            return matches;
        }
    }
}
