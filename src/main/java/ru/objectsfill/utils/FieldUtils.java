package ru.objectsfill.utils;

import ru.objectsfill.object_param.Extend;
import ru.objectsfill.object_param.Fill;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_FIELD_ARRAY;
/**
 * Utility class for working with fields in Java classes.
 */
public class FieldUtils {

    private FieldUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
    /**
     * Invokes the given FieldCallback for each field in the specified class and its superclasses.
     *
     * @param clazz the class to process fields for
     * @param fc    the FieldCallback to invoke for each field
     * @throws IllegalArgumentException    if the class is null
     * @throws IllegalStateException   if the FieldCallback encounters an error while processing a field
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc) {
        Class<?> targetClass = clazz;
        do {
            Field[] fields = getDeclaredFields(targetClass);
            for (Field field : fields) {
                try {
                    fc.doWith(field);
                }
                catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);
    }
    /**
     * Retrieves the declared fields of the specified class.
     * The fields are cached in the declaredFieldsCache map for performance optimization.
     *
     * @param clazz the class to retrieve declared fields from
     * @return an array of Field objects representing the declared fields of the class
     * @throws IllegalArgumentException if the class is null
     * @throws IllegalStateException    if an error occurs while introspecting the class
     */
    private static Field[] getDeclaredFields(Class<?> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("Class must not be null");
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                declaredFieldsCache.put(clazz, (result.length == 0 ? EMPTY_FIELD_ARRAY : result));
            }
            catch (Exception ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }

    /**
     * get wrap function or default t -> t. If we don't have reference to field
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return function
     */
    public static UnaryOperator<Object> getObjectUnaryOperator(Fill fill) {
        return getObjectUnaryOperator(fill, null);
    }
    /**
     * get wrap function or default t -> t. If we don't have reference to field
     *
     * @param fill the `Fill` object containing the generation parameters
     * @param field The field for which the collection stream is being filled.
     * @return function
     */
    public static UnaryOperator<Object> getObjectUnaryOperator(Fill fill, Field field) {
        UnaryOperator<Object> mutationFunction = t -> t;
        if(field == null) {
            Optional<Extend> extend = getFirstSingleParamFunction(fill);
            if (extend.isPresent() && extend.get().getFieldName() == null && extend.get().getFieldChangeFunction() != null) {
                mutationFunction = extend.get().getFieldChangeFunction();
            }
        } else {
            Optional<Extend> extFieldParam = getExtFillParam(field, fill);
            if (extFieldParam.isPresent() && extFieldParam.get().getFieldChangeFunction() != null) {
                mutationFunction = extFieldParam.get().getFieldChangeFunction();
            }
        }
        return mutationFunction;
    }

    /**
     * Get first of ext parameters
     *
     * @param fill      the `Fill` object containing the generation parameters
     * @return get any of param
     */
    private static Optional<Extend> getFirstSingleParamFunction(Fill fill) {
        return fill.getExtendedFieldParams()
                .stream()
                .findFirst();
    }

    /**
     *
     * @param field The field for which the collection stream is being filled.
     * @param fill The Fill object containing the necessary information for generation.
     * @return extended parameter for some field
     */
    public static Optional<Extend> getExtFillParam(Field field, Fill fill) {
        return fill.getExtendedFieldParams()
                .stream()
                .filter(fillFieldParameter -> fillFieldParameter.getFieldName().equals(field.getName()))
                .findFirst();
    }

}
