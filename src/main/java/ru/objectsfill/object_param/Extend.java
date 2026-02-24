package ru.objectsfill.object_param;

import java.util.function.UnaryOperator;

/**
 * Per-field parameter override for customizing how individual fields are populated.
 * Allows setting custom collection sizes, value lengths, and mutation functions
 * that target specific fields by name, by class type, or apply globally.
 *
 * <p>Example:
 * <pre>{@code
 * Extend.field("name").addMutationFunction(n -> "Custom").gen()
 * Extend.clazz(String.class).valueLength(20).gen()
 * Extend.wrapByFunction(obj -> transform(obj)).gen()
 * }</pre>
 */
public class Extend {

    /** Class type to match fields by their declared type. */
    private Class<?> clazz;

    /** Field name to match a specific field. */
    private String fieldName;

    /** Number of elements to generate for this field's collection. */
    private Integer collectionSize;

    /** Length of randomly generated string values for this field. */
    private Integer valueLength;

    /** Mutation function applied to the generated value before assignment. */
    private UnaryOperator<Object> singleChangeFunction;

    /**
     * Returns the class type used for field matching.
     *
     * @return the class type, or {@code null} if matching by field name
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Returns the mutation function applied to generated values.
     *
     * @return the mutation function, or {@code null} if none is set
     */
    public UnaryOperator<Object> getFieldChangeFunction() {
        return singleChangeFunction;
    }

    /**
     * Returns the field name used for matching.
     *
     * @return the field name, or {@code null} if matching by class type
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the custom collection size for this field.
     *
     * @return the collection size override, or {@code null} to use the default
     */
    public Integer getCollectionSize() {
        return collectionSize;
    }

    /**
     * Returns the custom value length for this field.
     *
     * @return the value length override, or {@code null} to use the default
     */
    public Integer getValueLength() {
        return valueLength;
    }

    /**
     * Starts building an {@link Extend} that targets a specific field by name.
     *
     * @param fieldName the name of the field to customize
     * @return a new builder
     */
    public static FillFieldParametersBuilder field(String fieldName) {
        return new FillFieldParametersBuilder(fieldName);
    }

    /**
     * Starts building an {@link Extend} that targets all fields of a specific class type.
     *
     * @param clazz the class type to match fields against
     * @return a new builder
     */
    public static FillFieldParametersBuilder clazz(Class<?> clazz) {
        return new FillFieldParametersBuilder(clazz);
    }

    /**
     * Starts building an {@link Extend} with a global mutation function
     * applied to every generated value (when no field name or class is specified).
     *
     * @param singleChangeFunction the mutation function
     * @return a new builder
     */
    public static FillFieldParametersBuilder wrapByFunction(UnaryOperator<Object> singleChangeFunction) {
        return new FillFieldParametersBuilder(singleChangeFunction);
    }

    /**
     * Constructs a new Extend with all parameters.
     *
     * @param fieldName            the target field name (or {@code null})
     * @param collectionSize       the collection size override (or {@code null})
     * @param valueLength          the value length override (or {@code null})
     * @param singleChangeFunction the mutation function (or {@code null})
     * @param clazz                the target class type (or {@code null})
     */
    public Extend(String fieldName, Integer collectionSize, Integer valueLength,
                  UnaryOperator<Object> singleChangeFunction, Class<?> clazz) {
        this.fieldName = fieldName;
        this.collectionSize = collectionSize;
        this.valueLength = valueLength;
        this.singleChangeFunction = singleChangeFunction;
        this.clazz = clazz;
    }

    /**
     * Fluent builder for constructing {@link Extend} instances.
     */
    public static final class FillFieldParametersBuilder {

        private Class<?> clazz;
        private String fieldName;
        private Integer collectionSize;
        private Integer valueLength;
        UnaryOperator<Object> singleChangeFunction;

        /**
         * Creates a builder targeting a field by name.
         *
         * @param fieldName the field name to match
         */
        public FillFieldParametersBuilder(String fieldName) {
            this.fieldName = fieldName;
        }

        /**
         * Creates a builder targeting fields by class type.
         *
         * @param clazz the class type to match
         */
        public FillFieldParametersBuilder(Class<?> clazz) {
            this.clazz = clazz;
        }

        /**
         * Creates a builder with a global mutation function.
         *
         * @param singleChangeFunction the mutation function to apply
         */
        public FillFieldParametersBuilder(UnaryOperator<Object> singleChangeFunction) {
            this.singleChangeFunction = singleChangeFunction;
        }

        /**
         * Creates an empty builder.
         */
        public FillFieldParametersBuilder() {
        }

        /**
         * Sets the collection size override for this field.
         *
         * @param collectionSize the number of collection elements
         * @return this builder
         */
        public FillFieldParametersBuilder collectionSize(Integer collectionSize) {
            this.collectionSize = collectionSize;
            return this;
        }

        /**
         * Sets the value length override for this field.
         *
         * @param valueLength the string value length
         * @return this builder
         */
        public FillFieldParametersBuilder valueLength(Integer valueLength) {
            this.valueLength = valueLength;
            return this;
        }

        /**
         * Sets the mutation function applied to the generated value before assignment.
         *
         * @param singleChangeFunction the mutation function
         * @return this builder
         */
        public FillFieldParametersBuilder addMutationFunction(UnaryOperator<Object> singleChangeFunction) {
            this.singleChangeFunction = singleChangeFunction;
            return this;
        }

        /**
         * Builds and returns the {@link Extend} instance.
         *
         * @return the constructed Extend
         */
        public Extend gen() {
            return new Extend(fieldName, collectionSize, valueLength, singleChangeFunction, clazz);
        }
    }
}
