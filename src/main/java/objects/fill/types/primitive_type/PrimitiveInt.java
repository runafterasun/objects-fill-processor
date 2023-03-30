package objects.fill.types.primitive_type;

import objects.fill.object_param.Fill;
import objects.fill.types.box_type.BoxTypeFill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class PrimitiveInt implements BoxTypeFill {
    @Override
    public Object generate(Fill fill) {
        return Integer.parseInt(randomNum(fill));
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> Integer.parseInt(randomNum(fill)));
    }

}
