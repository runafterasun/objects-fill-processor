package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class FillSetCollection implements CollectionTypeFill {

    /**
     * Generates a set collection based on the provided field and fill parameters.
     * The generated collection will be in the form of a `Set` object.
     *
     * @param field the field for which the set collection is generated
     * @param fill  the `Fill` object containing the generation parameters
     * @return the generated set collection
     */
    @Override
    public Object generate(Field field, Fill fill) {
        return fillCollectionStream(field, fill).collect(Collectors.toSet());
    }

}