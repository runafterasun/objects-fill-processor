package objects.fill.types.collection_type;

import objects.fill.annotations.CollectionType;
import objects.fill.object_param.FillObjectParams;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static objects.fill.types.collection_type.utils.FillCollections.fillCollectionStream;

@CollectionType
public class FillListCollection implements FillCollectionType {

    @Override
    public Object generate(Field field, FillObjectParams fillObjectParams) {
        return fillCollectionStream(field, fillObjectParams).collect(Collectors.toList());
    }

    @Override
    public Class<?> getClazz() {
        return List.class;
    }
}
