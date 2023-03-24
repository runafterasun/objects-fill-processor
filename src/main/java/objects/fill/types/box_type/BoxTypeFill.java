package objects.fill.types.box_type;

import objects.fill.object_param.Fill;
import objects.fill.types.interfaces.ClazzType;
import objects.fill.types.interfaces.BoxCollectionFillExtension;

public interface BoxTypeFill extends ClazzType, BoxCollectionFillExtension {

    Object generate(Fill fill);

}
