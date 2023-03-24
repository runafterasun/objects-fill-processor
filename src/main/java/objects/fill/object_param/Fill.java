package objects.fill.object_param;

import objects.fill.annotation_processor.exceptions.RandomValueException;
import objects.fill.core.GlobalParameters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class Fill {

    private Fill() {}

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
     * genericType тип обобщения
     */
    private Type[] genericType;

    private Fill(Object objectz, Class<?> clazz, List<String> excludedFieldName, Integer deep, Type[] genericType) {
        this.objectz = objectz;
        this.clazz = clazz;
        this.excludedFieldName = excludedFieldName;
        this.deep = deep;
        this.genericType = genericType;
    }

    public Type[] getGenericType() {
        return genericType;
    }


    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public void setGenericType(Type[] genericType) {
        this.genericType = genericType;
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

    public static FillBuilder object(Object object) {
        return new FillBuilder(object);
    }

    public static FillBuilder clazz(Class<?> clazz) {
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
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                throw new RandomValueException(ex);
            }
        }

        private final Object objectz;

        private final Class<?> clazz;

        private List<String> excludedFieldName = new ArrayList<>();

        private Integer deep = GlobalParameters.fillDeep.getValue();

        private Type[] genericType;

        public FillBuilder withGeneric(Type[] genericType) {
            this.genericType = genericType;
            return this;
        }

        public FillBuilder withGeneric(Class<?> genericClass) {
            if (genericClass != null) {
                this.genericType = new Type[]{genericClass};
            }
            return this;
        }

        public FillBuilder excludeField(List<String> excludedFieldName) {
            this.excludedFieldName = excludedFieldName;
            return this;
        }

        public FillBuilder setDeep(Integer deep) {
            this.deep = deep;
            return this;
        }

        public Fill gen() {
            return new Fill(objectz, clazz, excludedFieldName, deep, genericType);
        }

    }
}
