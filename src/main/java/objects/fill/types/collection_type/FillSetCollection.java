package objects.fill.types.collection_type;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

public class FillSetCollection implements CollectionTypeFill {

    @Override
    public Object generate(Field field, FillObjectParams fillObjectParams) {
        return new ElementCreationService().fillCollectionStream(field, fillObjectParams).collect(Collectors.toSet());
    }

    @Override
    public Class<?> getClazz() {
        return Set.class;
    }

    @Override
    public int hashCode() {
        return getClazz().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CollectionTypeFill collectionTypeFill) {
            return this.getClazz().equals(collectionTypeFill.getClazz());
        }
        return false;
    }
}
