package ru.objectsfill.service;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.containers.DefaultBoxTypeContainer;
import ru.objectsfill.service.containers.DefaultObjectTypeContainer;
import ru.objectsfill.service.interfaces.BoxTypeContainerService;
import ru.objectsfill.service.interfaces.ObjectTypeContainerService;
import ru.objectsfill.types.box_type.BoxTypeFill;
import ru.objectsfill.types.object_type.ObjectTypeFill;
import ru.objectsfill.utils.ScanningForClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ru.objectsfill.service.CollectionElementCreationService.getTypeClass;
import static ru.objectsfill.utils.FieldUtils.*;


/**
 The ElementCreationService class is responsible for generating single values or instances of classes
 based on the provided field type and Fill object.
 It maintains separate containers for BoxTypeFill and ObjectTypeFill implementations,
 which are used to generate values for primitive types and object types, respectively.
 */
public class ElementCreationService {

    private final Map<Class<?>, BoxTypeFill> containerBoxType = new HashMap<>();

    private final Map<Class<?>, ObjectTypeFill> containerObjectType = new HashMap<>();

    /**
     * DEFAULT_LOCAL_CLASS_CREATION_PATH
     */
    public static final String DEFAULT_LOCAL_CLASS_CREATION_PATH = "generated.fill";

    /**
     Constructs a new ElementCreationService and initializes the containers by searching for local container implementations
     and adding default mappings.
     */
    public ElementCreationService() {
        findLocalContainerForBoxType();
        new DefaultBoxTypeContainer().getContainer().forEach(containerBoxType::putIfAbsent);

        findLocalContainerForObjectType();
        new DefaultObjectTypeContainer().getContainer().forEach(containerObjectType::putIfAbsent);
    }

    /**
     Generates a single value based on the provided field type and Fill object.
     First, it attempts to find a BoxTypeFill implementation for the field type in the containerBoxType.
     If found, it delegates the generation to that implementation.
     If not found, it attempts to find an ObjectTypeFill implementation for the field type in the containerObjectType.
     If found, it delegates the generation to that implementation.
     If no suitable implementation is found, it creates a new instance of the field type using createInstance() method.
     @param fieldType The type of the field for which the value is being generated.
     @param fill The Fill object containing the necessary information for generation.
     @return The generated single value.
     */
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

    /**
     Fills a collection stream based on the provided field and Fill object.
     Extracts the generic collection type from the field's ParameterizedType and attempts to generate a collection stream
     based on that type using generateCollectionByClassType() method.
     @param field The field for which the collection stream is being filled.
     @param fill The Fill object containing the necessary information for generation.
     @param <T> The type of elements in the collection.
     @return A stream of collection elements.
     */

    public <T> Stream<T> fillCollectionStream(Field field, Fill fill) {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Optional<Type> genericCollectionType = Stream.of(listType.getActualTypeArguments()).findFirst();
        if (genericCollectionType.isPresent()) {
            try {
                return getStreamByType(field, fill, genericCollectionType.get());
            } catch (Exception ex) {
                return Stream.empty();
            }
        }
        return Stream.empty();
    }

    /**
     * Get stream by Type
     * @param field The field for which the collection stream is being filled.
     * @param fill The Fill object containing the necessary information for generation.
     * @param genericCollectionType generic collection Type
     * @return fill stream
     * @param <T> The type of elements in the collection.
     */
    @SuppressWarnings("unchecked")
    private <T> Stream<T> getStreamByType(Field field, Fill fill, Type genericCollectionType) {
        Class<?> type;
        try {
            type = (Class<?>) genericCollectionType;
        } catch (Exception ex) {
            type = getTypeClass(field, fill);
        }
        return (Stream<T>) generateCollectionByClassType(field, fill, type);
    }

    /**
     Generates a collection stream based on the provided class type and Fill object.
     First, it attempts to find a BoxTypeFill implementation for the collection generic type in the containerBoxType.
     If found, it returns the stream generated by that implementation.
     If not found, it attempts to find an ObjectTypeFill implementation for the collection generic type in the containerObjectType.
     If found, it returns the stream generated by that implementation.
     If no suitable implementation is found, it generates a stream of instances of the collection generic type using fillInnerStream() method.
     @param fill The Fill object containing the necessary information for generation.
     @param collectionGenericType The generic type of the collection for which the stream is being generated.
     @return A stream of collection elements.
     */
    private Stream<?> generateCollectionByClassType(Field field, Fill fill, Class<?> collectionGenericType) {

        UnaryOperator<Object> mutationFunction = getObjectUnaryOperator(fill, field);

        Optional<BoxTypeFill> classForGenerationBoxType = findClassInContainer(collectionGenericType, containerBoxType);
        if (classForGenerationBoxType.isPresent()) {
            return classForGenerationBoxType.get().fillStream(fill).map(mutationFunction);

        }

        Optional<ObjectTypeFill> classForGenerationObjectType = findClassInContainer(collectionGenericType, containerObjectType);
        if (classForGenerationObjectType.isPresent()) {
            return classForGenerationObjectType.get().fillStream(collectionGenericType, fill).map(mutationFunction);

        }

        return fillInnerStream(collectionGenericType, fill).map(mutationFunction);
    }



    /**

     Finds the value associated with the provided field type in the given container map.
     It first attempts to find an exact match for the field type in the map.
     If found, it returns the corresponding value.
     If not found, it searches for a key in the map that is assignable from the field type.
     If found, it returns the value associated with that key.
     @param fieldType The type of the field for which a value is being searched.
     @param container The container map in which to search for the value.
     @param <T> The type of value to retrieve.
     @return An optional containing the value if found, or empty if not found.
     */
    public static <T> Optional<T> findClassInContainer(Class<?> fieldType, Map<Class<?>, T> container) {
        Optional<Class<?>> clazzVal = container.keySet()
                .stream()
                .filter(types -> types.isAssignableFrom(fieldType))
                .findFirst();
        if(container.containsKey(fieldType)) {
            return Optional.ofNullable(container.get(fieldType));
        }
        return clazzVal.map(container::get);
    }
    /**

     Fills an inner stream with instances of the provided class type.
     Generates a stream of instances by creating new instances using createInstance() method.
     @param vClass The class type of elements in the inner stream.
     @param fill The Fill object containing the necessary information for generation.
     @param <V> The type of elements in the inner stream.
     @return A stream of instances of the provided class type.
     */
    private <V> Stream<V> fillInnerStream(Class<V> vClass, Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> createInstance(vClass, fill));
    }
    /**

     Searches for local container implementations of BoxTypeContainerService,
     retrieves their containers, and adds them to the containerBoxType map.
     */
    private void findLocalContainerForBoxType() {
        ScanningForClassUtils.scanClassImplInterface(BoxTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(BoxTypeContainerService::getContainer)
                .forEach(container -> container.forEach(containerBoxType::putIfAbsent));
    }
    /**

     Searches for local container implementations of ObjectTypeContainerService,
     retrieves their containers, and adds them to the containerObjectType map.
     */
    private void findLocalContainerForObjectType() {
        ScanningForClassUtils.scanClassImplInterface(ObjectTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(ObjectTypeContainerService::getContainer)
                .forEach(container -> container.forEach(containerObjectType::putIfAbsent));
    }

    /**

     Creates a new instance of the provided class type using reflection.
     The created instance is assigned a Fill object with decreased depth,
     and the fill() method is called to populate the fields of the instance.
     @param vClass The class type of the instance being created.
     @param fill The Fill object containing the necessary information for generation.
     @param <T> The type of the instance being created.
     @return The created instance, or null if an error occurred during instantiation.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> vClass, Fill fill) {
        try {
            Optional<Constructor<?>> minConstructSize = Arrays
                    .stream(vClass.getConstructors())
                    .min(Comparator.comparingInt(Constructor::getParameterCount));

            T newInstance;
            if(minConstructSize.isPresent() && minConstructSize.get().getParameters().length > 0) {
                newInstance = addObjectWithParamConstruct(minConstructSize.get(), fill, vClass);
            } else {
                newInstance = vClass.getDeclaredConstructor().newInstance();
            }
            Integer deep = fill.getDeep();
            if (deep > 0) {
                Fill fillNextNode = Fill.object(newInstance)
                        .excludeField(fill.getExcludedField())
                        .collectionSize(fill.getCollectionSize())
                        .valueLength(fill.getValueLength())
                        .fieldParams(fill.getExtendedFieldParams())
                        .setDeep(--deep)
                        .withGeneric(fill.getGenericType())
                        .gen();
                fill(fillNextNode);
                return (T) fillNextNode.getObjectz();
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     Fills the fields with the Fill object's associated object instance using reflection.
     @param fill The Fill object containing the object instance to be filled.
     */
    private static void fill(Fill fill) {
        doWithFields(fill);
    }
}
