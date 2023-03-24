package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * */
public class MapFill implements CollectionTypeFill {

    @Override
    public Object generate(Field field, Fill fill) {
        return fillMap(field, fill);
    }

    @Override
    public Class<?> getClazz() {
        return Map.class;
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> fillMap(Field field, Fill fill) {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<K> mapKey = (Class<K>) listType.getActualTypeArguments()[0];
        Class<V> mapValue = (Class<V>) listType.getActualTypeArguments()[1];

        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < fill.getCollectionSize(); i++) {
            K key = (K) new ElementCreationService().generateSingleValue(mapKey, fill);
            V value = (V) new ElementCreationService().generateSingleValue(mapValue, fill);
            map.put(key, value);
        }

        return map;
    }

}
