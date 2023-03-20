package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

public interface FillBoxType {

    Object generate(FillObjectParams fillObjectParams);

    Class<?> getClazz();
}
