package objects.fill.types.box_type;

import objects.fill.object_param.Fill;
import objects.fill.types.interfaces.BoxCollectionFillExtension;

public interface BoxTypeFill extends BoxCollectionFillExtension {

    Object generate(Fill fill);

}
