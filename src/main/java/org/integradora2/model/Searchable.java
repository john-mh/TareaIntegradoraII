package org.integradora2.model;

/**
 * This interface doesn't need to have any methods or fields,
 * as its main purpose is to mark objects that can be searched.
 * <p> This interface is used in the {@link Searcher} functional interface.
 *
 * @param <T> the type of objects that can be searched
 */
public interface Searchable<T> {}
