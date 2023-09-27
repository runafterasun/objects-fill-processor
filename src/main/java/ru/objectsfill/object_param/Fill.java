package ru.objectsfill.object_param;

import ru.objectsfill.annotation_processor.exceptions.FillException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

/**

 The Fill class is used to provide information for populating POJO (Plain Old Java Object) classes with random data.
 */
public final class Fill {

    /**
     The object to be filled with random data.
     */
    private Object objectz;

    /**
     The class of the object to be created and filled.
     */
    private Class<?> clazz;

    /**
     The names of fields excluded from filling.
     */
    private List<String> excludedFieldName;

    /**
     The depth limit for traversing dependency trees or preventing cyclic dependencies.
     */
    private Integer deep;

    /**
     The number of objects to be created in a collection.
     */
    private Integer collectionSize;

    /**
     The length of randomly generated values.
     */
    private Integer valueLength;

    /**
     The generic types used in the object.
     */
    private Map<String, Type> genericType;

    /**
     The extended field parameters.
     */
    private List<Extend> extendedFieldParams;

    /**
     Constructs a new Fill object.
     @param objectz The object to be filled.
     @param clazz The class of the object.
     @param excludedFieldName The names of excluded fields.
     @param deep The depth limit.
     @param genericType The generic types.
     @param collectionSize The collection size.
     @param valueLength The value length.
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
     set new object if old object was without simple construct
     @param objectz new created object.
     */
    public void setObjectz(Object objectz) {
        this.objectz = objectz;
    }
    /**

     Gets the generic types.
     @return The generic types.
     */
    public Map<String, Type> getGenericType() {
        return genericType;
    }
    /**

     Sets the depth limit.
     @param deep The depth limit.
     */
    public void setDeep(Integer deep) {
        this.deep = deep;
    }
    /**

     Sets the generic types.
     @param genericType The generic types.
     */
    public void setGenericType(Map<String, Type> genericType) {
        this.genericType = genericType;
    }
    /**

     Sets a specific generic type.
     @param genericName The name of the generic type.
     @param genericType The specific generic type.
     */
    public void setGenericType(String genericName, Type genericType) {
        if (this.genericType == null)
            this.genericType = new HashMap<>();
        this.genericType.putIfAbsent(genericName, genericType);
    }
    /**

     Gets the class of the object.
     @return The class of the object.
     */
    public Class<?> getClazz() {
        return clazz;
    }
    /**

     Gets the object to be filled.
     @return The object to be filled.
     */
    public Object getObjectz() {
        return objectz;
    }
    /**

     Gets the names of excluded fields.
     @return The names of excluded fields.
     */
    public List<String> getExcludedField() {
        return excludedFieldName;
    }
    /**

     Gets the depth limit.
     @return The depth limit.
     */
    public Integer getDeep() {
        return deep;
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

     Gets the extended field parameters.
     @return The extended field parameters.
     */
    public List<Extend> getExtendedFieldParams() {
        return extendedFieldParams;
    }
    /**

     Starts building a Fill object with the specified object.
     @param object The object to be filled.
     @return A FillBuilder instance.
     */
    public static FillBuilder object(Object object) {
        return new FillBuilder(object);
    }
    /**

     Starts building a Fill object with the specified class.
     @param clazz The class to be created and filled.
     @return A FillBuilder instance.
     */
    public static FillBuilder object(Class<?> clazz) {
        return new FillBuilder(clazz);
    }
    /**

     The FillBuilder class provides a fluent interface for building a Fill object.
     */
    public static final class FillBuilder {

        /**

         The object to be filled.
         */
        private Object objectz;
        /**

         The class to be created and filled.
         */
        private Class<?> clazz;
        /**

         The names of excluded fields.
         */
        private List<String> excludedFieldName = new ArrayList<>();
        /**

         The depth limit for traversing dependency trees.
         */
        private Integer deep = 3;
        /**

         The number of objects to be created in a collection.
         */
        private Integer collectionSize = 5;
        /**

         The length of randomly generated values.
         */
        private Integer valueLength = 5;
        /**

         The generic types used in the object.
         */
        private Map<String, Type> genericType = new HashMap<>();

        /**

         The extended field parameters/
         */
        private List<Extend> extendedFieldParams = new ArrayList<>();
        /**

         Constructs a new FillBuilder object with the specified object.
         @param objectz The object to be filled.
         @param <T> The class to be created and filled.
         */
        public <T> FillBuilder(T objectz) {
            this.objectz = objectz;
            this.clazz = objectz.getClass();
        }
        /**

         Constructs a new FillBuilder object with the specified class.
         @param clazz The class to be created and filled.
         */
        public FillBuilder(Class<?> clazz) {
            try {
                this.clazz = clazz;
                this.objectz = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
            }
        }
        /**

         Sets the collection size.
         @param collectionSize The collection size.
         @return The FillBuilder instance.
         */
        public FillBuilder collectionSize(Integer collectionSize) {
            checkPositive(collectionSize);
            this.collectionSize = collectionSize;
            return this;
        }
        /**
         Sets the value length.
         @param valueLength The value length.
         @return The FillBuilder instance.
         */
        public FillBuilder valueLength(Integer valueLength) {
            checkPositive(valueLength);
            this.valueLength = valueLength;
            return this;
        }

        /**

         Sets the extended field params.
         @param parameter The field params.
         @return The FillBuilder instance.
         */
        public FillBuilder fieldParams(Extend parameter) {
            this.extendedFieldParams.add(parameter);
            return this;
        }

        /**

         Sets the extended field params.
         @param parameter The field params.
         @return The FillBuilder instance.
         */
        public FillBuilder fieldParams(Extend... parameter) {
            this.extendedFieldParams.addAll(Arrays.stream(parameter).toList());
            return this;
        }

        /**

         Sets the extended field params list.
         @param parameter The field params.
         @return The FillBuilder instance.
         */
        public FillBuilder fieldParams(List<Extend> parameter) {
            this.extendedFieldParams.addAll(parameter);
            return this;
        }
        /**

         Adds a generic type to the FillBuilder.
         @param genericName The name of the generic type.
         @param genericType The generic type.
         @return The FillBuilder instance.
         */
        public FillBuilder withGeneric(String genericName, Type genericType) {
            this.genericType.putIfAbsent(genericName, genericType);
            return this;
        }
        /**

         Sets multiple generic types in the FillBuilder.
         @param genericType The map of generic types.
         @return The FillBuilder instance.
         */
        public FillBuilder withGeneric(Map<String, Type> genericType) {
            this.genericType.putAll(genericType);
            return this;
        }
        /**

         Adds a generic type to the FillBuilder using a Class object.
         @param genericName The name of the generic type.
         @param genericClass The generic class.
         @return The FillBuilder instance.
         */
        public FillBuilder withGeneric(String genericName, Class<?> genericClass) {
            if (genericClass != null) {
                this.genericType.putIfAbsent(genericName, genericClass);
            }
            return this;
        }
        /**

         Sets the excluded field names.
         @param excludedFieldName The excluded field names.
         @return The FillBuilder instance.
         */
        public FillBuilder excludeField(List<String> excludedFieldName) {
            this.excludedFieldName.addAll(excludedFieldName);
            return this;
        }

        /**
         Sets the excluded field names.
         @param excludedFieldName The excluded field names.
         @return The FillBuilder instance.
         */
        public FillBuilder excludeField(String... excludedFieldName) {
            List<String> excludedFieldNameList = Arrays.stream(excludedFieldName).toList();
            this.excludedFieldName.addAll(excludedFieldNameList);
            return this;
        }
        /**

         Sets the depth limit.
         @param deep The depth limit.
         @return The FillBuilder instance.
         */
        public FillBuilder setDeep(Integer deep) {
            checkPositive(deep);
            this.deep = deep;
            return this;
        }
        /**
         Builds and returns the Fill object.
         @return The created Fill object.
         */
        public Fill gen() {
            return new Fill(objectz, clazz, excludedFieldName, deep, genericType, collectionSize, valueLength, extendedFieldParams);
        }
        /**

         Checks if a number is positive.
         @param num The number to check.
         @throws FillException if the number is not positive.
         */
        private void checkPositive(Integer num) {
            if (num < 1) {
                throw new FillException("Value cannot be less than 1");
            }
        }
    }
}