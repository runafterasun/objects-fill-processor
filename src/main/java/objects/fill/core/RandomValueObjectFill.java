package objects.fill.core;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.SingleElementCreationService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static objects.fill.utils.FieldUtils.doWithFields;

/**
 * Класс предназначен для наполнения pojo классов случайными данными.
 * Примитивные типы не заполняет
 */
public class RandomValueObjectFill {

    /**
     * Количество объектов созданных в коллекции.
     */
    public static int objectCount = 5;

    /**
     * Количество символов для случайной генерации.
     */
    public static int valueLength = 5;

    /**
     * Глубина дерева классов.
     */
    public static int fillDeep = 3;

    /**
     * @param object объект для заполнения тестовыми данными.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fill(T object) {
        FillObjectParams fillObjectParams = getFillObjectParams(object);
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
        return ((T) fillObjectParams.getObject());
    }

    /**
     * @param object            объект для заполнения тестовыми данными.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fill(T object, List<String> excludedFieldName) {
        FillObjectParams fillObjectParams = getFillObjectParams(object, excludedFieldName);
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
        return ((T) fillObjectParams.getObject());
    }

    /**
     * @param fillObjectParams  Специальный объект для передачи объекта наполнения и состояния.
     */
    @SuppressWarnings("unchecked")
    private static <T> T fill(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
        return ((T) fillObjectParams.getObject());
    }

    /**
     * @param collection   Тип коллекции для наполнения.
     * @param genericClass Так как пока непонятно как вытаскивать тип из generic коллекции легче его передать отдельной переменной.
     *                    //todo Попытаться вытянуть за приемлемое время generic самой коллекции.
     */
    public static <T extends Collection<Object>> void fill(T collection, Class<Object> genericClass) {
        for (int i = 0; i < objectCount; i++) {
            Object o = new SingleElementCreationService().generateSingleValue(genericClass, getFillObjectParams(genericClass));
            doWithFields(genericClass, new RandomValueFieldSetterCallback(getFillObjectParams(o)));
            if (o.getClass().isAssignableFrom(genericClass)) {
                collection.add((o));
            }
        }
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     */
    private static <T> FillObjectParams getFillObjectParams(T object) {
        return getFillObjectParams(object, new ArrayList<>());
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    private static <T> FillObjectParams getFillObjectParams(T object, List<String> excludedFieldName) {
        return getFillObjectParams(object, excludedFieldName, fillDeep);
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     * @param excludedFieldName список полей которые заполнять не надо.
     * @param deep глубина дерева классов.
     */
    private static <T> FillObjectParams getFillObjectParams(T object, List<String> excludedFieldName, Integer deep) {
        try {
            return new FillObjectParams()
                    .setObject(object.getClass().getDeclaredConstructor().newInstance())
                    .setExcludedFieldName(excludedFieldName)
                    .setDeep(deep);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param vClass класс поле из объекта наполнения.
     * @param fillObjectParams специальный объект для передачи объекта наполнения и состояния.
     *
     * Тут мы возвращаем null так как если объект создать нельзя или нужная глубина достигнута то в поле мы просто записываем null;
     */
    @SuppressWarnings("unchecked")
    public static <V> V createInstance(Class<V> vClass, FillObjectParams fillObjectParams) {
        try {
            V v = vClass.getDeclaredConstructor().newInstance();
            FillObjectParams fillObjectParamsNextNode = getFillObjectParams(v, fillObjectParams.getExcludedFieldName());
            Integer deep = fillObjectParams.getDeep();
            if(deep > 0) {
                fillObjectParamsNextNode.setDeep(--deep);
                fill(fillObjectParamsNextNode);
                return (V) fillObjectParamsNextNode.getObject();
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

}
