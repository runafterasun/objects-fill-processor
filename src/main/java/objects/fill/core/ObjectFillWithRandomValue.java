package objects.fill.core;

import objects.fill.annotation_processor.exceptions.RandomValueException;
import objects.fill.object_param.FillObjectParams;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static objects.fill.utils.FieldUtils.doWithFields;

/**
 * Класс предназначен для наполнения pojo классов случайными данными.
 */
public final class ObjectFillWithRandomValue {

    private ObjectFillWithRandomValue() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param object объект для заполнения тестовыми данными.
     */
    public static <T> T fill(T object) {
        return fill(object, new ArrayList<>());
    }

    /**
     * @param object            объект для заполнения тестовыми данными.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    public static <T> T fill(T object, List<String> excludedFieldName) {
        FillObjectParams fillObjectParams = getFillObjectParams(object, excludedFieldName);
        return fillTheField(fillObjectParams);
    }

    /**
     * @param object объект для заполнения тестовыми данными.
     * @param genericClass класс для обобщений
     */

    public static <T, K> T fillWithGeneric(T object, Class<K> genericClass) {
       return fillWithGeneric(object, genericClass, new ArrayList<>());
    }

    /**
     * @param object объект для заполнения тестовыми данными.
     * @param genericClass класс для обобщений
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    public static <T, K> T fillWithGeneric(T object, Class<K> genericClass, List<String> excludedFieldName) {
        FillObjectParams fillObjectParams = getFillObjectParams(object, excludedFieldName);
        if(genericClass != null) {
            fillObjectParams.setGenericType(new Type[]{genericClass});
        }
        return fillTheField(fillObjectParams);
    }

    /**
     * @param objectClass класс объекта для заполнения тестовыми данными.
     * @param genericClass класс для обобщений
     */
    public static <T, K> T fillWithGeneric(Class<T> objectClass, Class<K> genericClass) {
        return fillWithGeneric(objectClass, genericClass, new ArrayList<>());
    }

    /**
     * @param objectClass класс объекта для заполнения тестовыми данными.
     * @param genericClass класс для обобщений
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    public static <T, K> T fillWithGeneric(Class<T> objectClass, Class<K> genericClass, List<String> excludedFieldName) {
        FillObjectParams fillObjectParams = getFillObjectParams(objectClass, excludedFieldName);
        if(genericClass != null) {
            fillObjectParams.setGenericType(new Type[]{genericClass});
        }
        return fillTheField(fillObjectParams);
    }


    /**
     * @param objectClass класс объекта для заполнения тестовыми данными.
     */

    public static <T> T fill(Class<T> objectClass) {
        return fill(objectClass, new ArrayList<>());
    }

    /**
     * @param objectClass класс объекта для заполнения тестовыми данными.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    public static <T> T fill(Class<T> objectClass, List<String> excludedFieldName) {
        FillObjectParams fillObjectParams = getFillObjectParams(objectClass, excludedFieldName);
        if(fillObjectParams != null) {
            return fillTheField(fillObjectParams);
        }
        return null;
    }

    /**
     * @param collection   Тип коллекции для наполнения.
     * @param genericClass Так как пока непонятно как вытаскивать тип из generic коллекции легче его передать отдельной переменной.
     */
    public static <T extends Collection<K>, K> void fillCollection(T collection, Class<K> genericClass) {
        fillCollection(collection, genericClass, new ArrayList<>());
    }

    /**
     * @param collection   Тип коллекции для наполнения.
     * @param genericClass Так как пока непонятно как вытаскивать тип из generic коллекции легче его передать отдельной переменной.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T extends Collection<K>, K> void fillCollection(T collection, Class<K> genericClass, List<String> excludedFieldName) {
        for (int i = 0; i < GlobalParameters.objectCount.getValue(); i++) {
            K o = (K) new ElementCreationService().generateSingleValue(genericClass, getFillObjectParams(genericClass, excludedFieldName));
            doWithFields(genericClass, new RandomValueFieldSetterCallback(getFillObjectParams(o)));
            if (o.getClass().isAssignableFrom(genericClass)) {
                collection.add((o));
            }
        }
    }

    /**
     * @param fillObjectParams класс с параметрами
     */
    @SuppressWarnings("unchecked")
    private static <T> T fillTheField(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
        return ((T) fillObjectParams.getObject());
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     */
    private static <T> FillObjectParams getFillObjectParams(T object) {
        return getFillObjectParams(object, new ArrayList<>());
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     */
    private static <T> FillObjectParams getFillObjectParams(Class<T> object, List<String> excludedFieldName) {
        try {
            return getFillObjectParams(object.getDeclaredConstructor().newInstance(), excludedFieldName);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RandomValueException(ex);
        }
    }

    /**
     * @param object            создаем специальный объект для передачи объекта наполнения и состояния.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    public static <T> FillObjectParams getFillObjectParams(T object, List<String> excludedFieldName) {
        try {
            return new FillObjectParams()
                    .setObject(object.getClass().getDeclaredConstructor().newInstance())
                    .setExcludedFieldName(excludedFieldName)
                    .setDeep(GlobalParameters.fillDeep.getValue());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ex) {
            throw new RandomValueException(ex);
        }
    }


}
