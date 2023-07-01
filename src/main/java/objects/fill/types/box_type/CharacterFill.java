package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomAlphabet;

/**
 * The `CharacterFill` class implements the `BoxTypeFill` interface to generate `Character` values.
 */
public class CharacterFill implements BoxTypeFill {

    /**
     * Generates a random `Character` value from the alphabet.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated `Character` value
     */
    @Override
    public Object generate(Fill fill) {
        return randomAlphabet(fill).charAt(0);
    }

    /**
     * Creates a stream of `Character` values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of `Character` values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }
}
