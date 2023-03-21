package objects.fill.service;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.containers.DefaultBoxTypeContainer;
import objects.fill.service.containers.DefaultObjectTypeContainer;
import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.interfaces.ClazzType;
import objects.fill.types.box_type.FillBoxType;
import objects.fill.types.object_type.FillObjectType;
import objects.fill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.createInstance;
import static objects.fill.core.RandomValueObjectFill.objectCount;

/**
 * Фабрика генерации случайных значений. Должна проходить по всему дереву зависимостей.
 */
public class ElementCreationService {

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
        Class<?> collectionGenericType = getCollectionGenericType(fieldType, fillObjectParams);

        Optional<FillBoxType> classForGenerationBoxType = findClassInContainer(collectionGenericType, containerBoxType);
        if (classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().generate(fillObjectParams);
        }

        Optional<FillObjectType> classForGenerationObjectType = findClassInContainer(collectionGenericType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().generate(collectionGenericType, fillObjectParams);
        }

        return createInstance(collectionGenericType, fillObjectParams);
    }

    public Stream<?> fillCollectionStream(Field field, FillObjectParams fillObjectParams) {
        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Optional<Type> genericCollectionType = Stream.of(listType.getActualTypeArguments()).findFirst();
        if (genericCollectionType.isPresent()) {
            Class<?> collectionGenericType = getCollectionGenericType(genericCollectionType.get(), fillObjectParams);

            Optional<FillBoxType> classForGenerationBoxType = findClassInContainer(collectionGenericType, containerBoxType);
            if (classForGenerationBoxType.isPresent()) {
                return classForGenerationBoxType.get().fillStream();
            }

            Optional<FillObjectType> classForGenerationObjectType = findClassInContainer(collectionGenericType, containerObjectType);
            if (classForGenerationObjectType.isPresent()) {
                return classForGenerationObjectType.get().fillStream(collectionGenericType);
            }

            return fillInnerStream(collectionGenericType, fillObjectParams);

        }
        return Stream.empty();
    }

    private Class<?> getCollectionGenericType(Type genericCollectionType, FillObjectParams fillObjectParams) {
        try {
            //Добавил такую кривую проверку так как идет извлечение типов. А все объекты являются по умолчанию наследниками java.lang.Object
            if(genericCollectionType.getTypeName().equals("java.lang.Object")) {
               return getGenericClass(fillObjectParams);
            }
            return (Class<?>) genericCollectionType;
        } catch (Exception ex) {
            return getGenericClass(fillObjectParams);
        }
    }

    private static Class<?> getGenericClass(FillObjectParams fillObjectParams) {
        return (Class<?>) Arrays
                .stream(fillObjectParams.getGenericType())
                .findFirst()
                .orElse(null);
    }

    public static <T extends ClazzType> Optional<T> findClassInContainer(Class<?> fieldType, List<T> container) {
        return container
                .stream()
                .filter(types -> types.getClazz().isAssignableFrom(fieldType))
                .findFirst();
    }

    private <V> Stream<V> fillInnerStream(Class<V> vClass, FillObjectParams fillObjectParams) {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> createInstance(vClass, fillObjectParams));
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
