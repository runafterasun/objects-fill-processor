package objects.fill.types.collection_type.utils;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.SingleElementCreationService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.createInstance;
import static objects.fill.core.RandomValueObjectFill.objectCount;
import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class FillCollections {

    public static Stream<?> fillCollectionStream(Field field, FillObjectParams fillObjectParams)  {
        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> collectionGenericType = (Class<?>) listType.getActualTypeArguments()[0];

        if (collectionGenericType.isAssignableFrom(String.class)) {
            return fillStreamString();

        } else if (collectionGenericType.isAssignableFrom(Integer.class)) {
            return fillStreamInteger();

        } else if (collectionGenericType.isAssignableFrom(Long.class)) {
            return fillStreamLong();

        } else if (collectionGenericType.isAssignableFrom(Double.class)) {
            return fillStreamDouble();

        } else if (Date.class.isAssignableFrom(collectionGenericType)) {
            return fillStreamDate();

        } else if (collectionGenericType.isAssignableFrom(Boolean.class)) {
            return fillStreamBoolean();

        } else if (Enum.class.isAssignableFrom(collectionGenericType)) {
            return fillStreamEnum(collectionGenericType);
        } else {
            return fillInnerStream(collectionGenericType, fillObjectParams);

        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> fillMap(Field field, FillObjectParams fillObjectParams) {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<K> mapKey = (Class<K>) listType.getActualTypeArguments()[0];
        Class<V> mapValue = (Class<V>) listType.getActualTypeArguments()[1];

        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < objectCount; i++) {
            K key = (K) new SingleElementCreationService().generateSingleValue(mapKey, fillObjectParams);
            V value = (V) new SingleElementCreationService().generateSingleValue(mapValue, fillObjectParams);
            map.put(key, value);
        }

        return map;
    }

    private static <V> Stream<V> fillInnerStream(Class<V> vClass, FillObjectParams fillObjectParams) {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> createInstance(vClass, fillObjectParams));
    }

    private static Stream<String> fillStreamString() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> randomAlphabetic(valueLength));
    }

    private static Stream<Double> fillStreamDouble() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Double.parseDouble(randomNumeric(valueLength)));
    }

    private static Stream<Long> fillStreamLong() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Long.parseLong(randomNumeric(valueLength)));
    }

    private static Stream<Integer> fillStreamInteger() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Integer.parseInt(randomNumeric(valueLength)));
    }

    private static Stream<Boolean> fillStreamBoolean() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Math.random() < 0.5);
    }

    private static Stream<Date> fillStreamDate() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> new Date(System.currentTimeMillis()));
    }

    private static Stream<Object> fillStreamEnum(Class<?> collectionGenericType) {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> {
                    Object[] enumValues = collectionGenericType.getEnumConstants();
                    return enumValues[(int) Math.floor(Math.random() * enumValues.length)];
                });
    }
}
