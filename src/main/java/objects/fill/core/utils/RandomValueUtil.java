package objects.fill.core.utils;

import objects.fill.core.GlobalParameters;
import objects.fill.core.RandomValueFieldSetterCallback;
import objects.fill.core.exception.RandomValueException;
import objects.fill.object_param.FillObjectParams;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static objects.fill.utils.FieldUtils.doWithFields;

public class RandomValueUtil {

    private RandomValueUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param fillObjectParams Специальный объект для передачи объекта наполнения и состояния.
     */
    public static void fill(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     */
    public static <T> FillObjectParams getFillObjectParams(T object) {
        return getFillObjectParams(object, new ArrayList<>());
    }

    /**
     * @param object создаем специальный объект для передачи объекта наполнения и состояния.
     */
    public static <T> FillObjectParams getFillObjectParams(Class<T> object, List<String> excludedFieldName) {
        try {
            return getFillObjectParams(object.getDeclaredConstructor().newInstance(), excludedFieldName);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException ex) {
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



    @SuppressWarnings("unchecked")
    public static <T> T fillTheField(FillObjectParams fillObjectParams) {
        doWithFields(fillObjectParams.getObject().getClass(), new RandomValueFieldSetterCallback(fillObjectParams));
        return ((T) fillObjectParams.getObject());
    }
}
