package objects.fill.core;

import objects.fill.object_param.Fill;
import objects.fill.service.ElementCreationService;

import java.util.Collection;

import static objects.fill.utils.FieldUtils.doWithFields;

/**
 * Класс предназначен для наполнения pojo классов случайными данными.
 */
public final class RandomValue {

    private RandomValue() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param fill объект содержащий всю информацию по объекту.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fill(Fill fill) {
        doWithFields(fill.getObjectz().getClass(), new RandomValueFieldSetterCallback(fill));
        return ((T) fill.getObjectz());
    }

    /**
     * @param collection   Тип коллекции для наполнения.
     * @param fill объект содержащий всю информацию по файлу.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T extends Collection<K>, K> void fillCollection(T collection, Fill fill) {
        for (int i = 0; i < GlobalParameters.objectCount.getValue(); i++) {
            K o = (K) new ElementCreationService().generateSingleValue(fill.getClazz(), fill);
            doWithFields(fill.getClazz(), new RandomValueFieldSetterCallback(fill));
            if (o.getClass().isAssignableFrom(fill.getClazz())) {
                collection.add((o));
            }
        }
    }

}
