package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public interface CollectionTypeFill {

    /**
     * Generates a collection based on the provided field and fill parameters.
     *
     * @param field the field for which the collection is generated
     * @param fill  the `Fill` object containing the generation parameters
     * @return the generated collection object
     */
    Object generate(Field field, Fill fill);

    /**
     * Fills a collection stream based on the provided field and fill parameters.
     *
     * @param field the field for which the collection stream is filled
     * @param fill  the `Fill` object containing the generation parameters
     * @param <T>   the type of elements in the collection
     * @return a stream of filled collection elements
     */
    default <T> Stream<T> fillCollectionStream(Field field, Fill fill) {
        return new ElementCreationService().fillCollectionStream(field, fill);
    }

}
