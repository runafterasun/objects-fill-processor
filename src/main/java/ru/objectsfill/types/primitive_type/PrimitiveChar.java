package ru.objectsfill.types.primitive_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.types.box_type.BoxTypeFill;
import ru.objectsfill.utils.RandomGenerator;

import java.util.stream.Stream;

/**
 * Implementation of the BoxTypeFill interface for generating and filling primitive char values.
 */
public class PrimitiveChar implements BoxTypeFill {

    /**
     * Generates a random char value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a random char value
     */
    @Override
    public Object generate(Fill fill) {
        return RandomGenerator.randomAlphabet(fill).charAt(0);
    }

    /**
     * Creates a stream of char values using the generated value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a stream of char values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
