package objects.fill.service.containers;

import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.types.box_type.*;
import objects.fill.types.primitive_type.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultBoxTypeContainer implements BoxTypeContainerService {

    private final List<FillBoxType> container;

    public DefaultBoxTypeContainer() {
        container = new ArrayList<>();
        container.add(new FillBigDecimal());
        container.add(new FillLong());
        container.add(new FillInteger());
        container.add(new FillBoolean());
        container.add(new FillDouble());
        container.add(new FillDate());
        container.add(new FillString());
        container.add(new FillUUID());
        container.add(new PrimitiveInt());
        container.add(new PrimitiveLong());
        container.add(new PrimitiveDouble());
        container.add(new PrimitiveBoolean());
        container.add(new FillCharacter());
        container.add(new PrimitiveChar());

    }

    public List<FillBoxType> getContainer() {
        return container;
    }

}
