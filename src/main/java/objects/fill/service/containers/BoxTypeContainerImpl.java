package objects.fill.service.containers;

import objects.fill.service.interfaces.BoxTypeContainerService;
import objects.fill.types.box_type.*;

import java.util.ArrayList;
import java.util.List;

public class BoxTypeContainerImpl implements BoxTypeContainerService {

    private final List<FillBoxType> container;

    public BoxTypeContainerImpl() {
        container = new ArrayList<>();
        container.add(new FillBigDecimal());
        container.add(new FillLong());
        container.add(new FillInteger());
        container.add(new FillBoolean());
        container.add(new FillDouble());
        container.add(new FillDate());
        container.add(new FillString());
        container.add(new FillUUID());

    }

    public List<FillBoxType> getContainer() {
        return container;
    }

}
