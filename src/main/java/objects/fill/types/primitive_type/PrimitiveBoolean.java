package objects.fill.types.primitive_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;
import objects.fill.types.box_type.FillBoxType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveBoolean implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Math.random() < 0.5;
    }

    @Override
    public Class<?> getClazz() {
        return Boolean.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> Math.random() < 0.5);
    }
}
