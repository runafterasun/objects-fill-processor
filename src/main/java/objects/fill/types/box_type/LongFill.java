package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class LongFill implements BoxTypeFill {

    /**
     * Generates a random long value within the specified range.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated long value
     */
    @Override
    public Object generate(Fill fill) {
        return Long.parseLong(randomNum(fill));
    }

    /**
     * Creates a stream of long values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of long values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
