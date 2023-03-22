package objects.fill.types.box_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FillBoolean implements FillBoxType {

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
