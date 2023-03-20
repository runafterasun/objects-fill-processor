package objects.fill.types.object_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.types.ClazzType;

public interface FillObjectType extends ClazzType {

    Object generate(Class<?> fieldType, FillObjectParams fillObjectParams);

}
