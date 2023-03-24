package objects.fill.types.array;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Array;

public class FillArray {

    @SuppressWarnings("unchecked")
    public <T> Object[] createArray(Class<T> fieldType, Fill fill) {
        T[] genericArray = (T[]) Array.newInstance(fieldType.getComponentType(), fill.getCollectionSize());
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = (T) new ElementCreationService().generateSingleValue(fieldType.getComponentType(), fill);
        }
        return genericArray;
    }
}
