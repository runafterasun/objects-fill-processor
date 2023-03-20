package objects.fill.service;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.containers.BoxTypeContainerImpl;
import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.types.box_type.FillBoxType;
import objects.fill.types.object_type.FillObjectType;
import objects.fill.utils.ScanningForClassUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static objects.fill.core.RandomValueObjectFill.createInstance;

/**
 * Фабрика генерации случайных значений. Должна проходить по всему дереву зависимостей.
 */
public class SingleElementCreationService {

    private final List<FillBoxType> containerBoxType = new ArrayList<>();
    {
        containerBoxType.addAll(new BoxTypeContainerImpl().getContainer());
        containerBoxType.addAll(ScanningForClassUtils.scanClassImplInterface(BoxTypeContainerService.class, "object.fill")
                .stream()
                .map(BoxTypeContainerService::<FillBoxType>getContainer)
                .flatMap(Collection::stream)
                .toList());
    }

    private List<FillObjectType> container = new ArrayList<>();

    public Object generateSingleValue(Class<?> fieldType, FillObjectParams fillObjectParams) {
        Optional<FillBoxType> classForGenerationBoxType = findClassForGenerationBoxType(fieldType);
        if(classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().generate(fillObjectParams);
        } else {
            Optional<FillObjectType> classForGenerationObjectType = findClassForGenerationObjectType(fieldType);
            if (classForGenerationObjectType.isPresent()) {
                return classForGenerationObjectType.get().generate(fieldType, fillObjectParams);
            } else {
                return createInstance(fieldType, fillObjectParams);
            }
        }
    }

    private Optional<FillBoxType> findClassForGenerationBoxType(Class<?> fieldType) {
        return containerBoxType
                .stream()
                .filter(types -> types.getClazz().isAssignableFrom(fieldType))
                .findFirst();
    }

    private Optional<FillObjectType> findClassForGenerationObjectType(Class<?> fieldType) {
        return container
                .stream()
                .filter(types -> types.getClazz().isAssignableFrom(fieldType))
                .findFirst();
    }
}
