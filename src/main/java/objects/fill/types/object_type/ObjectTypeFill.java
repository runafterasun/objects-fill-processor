package objects.fill.types.object_type;

import objects.fill.object_param.Fill;
import objects.fill.types.interfaces.ObjectCollectionFillExtension;

/**
 * This interface provides methods for generating objects of a specific type.
 * It extends the ObjectCollectionFillExtension interface to support object generation for collections.
 */
public interface ObjectTypeFill extends ObjectCollectionFillExtension {

    /**
     * Generates an object of the specified field type with the given fill parameters.
     *
     * @param fieldType the type of the field to generate an object for
     * @param fill      the fill parameters for object generation
     * @return the generated object
     */
    Object generate(Class<?> fieldType, Fill fill);

}