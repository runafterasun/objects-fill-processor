package objects.fill.core;

import objects.fill.object_param.FillObjectParams;
import objects.fill.service.CollectionElementCreationService;
import objects.fill.utils.FieldCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;

/**
 * Необходимый класс для рефлексии что бы можно было обходить поля
 */
public record RandomValueFieldSetterCallback(FillObjectParams fillObjectParams) implements FieldCallback {

    /**
     * Проверка исключенных полей
     */
    private static final Function<Field, Function<FillObjectParams, Boolean>> checkExcludedName =
            checkField ->
            fillObjectParam ->
                    fillObjectParam.getExcludedFieldName().contains(checkField.getName());

    /**
     * Переопределение метода обхода полей
     */
    @Override
    public void doWith(Field field) throws IllegalAccessException {
        if (!Modifier.isFinal(field.getModifiers())) {
            if (!checkExcludedName.apply(field).apply(fillObjectParams)) {
                Object value = CollectionElementCreationService.generateCollection(field, fillObjectParams);
                if (value != null) {
                    field.setAccessible(true);
                    field.set(fillObjectParams.getObject(), value);
                }
            }
        }
    }
}
