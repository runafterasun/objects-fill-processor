package objects.fill.types.primitive_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;
import objects.fill.types.box_type.FillBoxType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class PrimitiveInt implements FillBoxType {
    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Integer.parseInt(randomNum());
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> Integer.parseInt(randomNum()));
    }

    @Override
    public Class<?> getClazz() {
        return int.class;
    }
}
