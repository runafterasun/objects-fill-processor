package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomAlphabet;

public class StringFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return randomAlphabet(fill);
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> randomAlphabet(fill));
    }

}
