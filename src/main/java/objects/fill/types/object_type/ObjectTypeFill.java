package objects.fill.types.object_type;

import objects.fill.object_param.Fill;
import objects.fill.types.interfaces.ClazzType;
import objects.fill.types.interfaces.ObjectCollectionFillExtension;

public interface ObjectTypeFill extends ClazzType, ObjectCollectionFillExtension {

    Object generate(Class<?> fieldType, Fill fill);

}
