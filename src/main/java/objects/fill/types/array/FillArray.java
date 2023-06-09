package objects.fill.types.array;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Array;
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
    @SuppressWarnings("unchecked")
    public <T> Object[] createArray(Class<T> fieldType, Fill fill) {

        Class<?> componentType = fieldType.getComponentType() == null ? fieldType : fieldType.getComponentType();

        T[] genericArray = (T[]) Array.newInstance(componentType, fill.getCollectionSize());

        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = (T) new ElementCreationService().generateSingleValue(componentType, fill);
        }
        return genericArray;
    }
}
