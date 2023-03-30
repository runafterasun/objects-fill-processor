package objects.fill.types.collection_type;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public interface CollectionTypeFill {

    Object generate(Field field, Fill fill);

    default <T> Stream<T> fillCollectionStream(Field field, Fill fill) {
        return new ElementCreationService().fillCollectionStream(field, fill);
    }

}
