package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.objectCount;
import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class FillInteger implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Integer.parseInt(randomNumeric(valueLength));
    }

    @Override
    public Class<?> getClazz() {
        return Integer.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Integer.parseInt(randomNumeric(valueLength)));
    }
}
