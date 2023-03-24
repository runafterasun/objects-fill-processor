package objects.fill.types.interfaces;

import objects.fill.object_param.Fill;

import java.util.stream.Stream;

public interface ObjectCollectionFillExtension {

    Stream<Object> fillStream(Class<?> collectionGenericType, Fill fill);

}
