package ru.objectsfill.types.primitive_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.types.box_type.BoxTypeFill;

import java.util.stream.Stream;

/**
 * Implementation of the BoxTypeFill interface for generating and filling primitive boolean values.
 */
public class PrimitiveBoolean implements BoxTypeFill {

    /**
     * Generates a random boolean value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a random boolean value
     */
    @Override
    public Object generate(Fill fill) {
        return Math.random() < 0.5;
    }

    /**
     * Creates a stream of boolean values using the generated value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a stream of boolean values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
