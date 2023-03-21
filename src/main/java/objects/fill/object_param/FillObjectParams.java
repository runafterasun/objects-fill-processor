package objects.fill.object_param;

import java.lang.reflect.Type;
import java.util.List;

public final class FillObjectParams {

    /**
     * object объект для наполнения.
     */
    private Object object;

    /**
     * excludedFieldName поля исключенные из наполнения.
     */
    private List<String> excludedFieldName;

    /**
     * deep ограничение глубины обхода древа зависимостей или предотвращение циклической зависимости.
     */
    private Integer deep;

    private Type[] genericType;

    public Type[] getGenericType() {
        return genericType;
    }

    public void setGenericType(Type[] genericType) {
        this.genericType = genericType;
    }

    public FillObjectParams setObject(Object object) {
        this.object = object;
        return this;
    }

    public FillObjectParams setExcludedFieldName(List<String> excludedFieldName) {
        this.excludedFieldName = excludedFieldName;
        return this;
    }

    public FillObjectParams setDeep(Integer deep) {
        this.deep = deep;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public List<String> getExcludedFieldName() {
        return excludedFieldName;
    }

    public Integer getDeep() {
        return deep;
    }
}
