package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class DoubleFill implements BoxTypeFill {

    /**
     * Generates a random `double` value.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated `double` value
     */
    @Override
    public Object generate(Fill fill) {
        return Double.parseDouble(randomNum(fill));
    }

    /**
     * Creates a stream of `double` values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of `double` values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }
}
