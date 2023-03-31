package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.UUID;
import java.util.stream.Stream;


public class UUIDFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return UUID.randomUUID();
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
