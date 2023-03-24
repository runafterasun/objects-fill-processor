package objects.fill.types.object_type;

import objects.fill.object_param.Fill;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class EnumFill implements ObjectTypeFill {

    @Override
    public Object generate(Class<?> fieldType, Fill fill) {
        Object[] enumValues = fieldType.getEnumConstants();
        int randomEnumNumber = new Random().nextInt(enumValues.length);
        return enumValues[randomEnumNumber];
    }

    @Override
    public Class<?> getClazz() {
        return Enum.class;
    }


    @Override
    public Stream<Object> fillStream(Class<?> collectionGenericType, Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> {
                    Object[] enumValues = collectionGenericType.getEnumConstants();
                    int randomEnumNumber = new Random().nextInt(enumValues.length);
                    return enumValues[randomEnumNumber];
                });
    }

    @Override
    public int hashCode() {
        return getClazz().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ObjectTypeFill objectTypeFill) {
            return this.getClazz().equals(objectTypeFill.getClazz());
        }
        return false;
    }
}
