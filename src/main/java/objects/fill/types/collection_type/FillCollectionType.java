package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;

import java.lang.reflect.Field;

public interface FillCollectionType {

    Object generate(Field field, FillObjectParams fillObjectParams);

    Class<?> getClazz();
}
