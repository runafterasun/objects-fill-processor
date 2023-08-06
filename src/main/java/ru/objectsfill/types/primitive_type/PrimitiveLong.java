package ru.objectsfill.types.primitive_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.types.box_type.BoxTypeFill;

import java.util.stream.Stream;

import static ru.objectsfill.utils.RandomGenerator.randomNum;

/**
 * Implementation of the BoxTypeFill interface for generating and filling primitive long values.
 */
public class PrimitiveLong implements BoxTypeFill {

    /**
     * Generates a random long value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a random long value
     */
    @Override
    public Object generate(Fill fill) {
        return Long.parseLong(randomNum(fill));
    }

    /**
     * Creates a stream of long values using the generated value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a stream of long values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
