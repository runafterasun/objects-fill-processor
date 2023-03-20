package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class FillListCollection implements FillCollectionType {

    @Override
    public Object generate(Field field, FillObjectParams fillObjectParams) {
        return new ElementCreationService().fillCollectionStream(field, fillObjectParams).collect(Collectors.toList());
    }

    @Override
    public Class<?> getClazz() {
        return List.class;
    }
}
