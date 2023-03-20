package objects.fill;

import objects.fill.types.box_type.FillBigDecimal;
import objects.fill.types.box_type.FillBoxType;

import java.util.ArrayList;
import java.util.List;

public class Example {

    private final static List<FillBoxType> container;

    static {
        container = new ArrayList<>();
        container.add(new FillBigDecimal());
    }

}
