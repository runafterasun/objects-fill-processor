package objects.fill.core;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;
import objects.fill.types.array.FillArray;

import java.util.Collection;

import static objects.fill.utils.FieldUtils.doWithFields;

/**
 The RandomValue class is designed to populate POJO classes with random data.
 */
public final class RandomValue {

    private RandomValue() {
        throw new IllegalStateException("Utility class");
    }

    /**

     Fills the given object with random values.
     @param fill The object containing all the information about the target object.
     @param <T> The type of the target object.
     @return The filled object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fill(Fill fill) {
        doWithFields(fill.getObjectz().getClass(), new RandomValueFieldSetterCallback(fill));
        return ((T) fill.getObjectz());
    }

    /**

     Fills the given collection with random values.
     @param collection The collection type for populating.
     @param fill The object containing all the information about the target collection.
     @param <T> The type of the collection.
     @param <K> The type of the elements in the collection.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T extends Collection<K>, K> void fillCollection(T collection, Fill fill) {
        for (int i = 0; i < fill.getCollectionSize(); i++) {
            K o = (K) new ElementCreationService().generateSingleValue(fill.getClazz(), fill);
            if (o.getClass().isAssignableFrom(fill.getClazz())) {
                collection.add((o));
            }
        }
    }

    /**

     Creates and fills an array with random values.
     @param fill The object containing all the information about the target array.
     @param <T> The type of the elements in the array.
     @return The filled array.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> T[] fillArray(Fill fill) {
        return (T[]) new FillArray().createArray(fill.getClazz(), fill);
    }

    /**

     Generates a single random value.
     @param fill The object containing all the information about the target value.
     @param <K> The type of the value.
     @return The generated value.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <K> K fillSingleVal(Fill fill) {
        return (K) new ElementCreationService().generateSingleValue(fill.getClazz(), fill);
    }

}
