package objects.fill.types.interfaces;

import java.util.stream.Stream;

public interface ObjectCollectionFillExtension {

    Stream<Object> fillStream(Class<?> collectionGenericType);

}
