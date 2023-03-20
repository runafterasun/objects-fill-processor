package objects.fill.types.object_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.types.interfaces.ClazzType;
import objects.fill.types.interfaces.ObjectCollectionFillExtension;

public interface FillObjectType extends ClazzType, ObjectCollectionFillExtension {

    Object generate(Class<?> fieldType, FillObjectParams fillObjectParams);

}
