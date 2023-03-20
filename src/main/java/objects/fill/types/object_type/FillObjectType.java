package objects.fill.types.object_type;

import objects.fill.object_param.FillObjectParams;

public interface FillObjectType {

    Object generate(Class<?> fieldType, FillObjectParams fillObjectParams);

    Class<?> getClazz();
}
