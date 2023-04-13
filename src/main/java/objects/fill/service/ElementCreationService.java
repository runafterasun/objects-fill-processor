package objects.fill.service;

import objects.fill.core.RandomValueFieldSetterCallback;
import objects.fill.object_param.Fill;
import objects.fill.service.containers.DefaultBoxTypeContainer;
import objects.fill.service.containers.DefaultObjectTypeContainer;
import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.box_type.BoxTypeFill;
import objects.fill.types.object_type.ObjectTypeFill;
import objects.fill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.service.CollectionElementCreationService.getTypeClass;
import static objects.fill.utils.FieldUtils.doWithFields;

/**
 * Фабрика генерации случайных значений. Должна проходить по всему дереву зависимостей.
 */
public class ElementCreationService {

    private final Map<Class<?>, BoxTypeFill> containerBoxType = new HashMap<>();

    private final Map<Class<?>, ObjectTypeFill> containerObjectType = new HashMap<>();

    public static final String DEFAULT_LOCAL_CLASS_CREATION_PATH = "generated.fill";

    public ElementCreationService() {
        findLocalContainerForBoxType();
        new DefaultBoxTypeContainer().getContainer().forEach(containerBoxType::putIfAbsent);

        findLocalContainerForObjectType();
        new DefaultObjectTypeContainer().getContainer().forEach(containerObjectType::putIfAbsent);
    }

    public Object generateSingleValue(Class<?> fieldType, Fill fill) {

        Optional<BoxTypeFill> classForGenerationBoxType = findClassInContainer(fieldType, containerBoxType);
        if (classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().generate(fill);
        }

        Optional<ObjectTypeFill> classForGenerationObjectType = findClassInContainer(fieldType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().generate(fieldType, fill);
        }

        return createInstance(fieldType, fill);
    }

    @SuppressWarnings("unchecked")
    public <T> Stream<T> fillCollectionStream(Field field, Fill fill) {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Optional<Type> genericCollectionType = Stream.of(listType.getActualTypeArguments()).findFirst();

        if (genericCollectionType.isPresent()) {
            try {
                Class<?> type;
                try {
                    type = (Class<?>) genericCollectionType.get();
                } catch (Exception ex) {
                    type = getTypeClass(field, fill);
                }

                return (Stream<T>) generateCollectionByClassType(fill, type);

            } catch (Exception ex) {
                return Stream.empty();
            }
        }
        return Stream.empty();
    }


    private Stream<?> generateCollectionByClassType(Fill fill, Class<?> collectionGenericType) {
        Optional<BoxTypeFill> classForGenerationBoxType = findClassInContainer(collectionGenericType, containerBoxType);
        if (classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().fillStream(fill);
        }

        Optional<ObjectTypeFill> classForGenerationObjectType = findClassInContainer(collectionGenericType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().fillStream(collectionGenericType, fill);
        }

        return fillInnerStream(collectionGenericType, fill);
    }

    public static <T> Optional<T> findClassInContainer(Class<?> fieldType, Map<Class<?>, T> container) {
        Optional<Class<?>> clazzVal = container.keySet().stream().filter(types -> types.isAssignableFrom(fieldType)).findFirst();
        if(container.containsKey(fieldType)) {
            return Optional.ofNullable(container.get(fieldType));
        }
        return clazzVal.map(container::get);
    }

    private <V> Stream<V> fillInnerStream(Class<V> vClass, Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> createInstance(vClass, fill));
    }

    private void findLocalContainerForBoxType() {
        ScanningForClassUtils.scanClassImplInterface(BoxTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(BoxTypeContainerService::getContainer)
                .forEach(container -> container.forEach(containerBoxType::putIfAbsent));
    }

    private void findLocalContainerForObjectType() {
        ScanningForClassUtils.scanClassImplInterface(ObjectTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(ObjectTypeContainerService::getContainer)
                .forEach(container -> container.forEach(containerObjectType::putIfAbsent));
    }

    /**
     * @param vClass класс поле из объекта наполнения.
     * @param fill   специальный объект для передачи объекта наполнения и состояния.
     *               <p>
     *               Тут мы возвращаем null так как если объект создать нельзя или нужная глубина достигнута то в поле мы просто записываем null;
     */
    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> vClass, Fill fill) {
        try {
            T newInstance = vClass.getDeclaredConstructor().newInstance();
            Integer deep = fill.getDeep();
            if (deep > 0) {
                Fill fillNextNode = Fill.object(newInstance)
                        .excludeField(fill.getExcludedField())
                        .collectionSize(fill.getCollectionSize())
                        .valueLength(fill.getValueLength())
                        .setDeep(--deep)
                        .withGeneric(fill.getGenericType()).gen();
                fill(fillNextNode);
                return (T) fillNextNode.getObjectz();
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * @param fill Специальный объект для передачи объекта наполнения и состояния.
     */
    private static void fill(Fill fill) {
        doWithFields(fill.getObjectz().getClass(), new RandomValueFieldSetterCallback(fill));
    }
}
