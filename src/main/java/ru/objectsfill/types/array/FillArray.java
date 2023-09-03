package ru.objectsfill.types.array;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.ElementCreationService;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import static ru.objectsfill.types.primitive_type.PrimitiveTypeName.getByName;
import static ru.objectsfill.utils.FieldUtils.getObjectUnaryOperator;

/**
 * The `FillArray` class provides a method to create an array and fill it with generated values.
 */
public class FillArray {

    /**
     * Creates an array of the specified component type and fills it with generated values.
     *
     * @param fieldType the class representing the component type of the array
     * @param fill      the `Fill` object containing the generation parameters
     * @param <T>       the component type of the array
     * @return the created array filled with generated values
     */

    public <T> Object[] createArray(Class<T> fieldType, Fill fill) {
        return createArray(fieldType, fill, null);
    }

    /**
     * Creates an array of the specified component type and fills it with generated values.
     *
     * @param fieldType the class representing the component type of the array
     * @param fill      the `Fill` object containing the generation parameters
     * @param <T>       the component type of the array
     * @return the created array filled with generated values
     */

    public <T> Object[] createArray(Class<T> fieldType, Fill fill, Field field) {

        Class<?> componentType = fieldType.getComponentType() == null ? fieldType : fieldType.getComponentType();

        if(getByName(componentType.getName()) != null) {
            return forPrimitiveArrays(fill, componentType, field);
        } else {
            return forReferenceArrays(fill, componentType, field);
        }
    }

    /**
     * Creates an array of the specified component type and fills it with generated values.
     *
     * @param componentType the class representing the component type of the array
     * @param fill      the `Fill` object containing the generation parameters
     * @param <T>       the component type of the array
     * @return the created array filled with generated values
     */
    @SuppressWarnings("unchecked")
    private <T> T[] forReferenceArrays(Fill fill, Class<?> componentType, Field field) {
        T[] genericArray = (T[]) Array.newInstance(componentType, fill.getCollectionSize());
        for (int i = 0; i < genericArray.length; i++) {
            Object t = getObjectUnaryOperator(fill, field).apply(new ElementCreationService().generateSingleValue(componentType, fill));
            genericArray[i] = (T) t;
        }
        return genericArray;
    }

    /**
     * Creates an array of the specified component type and fills it with generated values.
     *
     * @param componentType the class representing the component type of the array
     * @param fill      the `Fill` object containing the generation parameters
     * @return the created array filled with generated values
     */
    private static Object[] forPrimitiveArrays(Fill fill, Class<?> componentType, Field field) {
        Object[] genericArray = new Object[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            Object t = getObjectUnaryOperator(fill, field).apply(new ElementCreationService().generateSingleValue(componentType, fill));
            genericArray[i] = t;
        }
        return genericArray;
    }

}
