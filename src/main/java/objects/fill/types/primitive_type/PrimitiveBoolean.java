package objects.fill.types.primitive_type;

import objects.fill.object_param.Fill;
import objects.fill.types.box_type.BoxTypeFill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveBoolean implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return Math.random() < 0.5;
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> Math.random() < 0.5);
    }

}
