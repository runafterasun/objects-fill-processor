package objects.fill.types.primitive_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.types.box_type.FillBoxType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.objectCount;
import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class PrimitiveLong implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Long.parseLong(randomNumeric(valueLength));
    }

    @Override
    public Class<?> getClazz() {
        return long.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Long.parseLong(randomNumeric(valueLength)));
    }
}