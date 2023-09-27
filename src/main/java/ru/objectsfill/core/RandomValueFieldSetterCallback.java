package ru.objectsfill.core;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.CollectionElementCreationService;
import ru.objectsfill.types.primitive_type.PrimitiveTypeName;
import ru.objectsfill.utils.FieldCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Function;

import static ru.objectsfill.service.CollectionElementCreationService.getTypeClass;
import static ru.objectsfill.types.primitive_type.PrimitiveTypeName.*;

/**
 * A necessary class for reflection to traverse fields.
 */
public record RandomValueFieldSetterCallback(Fill fill) implements FieldCallback {

    /**
     * Function to check excluded fields.
     */
    private static final Function<Field, Function<Fill, Boolean>> checkExcludedName =
            checkField ->
                    fillObjectParam ->
                            !fillObjectParam.getExcludedField().contains(checkField.getName());

    /**
     * Overrides the method for field traversal.
     *
     * @param field The field to process.
     * @throws IllegalAccessException If the field is inaccessible.
     */
    @Override
    public void doWith(Field field) throws IllegalAccessException {

        Boolean checkExName = checkExcludedName.apply(field).apply(fill);

        if (!Modifier.isFinal(field.getModifiers()) && Boolean.TRUE.equals(checkExName)) {
            Optional<Fill> extendedParameterForField = getExtendedParameterForField(field);

            if (extendedParameterForField.isPresent()) {
                generateExtendedValue(field, extendedParameterForField.get());

            } else {
                generateWithNoFillChange(field);

            }
        }
    }


    /**
     * split field fill
     *
     * @param field The field to process.
     * @throws IllegalAccessException If the field is inaccessible.
     */
    private void generateWithNoFillChange(Field field) throws IllegalAccessException {
        Class<?> type = getTypeClass(field, fill);
        Class<?> componentType = type.getComponentType() == null ? type : type.getComponentType();

        if (type.isArray() && getByName(componentType.getName()) != null) {
            field.setAccessible(true);
            PrimitiveTypeName
                    .getByName(componentType.getName())
                    .getCreatePrimitiveArray()
                    .apply(fill, field);

        } else {
            generateExtendedValue(field, fill);

        }
    }

    /**
     * split field fill
     *
     * @param field The field to process.
     * @throws IllegalAccessException If the field is inaccessible.
     */
    private void generateExtendedValue(Field field, Fill extendedParameterForField) throws IllegalAccessException {

        Object value = new CollectionElementCreationService().generateCollection(field, extendedParameterForField);
        if (value != null) {
            field.setAccessible(true);
            field.set(fill.getObjectz(), value);

        }
    }

    /**
     * split field fill
     *
     * @param field The field to process.
     */
    private Optional<Fill> getExtendedParameterForField(Field field) {

        return fill.getExtendedFieldParams()
                .stream()
                .filter(fillFieldParameter -> fillFieldParameter.getFieldName().equals(field.getName()))
                .map(extendedFieldParameter -> Fill.object(fill.getObjectz())
                        .excludeField(fill.getExcludedField())
                        .fieldParams(fill.getExtendedFieldParams())
                        .collectionSize(Optional.ofNullable(extendedFieldParameter.getCollectionSize()).orElse(fill.getCollectionSize()))
                        .valueLength(Optional.ofNullable(extendedFieldParameter.getValueLength()).orElse(fill.getCollectionSize()))
                        .setDeep(fill.getDeep())
                        .withGeneric(fill.getGenericType())
                        .gen()
                )
                .findFirst();
    }
}
