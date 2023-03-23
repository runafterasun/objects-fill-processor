package objects.fill.service.containers;

import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.types.box_type.*;
import objects.fill.types.primitive_type.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultBoxTypeContainer implements BoxTypeContainerService {

    private final List<BoxTypeFill> container;

    public DefaultBoxTypeContainer() {
        container = new ArrayList<>();
        container.add(new BigDecimalFill());
        container.add(new LongFill());
        container.add(new IntegerFill());
        container.add(new BooleanFill());
        container.add(new DoubleFill());
        container.add(new DateFill());
        container.add(new StringFill());
        container.add(new UUIDFill());
        container.add(new PrimitiveInt());
        container.add(new PrimitiveLong());
        container.add(new PrimitiveDouble());
        container.add(new PrimitiveBoolean());
        container.add(new CharacterFill());
        container.add(new PrimitiveChar());

    }

    public List<BoxTypeFill> getContainer() {
        return container;
    }

}
