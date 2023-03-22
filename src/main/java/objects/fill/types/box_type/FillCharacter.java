package objects.fill.types.box_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.*;

public class FillCharacter implements FillBoxType {
    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return randomAlphabet().charAt(0);
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> randomAlphabet().charAt(0));
    }

    @Override
    public Class<?> getClazz() {
        return Character.class;
    }
}
