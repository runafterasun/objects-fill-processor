package ru.objectsfill.types.primitive_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.types.box_type.BoxTypeFill;

import java.util.stream.Stream;

import static ru.objectsfill.utils.RandomGenerator.randomNum;

/**
 * Implementation of the BoxTypeFill interface for generating and filling primitive int values.
 */
public class PrimitiveInt implements BoxTypeFill {

    /**
     * Generates a random int value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a random int value
     */
    @Override
    public Object generate(Fill fill) {
        return Integer.parseInt(randomNum(fill));
    }

    /**
     * Creates a stream of int values using the generated value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a stream of int values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
