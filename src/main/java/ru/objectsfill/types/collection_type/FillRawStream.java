package ru.objectsfill.types.collection_type;

import ru.objectsfill.object_param.Fill;

import java.lang.reflect.Field;


/**
 * Generates a raw {@link java.util.stream.Stream} of elements without collecting into a concrete collection.
 */
public class FillRawStream implements CollectionTypeFill {

    /**
     * Returns a stream of randomly generated elements for the given field.
     *
     * @param field the field for which the stream is generated
     * @param fill  the generation parameters
     * @return a stream of generated elements
     */
    @Override
    public Object generate(Field field, Fill fill) {
        return fillCollectionStream(field, fill);
    }
}
