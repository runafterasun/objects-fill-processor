package ru.objectsfill.object_param;

import ru.objectsfill.annotation_processor.exceptions.FillException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Configuration object that holds all parameters for populating a POJO with random data.
 * Use the {@link FillBuilder} (via {@link #object(Class)} or {@link #object(Object)}) to construct instances.
 *
 * <p>Example:
 * <pre>{@code
 * Fill fill = Fill.object(MyClass.class)
 *     .collectionSize(10)
 *     .valueLength(15)
 *     .setDeep(5)
 *     .excludeField("id", "createdAt")
 *     .gen();
 * }</pre>
 */
public final class Fill {

    /** The object instance to be filled with random data. */
    private Object objectz;

    /** The class of the object to be created and filled. */
    private Class<?> clazz;

    /** Field names excluded from random value generation. */
    private List<String> excludedFieldName;

    /** Maximum recursion depth for nested object generation. */
    private Integer deep;

    /** Number of elements to generate for collections and arrays. */
    private Integer collectionSize;

    /** Length of randomly generated string values. */
    private Integer valueLength;

    /** Mapping of generic type parameter names to their resolved types. */
    private Map<String, Type> genericType;

    /** Per-field generation overrides (custom sizes, mutation functions, etc.). */
    private List<Extend> extendedFieldParams;

    /**
     * Constructs a new Fill configuration.
     *
     * @param objectz           the object instance to fill
     * @param clazz             the class of the object
     * @param excludedFieldName field names to exclude from generation
     * @param deep              maximum recursion depth
     * @param genericType       generic type parameter mappings
     * @param collectionSize    number of elements for collections
     * @param valueLength       length of generated string values
     * @param extendedFieldParams per-field parameter overrides
     */
    private Fill(Object objectz, Class<?> clazz, List<String> excludedFieldName,
                 Integer deep, Map<String, Type> genericType, Integer collectionSize,
                 Integer valueLength, List<Extend> extendedFieldParams) {
        this.objectz = objectz;
        this.clazz = clazz;
        this.excludedFieldName = excludedFieldName;
        this.deep = deep;
        this.genericType = genericType;
        this.collectionSize = collectionSize;
        this.valueLength = valueLength;
        this.extendedFieldParams = extendedFieldParams;
    }

    /**
     * Replaces the target object instance. Used when the original class could not be instantiated
     * via a no-arg constructor and a parameterized constructor was used instead.
     *
     * @param objectz the new object instance
     */
    public void setObjectz(Object objectz) {
        this.objectz = objectz;
    }

    /**
     * Returns the generic type parameter mappings.
     *
     * @return map of generic type names to their resolved {@link Type} instances
     */
    public Map<String, Type> getGenericType() {
        return genericType;
    }

    /**
     * Sets the maximum recursion depth for nested object generation.
     *
     * @param deep the depth limit
     */
    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    /**
     * Replaces all generic type parameter mappings.
     *
     * @param genericType the new generic type map
     */
    public void setGenericType(Map<String, Type> genericType) {
        this.genericType = genericType;
    }

    /**
     * Adds a single generic type parameter mapping. Creates the map if it does not yet exist.
     *
     * @param genericName the type parameter name (e.g. "T")
     * @param genericType the resolved type
     */
    public void setGenericType(String genericName, Type genericType) {
        if (this.genericType == null)
            this.genericType = new HashMap<>();
        this.genericType.putIfAbsent(genericName, genericType);
    }

    /**
     * Returns the class of the object being filled.
     *
     * @return the target class
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Returns the object instance being filled.
     *
     * @return the target object
     */
    public Object getObjectz() {
        return objectz;
    }

    /**
     * Returns the list of field names excluded from generation.
     *
     * @return excluded field names
     */
    public List<String> getExcludedField() {
        return excludedFieldName;
    }

    /**
     * Returns the maximum recursion depth.
     *
     * @return the depth limit
     */
    public Integer getDeep() {
        return deep;
    }

    /**
     * Returns the number of elements to generate for collections.
     *
     * @return the collection size
     */
    public Integer getCollectionSize() {
        return collectionSize;
    }

    /**
     * Returns the length of randomly generated string values.
     *
     * @return the value length
     */
    public Integer getValueLength() {
        return valueLength;
    }

    /**
     * Returns the list of per-field parameter overrides.
     *
     * @return the extended field parameters
     */
    public List<Extend> getExtendedFieldParams() {
        return extendedFieldParams;
    }

    /**
     * Starts building a {@link Fill} configuration for an existing object instance.
     *
     * @param object the object to fill
     * @return a new {@link FillBuilder}
     */
    public static FillBuilder object(Object object) {
        return new FillBuilder(object);
    }

    /**
     * Starts building a {@link Fill} configuration for a class.
     * The class will be instantiated automatically via its no-arg constructor.
     *
     * @param clazz the class to instantiate and fill
     * @return a new {@link FillBuilder}
     */
    public static FillBuilder object(Class<?> clazz) {
        return new FillBuilder(clazz);
    }

    /**
     * Fluent builder for constructing {@link Fill} configurations.
     */
    public static final class FillBuilder {

        private Object objectz;
        private Class<?> clazz;
        private List<String> excludedFieldName = new ArrayList<>();
        private Integer deep = 3;
        private Integer collectionSize = 5;
        private Integer valueLength = 5;
        private Map<String, Type> genericType = new HashMap<>();
        private List<Extend> extendedFieldParams = new ArrayList<>();

        /**
         * Creates a builder from an existing object instance.
         *
         * @param objectz the object to fill
         * @param <T>     the object type
         */
        public <T> FillBuilder(T objectz) {
            this.objectz = objectz;
            this.clazz = objectz.getClass();
        }

        /**
         * Creates a builder from a class, attempting to instantiate it via the no-arg constructor.
         * If instantiation fails (no public no-arg constructor, abstract class, etc.),
         * the object will be created later via a parameterized constructor.
         *
         * @param clazz the class to instantiate and fill
         */
        public FillBuilder(Class<?> clazz) {
            try {
                this.clazz = clazz;
                this.objectz = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
            }
        }

        /**
         * Sets the number of elements to generate for collections and arrays.
         *
         * @param collectionSize the collection size (must be &gt;= 1)
         * @return this builder
         * @throws FillException if the value is less than 1
         */
        public FillBuilder collectionSize(Integer collectionSize) {
            checkPositive(collectionSize);
            this.collectionSize = collectionSize;
            return this;
        }

        /**
         * Sets the length of randomly generated string values.
         *
         * @param valueLength the value length (must be &gt;= 1)
         * @return this builder
         * @throws FillException if the value is less than 1
         */
        public FillBuilder valueLength(Integer valueLength) {
            checkPositive(valueLength);
            this.valueLength = valueLength;
            return this;
        }

        /**
         * Adds a single per-field parameter override.
         *
         * @param parameter the field parameter override
         * @return this builder
         */
        public FillBuilder fieldParams(Extend parameter) {
            this.extendedFieldParams.add(parameter);
            return this;
        }

        /**
         * Adds multiple per-field parameter overrides.
         *
         * @param parameter the field parameter overrides
         * @return this builder
         */
        public FillBuilder fieldParams(Extend... parameter) {
            this.extendedFieldParams.addAll(Arrays.stream(parameter).toList());
            return this;
        }

        /**
         * Adds a list of per-field parameter overrides.
         *
         * @param parameter the field parameter overrides
         * @return this builder
         */
        public FillBuilder fieldParams(List<Extend> parameter) {
            this.extendedFieldParams.addAll(parameter);
            return this;
        }

        /**
         * Registers a generic type parameter mapping by name.
         *
         * @param genericName the type parameter name (e.g. "T")
         * @param genericType the resolved type
         * @return this builder
         */
        public FillBuilder withGeneric(String genericName, Type genericType) {
            this.genericType.putIfAbsent(genericName, genericType);
            return this;
        }

        /**
         * Registers multiple generic type parameter mappings.
         *
         * @param genericType map of type parameter names to resolved types
         * @return this builder
         */
        public FillBuilder withGeneric(Map<String, Type> genericType) {
            this.genericType.putAll(genericType);
            return this;
        }

        /**
         * Registers a generic type parameter mapping by name using a {@link Class}.
         *
         * @param genericName  the type parameter name (e.g. "T")
         * @param genericClass the resolved class (ignored if null)
         * @return this builder
         */
        public FillBuilder withGeneric(String genericName, Class<?> genericClass) {
            if (genericClass != null) {
                this.genericType.putIfAbsent(genericName, genericClass);
            }
            return this;
        }

        /**
         * Sets the list of field names to exclude from generation.
         *
         * @param excludedFieldName the field names to exclude
         * @return this builder
         */
        public FillBuilder excludeField(List<String> excludedFieldName) {
            this.excludedFieldName.addAll(excludedFieldName);
            return this;
        }

        /**
         * Sets the field names to exclude from generation.
         *
         * @param excludedFieldName the field names to exclude
         * @return this builder
         */
        public FillBuilder excludeField(String... excludedFieldName) {
            List<String> excludedFieldNameList = Arrays.stream(excludedFieldName).toList();
            this.excludedFieldName.addAll(excludedFieldNameList);
            return this;
        }

        /**
         * Sets the maximum recursion depth for nested object generation.
         *
         * @param deep the depth limit (must be &gt;= 1)
         * @return this builder
         * @throws FillException if the value is less than 1
         */
        public FillBuilder setDeep(Integer deep) {
            checkPositive(deep);
            this.deep = deep;
            return this;
        }

        /**
         * Builds and returns the {@link Fill} configuration.
         *
         * @return the constructed Fill object
         */
        public Fill gen() {
            return new Fill(objectz, clazz, excludedFieldName, deep, genericType, collectionSize, valueLength, extendedFieldParams);
        }

        /**
         * Validates that the given number is positive.
         *
         * @param num the number to check
         * @throws FillException if the number is less than 1
         */
        private void checkPositive(Integer num) {
            if (num < 1) {
                throw new FillException("Value cannot be less than 1");
            }
        }
    }
}
