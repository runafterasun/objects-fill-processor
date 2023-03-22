package objects.fill.core;

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
 * Примитивные типы не заполняет
 * todo сделать генерацию для map
 */
public class RandomValueObjectFill {

    /**
     * @param object объект для заполнения тестовыми данными.
     */
    public static <T> T fill(T object) {
        return fillWithGeneric(object, null);
    }

    /**
     * @param object объект для заполнения тестовыми данными.
     * @param genericClass класс для обобщений
     */
    public static <T, K> T fillWithGeneric(T object, Class<K> genericClass) {
        FillObjectParams fillObjectParams = getFillObjectParams(object);
        if(genericClass != null) {
            fillObjectParams.setGenericType(new Type[]{genericClass});
        }
        return fillTheField(fillObjectParams);
    }


    /**
     * @param objectClass класс объекта для заполнения тестовыми данными.
     */
    public static <T> T fill(Class<T> objectClass) {
        FillObjectParams fillObjectParams = getFillObjectParams(objectClass);
        if(fillObjectParams != null) {
            return fillTheField(fillObjectParams);
        }
        return null;
    }

    /**
     * @param object            объект для заполнения тестовыми данными.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    public static <T> T fill(T object, List<String> excludedFieldName) {
        FillObjectParams fillObjectParams = getFillObjectParams(object, excludedFieldName);
        return fillTheField(fillObjectParams);
    }

    @SuppressWarnings("unchecked")
    private static <T> T fillTheField(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
        return ((T) fillObjectParams.getObject());
    }

    /**
     * @param collection   Тип коллекции для наполнения.
     * @param genericClass Так как пока непонятно как вытаскивать тип из generic коллекции легче его передать отдельной переменной.
     *                    //todo Попытаться вытянуть за приемлемое время generic самой коллекции.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T extends Collection<K>, K> void fillCollection(T collection, Class<K> genericClass) {
        for (int i = 0; i < GlobalParameters.objectCount.getValue(); i++) {
            K o = (K) new ElementCreationService().generateSingleValue(genericClass, getFillObjectParams(genericClass));
            doWithFields(genericClass, new RandomValueFieldSetterCallback(getFillObjectParams(o)));
            if (o.getClass().isAssignableFrom(genericClass)) {
                collection.add((o));
            }
        }
    }

    /**
     * @param fillObjectParams Специальный объект для передачи объекта наполнения и состояния.
     */
    private static void fill(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
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
    private static <T> FillObjectParams getFillObjectParams(Class<T> object) {
        try {
            return getFillObjectParams(object.getDeclaredConstructor().newInstance(), new ArrayList<>());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't create instance of class");
        }
    }

    /**
     * @param object            создаем специальный объект для передачи объекта наполнения и состояния.
     * @param excludedFieldName список полей которые заполнять не надо.
     */
    private static <T> FillObjectParams getFillObjectParams(T object, List<String> excludedFieldName) {
        try {
            return new FillObjectParams()
                    .setObject(object.getClass().getDeclaredConstructor().newInstance())
                    .setExcludedFieldName(excludedFieldName)
                    .setDeep(GlobalParameters.fillDeep.getValue());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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

}
