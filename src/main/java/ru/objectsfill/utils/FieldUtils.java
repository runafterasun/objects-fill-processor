package ru.objectsfill.utils;

import ru.objectsfill.core.RandomValueFieldSetterCallback;
import ru.objectsfill.object_param.Extend;
import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.CollectionElementCreationService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_FIELD_ARRAY;
import static ru.objectsfill.core.RandomValueFieldSetterCallback.getExtendPredicate;

/**
 * Utility class for reflective field traversal and population.
 * Provides caching of declared fields and resolution of per-field mutation functions.
 */
public class FieldUtils {

    private FieldUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);

    /**
     * Iterates over all declared fields of the target object (including superclass fields)
     * and populates each one using {@link RandomValueFieldSetterCallback}.
     * If the object is {@code null} but the class is set, attempts to create an instance
     * via the constructor with the fewest parameters.
     *
     * @param fill the Fill configuration containing the target object and generation parameters
     * @throws IllegalArgumentException if the class is null
     * @throws IllegalStateException    if a field cannot be accessed
     */
    public static void doWithFields(Fill fill) {
        RandomValueFieldSetterCallback fc = new RandomValueFieldSetterCallback(fill);
        if (fill.getObjectz() != null) {
            Class<?> targetClass = fill.getObjectz().getClass();
            do {
                Field[] fields = getDeclaredFields(targetClass);
                for (Field field : fields) {
                    try {
                        fc.doWith(field);
                    } catch (IllegalAccessException ex) {
                        throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                    }
                }
                targetClass = targetClass.getSuperclass();
            }
            while (targetClass != null && targetClass != Object.class);

        } else if(fill.getClazz() != null) {
            Optional<Constructor<?>> minConstructSize = Arrays
                    .stream(fill.getClazz().getConstructors())
                    .min(Comparator.comparingInt(Constructor::getParameterCount));

            if(minConstructSize.isPresent()) {
                fill.setObjectz(addObjectWithParamConstruct(minConstructSize.get(), fill));
                doWithFields(fill);
            }
        }
    }

    /**
     * Creates an instance of the Fill's target class using the given constructor,
     * populating constructor parameters with generated values matched by parameter name.
     *
     * @param constructor the constructor to invoke
     * @param fill        the generation parameters
     * @param <T>         the type of the created instance
     * @return the created instance, or {@code null} if instantiation fails
     */
    public static <T> T addObjectWithParamConstruct(Constructor<?> constructor, Fill fill) {
        return addObjectWithParamConstruct(constructor, fill, fill.getClazz());
    }

    /**
     * Creates an instance of the specified class using the given constructor,
     * populating constructor parameters with generated values matched by parameter name
     * against the class's declared fields.
     *
     * @param constructor the constructor to invoke
     * @param fill        the generation parameters
     * @param tClass      the class whose fields are matched to constructor parameters
     * @param <T>         the type of the created instance
     * @return the created instance, or {@code null} if instantiation fails
     */
    @SuppressWarnings("unchecked")
    public static <T> T addObjectWithParamConstruct(Constructor<?> constructor, Fill fill, Class<?> tClass) {
        Parameter[] parameters = constructor.getParameters();
        Field[] fields = getDeclaredFields(tClass);
        Object[] filledParameters = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            for (Field field : fields) {
                if(parameters[i].getName().equals(field.getName())) {
                    Object value = new CollectionElementCreationService().generateCollection(field, fill);
                    filledParameters[i] = value;
                }
            }
        }
        try {
            return (T) constructor.newInstance(filledParameters);
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * Returns the declared fields for the given class, using a cache for performance.
     *
     * @param clazz the class to introspect
     * @return an array of declared fields
     * @throws IllegalArgumentException if the class is null
     * @throws IllegalStateException    if class introspection fails
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
     * Resolves the mutation function for the Fill configuration.
     * Delegates to {@link #getObjectUnaryOperator(Fill, Field)} with a {@code null} field.
     *
     * @param fill the Fill configuration
     * @return the mutation function, or identity if none is configured
     */
    public static UnaryOperator<Object> getObjectUnaryOperator(Fill fill) {
        return getObjectUnaryOperator(fill, null);
    }

    /**
     * Resolves the mutation function for a specific field.
     * If the field is {@code null}, returns the first global {@link Extend} function
     * (one with no field name and no class). Otherwise, returns the function
     * from the matching {@link Extend} parameter for the given field.
     *
     * @param fill  the Fill configuration
     * @param field the target field, or {@code null} for global resolution
     * @return the mutation function, or identity ({@code t -> t}) if none is configured
     */
    public static UnaryOperator<Object> getObjectUnaryOperator(Fill fill, Field field) {
        UnaryOperator<Object> mutationFunction = t -> t;
        if(field == null) {
            Optional<Extend> extend = getFirstSingleParamFunction(fill);
            if (extend.isPresent() &&
                    extend.get().getFieldName() == null &&
                    extend.get().getFieldChangeFunction() != null &&
                    extend.get().getClazz() == null) {
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
     * Returns the first {@link Extend} parameter from the Fill's extended parameter list.
     *
     * @param fill the Fill configuration
     * @return the first Extend parameter, or empty if none exist
     */
    private static Optional<Extend> getFirstSingleParamFunction(Fill fill) {
        return fill.getExtendedFieldParams()
                .stream()
                .findFirst();
    }

    /**
     * Finds the {@link Extend} parameter that matches the given field
     * by name or class type using {@link RandomValueFieldSetterCallback#getExtendPredicate}.
     *
     * @param field the field to match against
     * @param fill  the Fill configuration containing extended parameters
     * @return the matching Extend, or empty if no match
     */
    public static Optional<Extend> getExtFillParam(Field field, Fill fill) {
        return fill.getExtendedFieldParams()
                .stream()
                .filter(getExtendPredicate(field))
                .findFirst();
    }

}
