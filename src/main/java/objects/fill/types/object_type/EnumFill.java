package objects.fill.types.object_type;

import objects.fill.object_param.Fill;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * This class implements the ObjectTypeFill interface to provide object generation for enum types.
 * It generates random enum values based on the provided enum class.
 */
public class EnumFill implements ObjectTypeFill {

    /**
     * Generates a random enum value for the given enum type and fill parameters.
     *
     * @param fieldType the enum type
     * @param fill      the fill parameters for object generation
     * @return a random enum value
     */
    @Override
    public Object generate(Class<?> fieldType, Fill fill) {
        Object[] enumValues = fieldType.getEnumConstants();
        int randomEnumNumber = new Random().nextInt(enumValues.length);
        return enumValues[randomEnumNumber];
    }

    /**
     * Generates a stream of random enum values for the given collection generic type and fill parameters.
     *
     * @param collectionGenericType the generic type of the collection (enum type)
     * @param fill                  the fill parameters for object generation
     * @return a stream of random enum values
     */
    @Override
    public Stream<Object> fillStream(Class<?> collectionGenericType, Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> {
                    Object[] enumValues = collectionGenericType.getEnumConstants();
                    int randomEnumNumber = new Random().nextInt(enumValues.length);
                    return enumValues[randomEnumNumber];
                });
    }

}
