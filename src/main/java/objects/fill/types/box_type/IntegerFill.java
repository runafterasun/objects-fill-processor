package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class IntegerFill implements BoxTypeFill {

    /**
     * Generates a random integer value within the specified range.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated integer value
     */
    @Override
    public Object generate(Fill fill) {
        return Integer.parseInt(randomNum(fill));
    }

    /**
     * Creates a stream of integer values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of integer values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
