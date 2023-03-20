package objects.fill.service;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.containers.DefaultCollectionTypeContainer;
import objects.fill.service.interfaces.CollectionTypeContainerService;
import objects.fill.types.collection_type.FillCollectionType;
import objects.fill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static objects.fill.service.ElementCreationService.DEFAULT_LOCAL_CLASS_CREATION_PATH;
import static objects.fill.service.ElementCreationService.findClassInContainer;

/**
 * Фабрика генерации случайных коллекций. Должна проходить по всему дереву зависимостей.
 * todo Для map не сделан древовидный обход с возможностью заполнения коллекций или других листов.
 */
public class CollectionElementCreationService {
    private static final List<FillCollectionType> containerCollectionType = new ArrayList<>();

    {
        containerCollectionType.addAll(new DefaultCollectionTypeContainer().getContainer());
        findLocalContainerForCollectionType();
    }

    public Object generateCollection(Field field, FillObjectParams fillObjectParams) {
        Class<?> type = field.getType();
        Optional<FillCollectionType> classForCollectionType = findClassInContainer(type, containerCollectionType);

        if(classForCollectionType.isPresent()) {
            return classForCollectionType.get().generate(field, fillObjectParams);
        } else {
            return new ElementCreationService().generateSingleValue(type, fillObjectParams);
        }
    }


    private void findLocalContainerForCollectionType() {
        containerCollectionType.addAll(ScanningForClassUtils.scanClassImplInterface(CollectionTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(CollectionTypeContainerService::getContainer)
                .flatMap(Collection::stream)
                .toList());
    }
}
