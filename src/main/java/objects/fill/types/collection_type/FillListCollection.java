package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.util.List;

public class FillListCollection implements CollectionTypeFill {

    @Override
    public Object generate(Field field, Fill fill) {
        return new ElementCreationService().fillCollectionStream(field, fill).toList();
    }

    @Override
    public Class<?> getClazz() {
        return List.class;
    }

}
