package ru.objectsfill.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Searches for and creates an object with a specific box type class.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface BoxType {

    /**
     * class information for registering the box.
     * @return the class
     */
    Class<?> clazz();
}