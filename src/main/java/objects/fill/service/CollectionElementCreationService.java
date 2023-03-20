package objects.fill.service;

import objects.fill.object_param.FillObjectParams;
import objects.fill.types.collection_type.FillCollectionType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Фабрика генерации случайных коллекций. Должна проходить по всему дереву зависимостей.
 * todo Для map не сделан древовидный обход с возможностью заполнения коллекций или других листов.
 */
public class CollectionElementCreationService {
    private static List<FillCollectionType> container = new ArrayList<>();

    public static Object generateCollection(Field field, FillObjectParams fillObjectParams) {
        Class<?> type = field.getType();
        Optional<FillCollectionType> classForCollectionType = findClassForCollectionType(type);

        if(classForCollectionType.isPresent()) {
            return classForCollectionType.get().generate(field, fillObjectParams);
        } else {
            return new SingleElementCreationService().generateSingleValue(type, fillObjectParams);
        }
    }

    private static Optional<FillCollectionType> findClassForCollectionType(Class<?> fieldType) {
        return container
                .stream()
                .filter(types -> types.getClazz().isAssignableFrom(fieldType))
                .findFirst();
    }
}
