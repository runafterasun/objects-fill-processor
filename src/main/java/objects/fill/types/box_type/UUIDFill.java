package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.util.UUID;
import java.util.stream.Stream;


public class UUIDFill implements BoxTypeFill {

    /**
     * Generates a random UUID.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated UUID
     */
    @Override
    public Object generate(Fill fill) {
        return UUID.randomUUID();
    }

    /**
     * Creates a stream of UUID values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of UUID values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
