package objects.fill.types.object_type;

import objects.fill.object_param.Fill;
import objects.fill.types.interfaces.ObjectCollectionFillExtension;

public interface ObjectTypeFill extends ObjectCollectionFillExtension {

    Object generate(Class<?> fieldType, Fill fill);

}
