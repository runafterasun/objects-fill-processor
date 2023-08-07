package ru.objectsfill.types.box_type;

import ru.objectsfill.object_param.Fill;

import java.util.stream.Stream;

import static ru.objectsfill.utils.RandomGenerator.randomNum;

/**
 * Class for generating a value of the double type
 */
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
