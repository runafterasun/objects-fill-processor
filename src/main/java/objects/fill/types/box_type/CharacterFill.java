package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.*;

public class CharacterFill implements BoxTypeFill {
    @Override
    public Object generate(Fill fill) {
        return randomAlphabet(fill).charAt(0);
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> randomAlphabet(fill).charAt(0));
    }


}
