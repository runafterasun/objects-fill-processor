package objects.fill.object_param;

import objects.fill.annotation_processor.exceptions.FillException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Fill {

    private Fill(Object objectz,
                 Class<?> clazz,
                 List<String> excludedFieldName,
                 Integer deep,
                 Map<String, Type> genericType,
                 Integer collectionSize,
                 Integer valueLength) {
        this.objectz = objectz;
        this.clazz = clazz;
        this.excludedFieldName = excludedFieldName;
        this.deep = deep;
        this.genericType = genericType;
        this.collectionSize = collectionSize;
        this.valueLength = valueLength;
    }

    /**
     * object объект для наполнения.
     */
    private Object objectz;

    /**
     * clazz класс для создания и наполнения.
     */
    private Class<?> clazz;

    /**
     * excludedFieldName поля исключенные из наполнения.
     */
    private List<String> excludedFieldName = new ArrayList<>();

    /**
     * deep ограничение глубины обхода древа зависимостей или предотвращение циклической зависимости.
     */
    private Integer deep;


    /**
     * Количество объектов созданных в коллекции.
     */
    private Integer collectionSize;

    /**
     * Количество символов для случайной генерации.
     */
    private Integer valueLength;


    /**
     * genericType тип обобщения
     */
    private Map<String, Type> genericType;


    public Map<String, Type> getGenericType() {
        return genericType;
    }


    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public void setGenericType(Map<String, Type> genericType) {
        this.genericType = genericType;
    }

    public void setGenericType(String genericName, Type genericType) {
        if(this.genericType == null)
            this.genericType = new HashMap<>();
        this.genericType.putIfAbsent(genericName, genericType);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getObjectz() {
        return objectz;
    }

    public List<String> getExcludedField() {
        return excludedFieldName;
    }

    public Integer getDeep() {
        return deep;
    }

    public Integer getCollectionSize() {
        return collectionSize;
    }

    public Integer getValueLength() {
        return valueLength;
    }

    public static FillBuilder object(Object object) {
        return new FillBuilder(object);
    }

    public static FillBuilder object(Class<?> clazz) {
        return new FillBuilder(clazz);
    }

    public static final class FillBuilder {

        public <T> FillBuilder(T objectz) {
            this.objectz = objectz;
            this.clazz = objectz.getClass();
        }

        public FillBuilder(Class<?> clazz) {
            try {
                this.clazz = clazz;
                this.objectz = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {

            }
        }

        private Object objectz;

        private Class<?> clazz;

        private List<String> excludedFieldName = new ArrayList<>();

        private Integer deep = 3;

        private Integer collectionSize = 5;

        private Integer valueLength = 5;

        private  Map<String, Type> genericType = new HashMap<>();

        public FillBuilder collectionSize(Integer collectionSize) {
            checkPositive(collectionSize);
            this.collectionSize = collectionSize;
            return this;
        }

        public FillBuilder valueLength(Integer valueLength) {
            checkPositive(valueLength);
            this.valueLength = valueLength;
            return this;
        }

        public FillBuilder withGeneric(String genericName, Type genericType) {
            this.genericType.putIfAbsent(genericName, genericType);
            return this;
        }

        public FillBuilder withGeneric(Map<String, Type> genericType) {
            this.genericType = genericType;
            return this;
        }

        public FillBuilder withGeneric(String genericName, Class<?> genericClass) {
            if (genericClass != null) {
                this.genericType.putIfAbsent(genericName, genericClass);
            }
            return this;
        }

        public FillBuilder excludeField(List<String> excludedFieldName) {
            this.excludedFieldName = excludedFieldName;
            return this;
        }

        public FillBuilder setDeep(Integer deep) {
            checkPositive(deep);
            this.deep = deep;
            return this;
        }

        public Fill gen() {
            return new Fill(objectz, clazz, excludedFieldName, deep, genericType, collectionSize, valueLength);
        }

        private void checkPositive(Integer num) {
            if (num < 1) {
                throw new FillException("Значение не может быть ниже единицы");
            }
        }

    }
}
