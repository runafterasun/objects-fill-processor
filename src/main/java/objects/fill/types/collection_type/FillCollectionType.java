package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.types.interfaces.ClazzType;

import java.lang.reflect.Field;

public interface FillCollectionType extends ClazzType {

    Object generate(Field field, FillObjectParams fillObjectParams);

}
