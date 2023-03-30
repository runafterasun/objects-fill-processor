package objects.fill.service.containers;

import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.types.box_type.BigDecimalFill;
import objects.fill.types.box_type.BooleanFill;
import objects.fill.types.box_type.BoxTypeFill;
import objects.fill.types.box_type.CharacterFill;
import objects.fill.types.box_type.DateFill;
import objects.fill.types.box_type.DoubleFill;
import objects.fill.types.box_type.IntegerFill;
import objects.fill.types.box_type.LongFill;
import objects.fill.types.box_type.StringFill;
import objects.fill.types.box_type.UUIDFill;
import objects.fill.types.primitive_type.PrimitiveBoolean;
import objects.fill.types.primitive_type.PrimitiveChar;
import objects.fill.types.primitive_type.PrimitiveDouble;
import objects.fill.types.primitive_type.PrimitiveInt;
import objects.fill.types.primitive_type.PrimitiveLong;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultBoxTypeContainer implements BoxTypeContainerService {

    private final Map<Class<?>, BoxTypeFill> container;

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
        container.putIfAbsent(int.class, new PrimitiveInt());
        container.putIfAbsent(long.class, new PrimitiveLong());
        container.putIfAbsent(double.class, new PrimitiveDouble());
        container.putIfAbsent(boolean.class, new PrimitiveBoolean());
        container.putIfAbsent(Character.class, new CharacterFill());
        container.putIfAbsent(char.class, new PrimitiveChar());

    }

    public Map<Class<?>, BoxTypeFill>  getContainer() {
        return container;
    }

}
