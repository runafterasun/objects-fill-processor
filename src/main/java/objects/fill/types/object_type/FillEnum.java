package objects.fill.types.object_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;

import java.util.stream.IntStream;
import java.util.stream.Stream;


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


    @Override
    public Stream<Object> fillStream(Class<?> collectionGenericType) {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> {
                    Object[] enumValues = collectionGenericType.getEnumConstants();
                    return enumValues[(int) Math.floor(Math.random() * enumValues.length)];
                });
    }
}
