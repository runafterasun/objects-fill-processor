package ru.objectsfill.types.collection_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;


/**
 * class MapFill
 */
public class MapFill implements CollectionTypeFill {

    /**
     * Generates a map collection based on the provided field and fill parameters.
     * The generated collection will be in the form of a `Map` object.
     *
     * @param field the field for which the map collection is generated
     * @param fill  the `Fill` object containing the generation parameters
     * @return the generated map collection
     */
    @Override
    public Object generate(Field field, Fill fill) {
        return fillMap(field, fill);
    }

    /**
     * Fills a map with generated key-value pairs based on the provided field and fill parameters.
     *
     * @param field    the field for which the map is filled
     * @param fill     the `Fill` object containing the generation parameters
     * @param <K>      the type of the map's key
     * @param <V>      the type of the map's value
     * @return the filled map
     */
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