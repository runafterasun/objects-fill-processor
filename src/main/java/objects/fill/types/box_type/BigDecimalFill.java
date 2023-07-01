package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;
/**
 * The `BigDecimalFill` class implements the `BoxTypeFill` interface to generate `BigDecimal` values.
 */
public class BigDecimalFill implements BoxTypeFill {
    /**
     * Generates a `BigDecimal` value based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated `BigDecimal` value
     */
    @Override
    public Object generate(Fill fill) {
        return BigDecimal.valueOf(Long.parseLong(randomNum(fill)));
    }
    /**
     * Creates a stream of `BigDecimal` values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of `BigDecimal` values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
