package ru.objectsfill.types.box_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.types.interfaces.BoxCollectionFillExtension;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface BoxTypeFill extends BoxCollectionFillExtension {

    /**
     * Generates a value of the corresponding boxed type.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated value
     */
    Object generate(Fill fill);

    /**
     * A function that creates a stream of values based on the provided `Fill` object and a value.
     */
    Function<Fill, Function<Object, Stream<Object>>> createStreamWithVal = fill ->
            val -> IntStream
                    .range(0, fill.getCollectionSize())
                    .mapToObj(i -> val);
}
