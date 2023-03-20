package objects.fill.utils;

import java.lang.reflect.Field;

@FunctionalInterface
public interface FieldCallback {

    /**
     * @param field над каким полем проводим операцию
     */
    void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
}
