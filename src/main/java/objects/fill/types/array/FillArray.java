package objects.fill.types.array;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Array;

import static objects.fill.core.RandomValueObjectFill.objectCount;

public class FillArray {

    @SuppressWarnings("unchecked")
    public <T> Object[] createArray(Class<T> fieldType, FillObjectParams fillObjectParams) {
        T[] genericArray = (T[]) Array.newInstance(fieldType.getComponentType(), objectCount);
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = (T) new ElementCreationService().generateSingleValue(fieldType.getComponentType(), fillObjectParams);
        }
        return genericArray;
    }
}
