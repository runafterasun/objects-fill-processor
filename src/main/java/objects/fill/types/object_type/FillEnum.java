package objects.fill.types.object_type;

import objects.fill.annotations.ObjectType;
import objects.fill.object_param.FillObjectParams;

@ObjectType
public class FillEnum implements FillObjectType {

    @Override
    public Object generate(Class<?> fieldType, FillObjectParams fillObjectParams) {
        Object[] enumValues = fieldType.getEnumConstants();
        return enumValues[(int) Math.floor(Math.random() * enumValues.length)];
    }

    @Override
    public Class<?> getClazz() {
        return Enum.class;
    }
}
