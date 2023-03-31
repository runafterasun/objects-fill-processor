package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

public class BooleanFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return Math.random() < 0.5;
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
