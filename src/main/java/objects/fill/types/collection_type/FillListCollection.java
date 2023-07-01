package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;

import java.lang.reflect.Field;


public class FillListCollection implements CollectionTypeFill {

    /**
     * Generates a list collection based on the provided field and fill parameters.
     * The generated collection will be in the form of a `List` object.
     *
     * @param field the field for which the list collection is generated
     * @param fill  the `Fill` object containing the generation parameters
     * @return the generated list collection
     */
    @Override
    public Object generate(Field field, Fill fill) {
        return fillCollectionStream(field, fill).toList();
    }

}
