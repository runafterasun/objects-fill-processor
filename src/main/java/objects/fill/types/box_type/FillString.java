package objects.fill.types.box_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.*;

public class FillString implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return randomAlphabet();
    }

    @Override
    public Class<?> getClazz() {
        return String.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> randomAlphabet());
    }
}
