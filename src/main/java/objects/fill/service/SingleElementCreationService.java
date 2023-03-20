package objects.fill.service;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.containers.DefaultBoxTypeContainer;
import objects.fill.service.containers.DefaultObjectTypeContainer;
import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.ClazzType;
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

    private final List<FillObjectType> containerObjectType = new ArrayList<>();

    public static final String DEFAULT_LOCAL_CLASS_CREATION_PATH = "object.fill";

    {
        this.containerBoxType.addAll(new DefaultBoxTypeContainer().getContainer());
        findLocalContainerForBoxType();

        this.containerObjectType.addAll(new DefaultObjectTypeContainer().getContainer());
        findLocalContainerForObjectType();
    }

    public Object generateSingleValue(Class<?> fieldType, FillObjectParams fillObjectParams) {
        Optional<FillBoxType> classForGenerationBoxType = findClassInContainer(fieldType, containerBoxType);
        if(classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().generate(fillObjectParams);
        }

        Optional<FillObjectType> classForGenerationObjectType = findClassInContainer(fieldType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().generate(fieldType, fillObjectParams);
        }

        return createInstance(fieldType, fillObjectParams);

    }

    public static  <T extends ClazzType> Optional<T> findClassInContainer(Class<?> fieldType, List<T> container) {
        return container
                .stream()
                .filter(types -> types.getClazz().isAssignableFrom(fieldType))
                .findFirst();
    }

    private void findLocalContainerForBoxType() {
        containerBoxType.addAll(ScanningForClassUtils.scanClassImplInterface(BoxTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(BoxTypeContainerService::getContainer)
                .flatMap(Collection::stream)
                .toList());
    }

    private void findLocalContainerForObjectType() {
        containerObjectType.addAll(ScanningForClassUtils.scanClassImplInterface(ObjectTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(ObjectTypeContainerService::getContainer)
                .flatMap(Collection::stream)
                .toList());
    }
}
