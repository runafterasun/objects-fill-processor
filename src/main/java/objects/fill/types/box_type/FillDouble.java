package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.objectCount;
import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class FillDouble implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Double.parseDouble(randomNumeric(valueLength));
    }

    @Override
    public Class<?> getClazz() {
        return Double.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> Double.parseDouble(randomNumeric(valueLength)));
    }
}
