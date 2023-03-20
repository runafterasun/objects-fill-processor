package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;

import java.lang.reflect.Field;
import java.util.Map;

import static objects.fill.types.collection_type.utils.FillCollections.fillMap;

public class FillMap implements FillCollectionType {

    @Override
    public Object generate(Field field, FillObjectParams fillObjectParams) {
        return fillMap(field, fillObjectParams);
    }

    @Override
    public Class<?> getClazz() {
        return Map.class;
    }
}
