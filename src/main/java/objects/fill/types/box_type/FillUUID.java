package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.objectCount;

public class FillUUID implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return UUID.randomUUID();
    }

    @Override
    public Class<?> getClazz() {
        return UUID.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, objectCount)
                .mapToObj(i -> UUID.randomUUID());
    }
}
