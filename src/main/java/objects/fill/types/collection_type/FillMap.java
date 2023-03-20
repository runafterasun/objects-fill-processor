package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import static objects.fill.core.RandomValueObjectFill.objectCount;

public class FillMap implements FillCollectionType {

    @Override
    public Object generate(Field field, FillObjectParams fillObjectParams) {
        return fillMap(field, fillObjectParams);
    }

    @Override
    public Class<?> getClazz() {
        return Map.class;
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> fillMap(Field field, FillObjectParams fillObjectParams) {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<K> mapKey = (Class<K>) listType.getActualTypeArguments()[0];
        Class<V> mapValue = (Class<V>) listType.getActualTypeArguments()[1];

        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < objectCount; i++) {
            K key = (K) new ElementCreationService().generateSingleValue(mapKey, fillObjectParams);
            V value = (V) new ElementCreationService().generateSingleValue(mapValue, fillObjectParams);
            map.put(key, value);
        }

        return map;
    }
}
