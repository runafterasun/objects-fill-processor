package objects.fill.core;

import objects.fill.object_param.Fill;
import objects.fill.service.CollectionElementCreationService;
import objects.fill.utils.FieldCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;

/**
 * Необходимый класс для рефлексии что бы можно было обходить поля
 */
public record RandomValueFieldSetterCallback(Fill fill) implements FieldCallback {

    /**
     * Проверка исключенных полей
     */
    private static final Function<Field, Function<Fill, Boolean>> checkExcludedName =
            checkField ->
                    fillObjectParam ->
                            !fillObjectParam.getExcludedField().contains(checkField.getName());

    /**
     * Переопределение метода обхода полей
     */
    @Override
    public void doWith(Field field) throws IllegalAccessException {
        Boolean checkExName = checkExcludedName.apply(field).apply(fill);
        if (!Modifier.isFinal(field.getModifiers()) && Boolean.TRUE.equals(checkExName)) {
                Object value = new CollectionElementCreationService().generateCollection(field, fill);
                if (value != null) {
                    field.setAccessible(true);
                    field.set(fill.getObjectz(), value);
                }

        }
    }
}
