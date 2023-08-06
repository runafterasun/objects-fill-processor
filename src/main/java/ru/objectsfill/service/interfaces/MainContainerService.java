package ru.objectsfill.service.interfaces;

import java.util.Map;
/**
 * This interface provides a container for implementations of a specific type `T`.
 * Implementing classes should maintain a map that maps classes to their corresponding implementations of type `T`.
 *
 * @param <T> the type of implementations stored in the container
 */
public interface MainContainerService<T> {

    /**
     * Returns the container map containing implementations of type `T`, mapped to their corresponding classes.
     *
     * @return the container map of implementations of type `T`
     */
    Map<Class<?>, T> getContainer();
}