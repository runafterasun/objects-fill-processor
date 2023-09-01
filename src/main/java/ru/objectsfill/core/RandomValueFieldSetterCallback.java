package ru.objectsfill.core;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.CollectionElementCreationService;
import ru.objectsfill.types.primitive_type.PrimitiveArrayFill;
import ru.objectsfill.utils.FieldCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;

import static ru.objectsfill.service.CollectionElementCreationService.getTypeClass;

/**
 A necessary class for reflection to traverse fields.
 */
public record RandomValueFieldSetterCallback(Fill fill) implements FieldCallback {

/**
 Function to check excluded fields.
 */
    private static final Function<Field, Function<Fill, Boolean>> checkExcludedName =
            checkField ->
                    fillObjectParam ->
                            !fillObjectParam.getExcludedField().contains(checkField.getName());

/**
 Overrides the method for field traversal.
 @param field The field to process.
 @throws IllegalAccessException If the field is inaccessible.
 */
    @Override
    public void doWith(Field field) throws IllegalAccessException {
        Boolean checkExName = checkExcludedName.apply(field).apply(fill);
        if (!Modifier.isFinal(field.getModifiers()) && Boolean.TRUE.equals(checkExName)) {

            Class<?> type = getTypeClass(field, fill);
            Class<?> componentType = type.getComponentType() == null ? type : type.getComponentType();

            if(type.isArray() && PrimitiveArrayFill.primitiveArrayFieldNames.contains(componentType.getName())) {
                PrimitiveArrayFill.fillPrimitiveArrayField(fill, componentType.getName(), field);

            } else {
                Object value = new CollectionElementCreationService().generateCollection(field, fill);
                if (value != null) {
                    field.setAccessible(true);
                    field.set(fill.getObjectz(), value);
                }
            }
        }
    }
}
