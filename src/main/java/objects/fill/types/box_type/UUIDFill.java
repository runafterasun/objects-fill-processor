package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class UUIDFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return UUID.randomUUID();
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> UUID.randomUUID());
    }

}
