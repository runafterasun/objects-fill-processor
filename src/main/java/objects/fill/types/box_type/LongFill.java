package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class LongFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return Long.parseLong(randomNum(fill));
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
