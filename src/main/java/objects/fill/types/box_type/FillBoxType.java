package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.types.interfaces.ClazzType;
import objects.fill.types.interfaces.BoxCollectionFillExtension;

public interface FillBoxType extends ClazzType, BoxCollectionFillExtension {

    Object generate(FillObjectParams fillObjectParams);

}
