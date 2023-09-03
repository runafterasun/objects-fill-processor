package ru.objectsfill.object_param;

import java.util.function.UnaryOperator;

public class Extend {

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


    public UnaryOperator<Object> getFieldChangeFunction() {
        return singleChangeFunction;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Integer getCollectionSize() {
        return collectionSize;
    }

    public Integer getValueLength() {
        return valueLength;
    }

    public static FillFieldParametersBuilder field(String fieldName) {
        return new FillFieldParametersBuilder(fieldName);
    }

    public static FillFieldParametersBuilder wrapByFunction() {
        return new FillFieldParametersBuilder();
    }

    public Extend(String fieldName, Integer collectionSize, Integer valueLength,
                  UnaryOperator<Object> singleChangeFunction) {
        this.fieldName = fieldName;
        this.collectionSize = collectionSize;
        this.valueLength = valueLength;
        this.singleChangeFunction = singleChangeFunction;
    }

    public static final class FillFieldParametersBuilder {

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

        public FillFieldParametersBuilder(String fieldName) {
            this.fieldName = fieldName;
        }

        public FillFieldParametersBuilder() {
        }


        public FillFieldParametersBuilder collectionSize(Integer collectionSize) {
            this.collectionSize = collectionSize;
            return this;
        }

        public FillFieldParametersBuilder valueLength(Integer valueLength) {
            this.valueLength = valueLength;
            return this;
        }

        public FillFieldParametersBuilder addMutationFunction(UnaryOperator<Object> singleChangeFunction) {
            this.singleChangeFunction = singleChangeFunction;
            return this;
        }

        /**
         Builds and returns the FillFieldParameters object.
         @return The created FillFieldParameters object.
         */
        public Extend gen() {
            return new Extend(fieldName, collectionSize, valueLength, singleChangeFunction);
        }
    }
}
