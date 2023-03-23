package objects.fill.service;

import objects.fill.core.GlobalParameters;
import objects.fill.core.RandomValueFieldSetterCallback;
import objects.fill.object_param.FillObjectParams;
import objects.fill.service.containers.DefaultBoxTypeContainer;
import objects.fill.service.containers.DefaultObjectTypeContainer;
import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.interfaces.ClazzType;
import objects.fill.types.box_type.BoxTypeFill;
import objects.fill.types.object_type.ObjectTypeFill;
import objects.fill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.ObjectFillWithRandomValue.getFillObjectParams;
import static objects.fill.utils.FieldUtils.doWithFields;

/**
 * Фабрика генерации случайных значений. Должна проходить по всему дереву зависимостей.
 */
public class ElementCreationService {

    private final Set<BoxTypeFill> containerBoxType = new HashSet<>();

    private final Set<ObjectTypeFill> containerObjectType = new HashSet<>();

    public static final String DEFAULT_LOCAL_CLASS_CREATION_PATH = "generated.fill";

    public ElementCreationService() {
        findLocalContainerForBoxType();
        this.containerBoxType.addAll(new DefaultBoxTypeContainer().getContainer());

        findLocalContainerForObjectType();
        this.containerObjectType.addAll(new DefaultObjectTypeContainer().getContainer());
    }

    public Object generateSingleValue(Class<?> fieldType, FillObjectParams fillObjectParams) {
        Class<?> collectionGenericType = getCollectionGenericType(fieldType, fillObjectParams);

        Optional<BoxTypeFill> classForGenerationBoxType = findClassInContainer(collectionGenericType, containerBoxType);
        if (classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().generate(fillObjectParams);
        }

        Optional<ObjectTypeFill> classForGenerationObjectType = findClassInContainer(collectionGenericType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().generate(collectionGenericType, fillObjectParams);
        }

        return createInstance(collectionGenericType, fillObjectParams);
    }

    public Stream<?> fillCollectionStream(Field field, FillObjectParams fillObjectParams) {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Optional<Type> genericCollectionType = Stream.of(listType.getActualTypeArguments()).findFirst();

        if (genericCollectionType.isPresent()) {
            try {
                Class<?> collectionGenericType = getCollectionGenericType(genericCollectionType.get(), fillObjectParams);

                return generateCollectionByClassType(fillObjectParams, collectionGenericType);
            } catch (Exception ex) {
                return Stream.empty();
            }
        }
        return Stream.empty();
    }

    private Stream<?> generateCollectionByClassType(FillObjectParams fillObjectParams, Class<?> collectionGenericType) {
        Optional<BoxTypeFill> classForGenerationBoxType = findClassInContainer(collectionGenericType, containerBoxType);
        if (classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().fillStream();
        }

        Optional<ObjectTypeFill> classForGenerationObjectType = findClassInContainer(collectionGenericType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().fillStream(collectionGenericType);
        }

        return fillInnerStream(collectionGenericType, fillObjectParams);
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

    public static <T extends ClazzType> Optional<T> findClassInContainer(Class<?> fieldType, Set<T> container) {
        return container
                .stream()
                .filter(types -> types.getClazz().isAssignableFrom(fieldType))
                .findFirst();
    }

    private <V> Stream<V> fillInnerStream(Class<V> vClass, FillObjectParams fillObjectParams) {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
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

    /**
     * @param vClass           класс поле из объекта наполнения.
     * @param fillObjectParams специальный объект для передачи объекта наполнения и состояния.
     *
     * Тут мы возвращаем null так как если объект создать нельзя или нужная глубина достигнута то в поле мы просто записываем null;
     */
    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> vClass, FillObjectParams fillObjectParams) {
        try {
            T v = vClass.getDeclaredConstructor().newInstance();
            FillObjectParams fillObjectParamsNextNode = getFillObjectParams(v, fillObjectParams.getExcludedFieldName());
            Integer deep = fillObjectParams.getDeep();
            if (deep > 0) {
                fillObjectParamsNextNode.setDeep(--deep);
                fillObjectParamsNextNode.setGenericType(fillObjectParams.getGenericType());
                fill(fillObjectParamsNextNode);
                return (T) fillObjectParamsNextNode.getObject();
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * @param fillObjectParams Специальный объект для передачи объекта наполнения и состояния.
     */
    private static void fill(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
    }
}
