package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomAlphabet;

public class StringFill implements BoxTypeFill {

    /**
     * Generates a random string value consisting of alphabetic characters.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated string value
     */
    @Override
    public Object generate(Fill fill) {
        return randomAlphabet(fill);
    }

    /**
     * Creates a stream of string values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of string values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
