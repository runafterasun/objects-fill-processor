package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

public class FillSetCollection implements CollectionTypeFill {

    @Override
    public Object generate(Field field, Fill fill) {
        return fillCollectionStream(field, fill).collect(Collectors.toSet());
    }

    @Override
    public Class<?> getClazz() {
        return Set.class;
    }

}
