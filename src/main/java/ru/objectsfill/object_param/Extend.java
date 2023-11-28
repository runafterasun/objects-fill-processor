package ru.objectsfill.object_param;

import java.util.function.UnaryOperator;

/**
 * class with parameters for field
 */
public class Extend {

    /**
     * class type for extend
     */
    private Class<?> clazz;

    /**
     * name of the field
     */
    private String fieldName;
    /**
     * The number of objects to be created in a collection.
     */
    private Integer collectionSize;
    /**
     * The length of randomly generated values.
     */
    private Integer valueLength;

    /**
     * add to single field generation function
     */
    private UnaryOperator<Object> singleChangeFunction;

    /**
     Gets class type for extend.
     @return class type for extend.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     Gets mutation function.
     @return mutation function.
     */
    public UnaryOperator<Object> getFieldChangeFunction() {
        return singleChangeFunction;
    }

    /**

     Gets the field name.
     @return field name.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**

     Gets the collection size.
     @return The collection size.
     */
    public Integer getCollectionSize() {
        return collectionSize;
    }

    /**

     Gets the value length.
     @return The value length.
     */
    public Integer getValueLength() {
        return valueLength;
    }

    /**
     start builder with name
     @param fieldName set field name
     @return fill field parameter builder.
     */
    public static FillFieldParametersBuilder field(String fieldName) {
        return new FillFieldParametersBuilder(fieldName);
    }

    /**
     start builder with name
     @param clazz set class type
     @return fill field parameter builder.
     */
    public static FillFieldParametersBuilder clazz(Class<?> clazz) {
        return new FillFieldParametersBuilder(clazz);
    }

    /**
     start function builder
     @param singleChangeFunction mutation function
     @return fill field parameter builder.
     */
    public static FillFieldParametersBuilder wrapByFunction(UnaryOperator<Object> singleChangeFunction) {
        return new FillFieldParametersBuilder(singleChangeFunction);
    }

    /**
     * constructor for builder
     * @param fieldName field name
     * @param collectionSize size
     * @param valueLength length
     * @param clazz class type for extend
     * @param singleChangeFunction mutation function
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
     *  builder for extend class
     */
    public static final class FillFieldParametersBuilder {

        /**
         * class type for extend
         */
        private Class<?> clazz;

        /**
         * name of the field
         */
        private String fieldName;

        /**
         * The number of objects to be created in a collection.
         */
        private Integer collectionSize;

        /**
         * The length of randomly generated values.
         */
        private Integer valueLength;

        /**
         * add to single field generation function
         */
        UnaryOperator<Object> singleChangeFunction;

        /**
         constructor with field name
         @param fieldName set field name
         */
        public FillFieldParametersBuilder(String fieldName) {
            this.fieldName = fieldName;
        }

        /**
         constructor with field class type
         @param clazz set field class type
         */
        public FillFieldParametersBuilder(Class<?> clazz) {
            this.clazz = clazz;
        }

        /**
         constructor with function
         @param singleChangeFunction with function
         */
        public FillFieldParametersBuilder(UnaryOperator<Object> singleChangeFunction) {
            this.singleChangeFunction = singleChangeFunction;
        }

        /**
         constructor
         */
        public FillFieldParametersBuilder() {
        }


        /**
         Sets the collection size.
         @param collectionSize The collection size.
         @return The FillBuilder instance.
         */
        public FillFieldParametersBuilder collectionSize(Integer collectionSize) {
            this.collectionSize = collectionSize;
            return this;
        }

        /**
         Sets the value length.
         @param valueLength The value length.
         @return The FillBuilder instance.
         */
        public FillFieldParametersBuilder valueLength(Integer valueLength) {
            this.valueLength = valueLength;
            return this;
        }

        /**
         Sets mutation function
         @param singleChangeFunction mutation function
         @return The FillBuilder instance.
         */
        public FillFieldParametersBuilder addMutationFunction(UnaryOperator<Object> singleChangeFunction) {
            this.singleChangeFunction = singleChangeFunction;
            return this;
        }

        /**
         Builds and returns the FillFieldParameters object.
         @return The created FillFieldParameters object.
         */
        public Extend gen() {
            return new Extend(fieldName, collectionSize, valueLength, singleChangeFunction, clazz);
        }
    }
}
