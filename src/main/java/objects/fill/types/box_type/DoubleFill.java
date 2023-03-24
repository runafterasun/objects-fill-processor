package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.*;

public class DoubleFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return Double.parseDouble(randomNum(fill));
    }

    @Override
    public Class<?> getClazz() {
        return Double.class;
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> Double.parseDouble(randomNum(fill)));
    }

}
