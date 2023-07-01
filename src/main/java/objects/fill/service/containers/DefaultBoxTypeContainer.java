package objects.fill.service.containers;

import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.types.box_type.*;
import objects.fill.types.primitive_type.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**

 The DefaultBoxTypeContainer class implements the BoxTypeContainerService interface

 and provides a default implementation of the container for BoxTypeFill instances.

 It contains mappings between Class objects and their respective BoxTypeFill implementations.
 */
public class DefaultBoxTypeContainer implements BoxTypeContainerService {

    private final Map<Class<?>, BoxTypeFill> container;
    /**

     Constructs a new DefaultBoxTypeContainer and initializes the container with default mappings.
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
        container.putIfAbsent(int.class, new PrimitiveInt());
        container.putIfAbsent(long.class, new PrimitiveLong());
        container.putIfAbsent(double.class, new PrimitiveDouble());
        container.putIfAbsent(boolean.class, new PrimitiveBoolean());
        container.putIfAbsent(Character.class, new CharacterFill());
        container.putIfAbsent(char.class, new PrimitiveChar());

    }
    /**

     Returns the container that holds the mappings between Class objects and BoxTypeFill implementations.
     @return The container.
     */
    public Map<Class<?>, BoxTypeFill>  getContainer() {
        return container;
    }

}
