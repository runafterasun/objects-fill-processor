package ru.objectsfill.service.containers;

import ru.objectsfill.service.interfaces.BoxTypeContainerService;
import ru.objectsfill.types.box_type.*;
import ru.objectsfill.types.primitive_type.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Default registry of {@link BoxTypeFill} implementations for all built-in types:
 * primitives, wrappers, String, UUID, BigDecimal, and Date.
 */
public class DefaultBoxTypeContainer implements BoxTypeContainerService {

    private final Map<Class<?>, BoxTypeFill> container;

    /**
     * Constructs the container and registers all built-in type handlers.
     */
    public DefaultBoxTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(BigDecimal.class, new BigDecimalFill());
        container.putIfAbsent(Long.class, new LongFill());
        container.putIfAbsent(Integer.class, new IntegerFill());
        container.putIfAbsent(Boolean.class, new BooleanFill());
        container.putIfAbsent(Double.class, new DoubleFill());
        container.putIfAbsent(Date.class, new DateFill());
        container.putIfAbsent(String.class, new StringFill());
        container.putIfAbsent(UUID.class, new UUIDFill());
        container.putIfAbsent(Character.class, new CharacterFill());
        container.putIfAbsent(int.class, new PrimitiveInt());
        container.putIfAbsent(long.class, new PrimitiveLong());
        container.putIfAbsent(double.class, new PrimitiveDouble());
        container.putIfAbsent(boolean.class, new PrimitiveBoolean());
        container.putIfAbsent(char.class, new PrimitiveChar());
    }

    /**
     * {@inheritDoc}
     */
    public Map<Class<?>, BoxTypeFill>  getContainer() {
        return container;
    }

}
