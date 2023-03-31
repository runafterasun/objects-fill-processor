package objects.fill.types.box_type;

import objects.fill.object_param.Fill;
import objects.fill.types.interfaces.BoxCollectionFillExtension;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface BoxTypeFill extends BoxCollectionFillExtension {

    Object generate(Fill fill);

    Function<Fill, Function<Object, Stream<Object>>> createStreamWithVal = fill ->
                                                                              val -> IntStream
                                                                                   .range(0, fill.getCollectionSize())
                                                                                   .mapToObj(i -> val);
}
