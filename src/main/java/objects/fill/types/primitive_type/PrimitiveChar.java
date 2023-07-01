package objects.fill.types.primitive_type;

import objects.fill.object_param.Fill;
import objects.fill.types.box_type.BoxTypeFill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomAlphabet;

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
        return randomAlphabet(fill).charAt(0);
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
