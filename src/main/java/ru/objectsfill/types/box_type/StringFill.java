package ru.objectsfill.types.box_type;

import ru.objectsfill.object_param.Fill;

import java.util.stream.Stream;

import static ru.objectsfill.utils.RandomGenerator.randomAlphabet;

/**
 *  class StringFill
 */
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
