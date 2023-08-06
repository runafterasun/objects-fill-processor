package ru.objectsfill.types.primitive_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.types.box_type.BoxTypeFill;
import ru.objectsfill.utils.RandomGenerator;

import java.util.stream.Stream;

/**
 * Implementation of the BoxTypeFill interface for generating and filling primitive double values.
 */
public class PrimitiveDouble implements BoxTypeFill {

    /**
     * Generates a random double value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a random double value
     */
    @Override
    public Object generate(Fill fill) {
        return Double.parseDouble(RandomGenerator.randomNum(fill));
    }

    /**
     * Creates a stream of double values using the generated value.
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return a stream of double values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
