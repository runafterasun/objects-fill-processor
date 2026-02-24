package ru.objectsfill.core;

import ru.objectsfill.object_param.Extend;
import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.CollectionElementCreationService;
import ru.objectsfill.types.primitive_type.PrimitiveTypeName;
import ru.objectsfill.utils.FieldCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static ru.objectsfill.service.CollectionElementCreationService.getTypeClass;
import static ru.objectsfill.types.primitive_type.PrimitiveTypeName.*;

/**
 * Callback that populates each field of an object with a randomly generated value during reflection traversal.
 * Handles field exclusion, extended per-field parameters, and primitive array special-casing.
 */
public record RandomValueFieldSetterCallback(Fill fill) implements FieldCallback {

    /**
     * Curried function that checks whether a field is not in the excluded field list.
     */
    private static final Function<Field, Function<Fill, Boolean>> checkExcludedName =
            checkField ->
                    fillObjectParam ->
                            !fillObjectParam.getExcludedField().contains(checkField.getName());

    /**
     * Processes a single field: skips final and excluded fields, applies extended parameters if present,
     * and delegates to the appropriate generation method.
     *
     * @param field the field to populate
     * @throws IllegalAccessException if the field cannot be accessed via reflection
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
     * Generates a value for the field using the default (unmodified) {@link Fill} parameters.
     * If the field is a primitive array, delegates to {@link PrimitiveTypeName} for specialized handling.
     *
     * @param field the field to populate
     * @throws IllegalAccessException if the field cannot be accessed via reflection
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
     * Generates a value for the field using the given {@link Fill} configuration
     * and sets it on the target object via reflection.
     *
     * @param field                    the field to populate
     * @param extendedParameterForField the {@link Fill} configuration (possibly with per-field overrides)
     * @throws IllegalAccessException if the field cannot be accessed via reflection
     */
    private void generateExtendedValue(Field field, Fill extendedParameterForField) throws IllegalAccessException {

        Object value = new CollectionElementCreationService().generateCollection(field, extendedParameterForField);
        if (value != null) {
            field.setAccessible(true);
            field.set(fill.getObjectz(), value);

        }
    }

    /**
     * Builds a new {@link Fill} with per-field overrides (collection size, value length)
     * from the matching {@link Extend} parameter, if one exists for the given field.
     *
     * @param field the field to look up in the extended parameters list
     * @return an {@link Optional} containing the customized {@link Fill}, or empty if no match
     */
    private Optional<Fill> getExtendedParameterForField(Field field) {

        return fill.getExtendedFieldParams()
                .stream()
                .filter(getExtendPredicate(field))
                .map(extendedFieldParameter -> Fill.object(fill.getObjectz())
                        .excludeField(fill.getExcludedField())
                        .fieldParams(fill.getExtendedFieldParams())
                        .collectionSize(Optional.ofNullable(extendedFieldParameter.getCollectionSize()).orElse(fill.getCollectionSize()))
                        .valueLength(Optional.ofNullable(extendedFieldParameter.getValueLength()).orElse(fill.getValueLength()))
                        .setDeep(fill.getDeep())
                        .withGeneric(fill.getGenericType())
                        .gen()
                )
                .findFirst();
    }

    /**
     * Returns a predicate that matches an {@link Extend} parameter to a field
     * either by field name or by assignable class type.
     *
     * @param field the field to match against
     * @return a predicate for filtering {@link Extend} parameters
     */
    public static Predicate<Extend> getExtendPredicate(Field field) {
        return fillFieldParameter -> {
            if(fillFieldParameter.getFieldName() != null) {
                return fillFieldParameter.getFieldName().equals(field.getName());
            } else {
                return field.getType().isAssignableFrom(fillFieldParameter.getClazz());
            }
        };
    }


}
