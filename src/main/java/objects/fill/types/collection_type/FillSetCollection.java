package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

import static objects.fill.types.collection_type.utils.FillCollections.fillCollectionStream;

public class FillSetCollection implements FillCollectionType {

    @Override
    public Object generate(Field field, FillObjectParams fillObjectParams) {
        return fillCollectionStream(field, fillObjectParams).collect(Collectors.toSet());
    }

    @Override
    public Class<?> getClazz() {
        return Set.class;
    }
}
