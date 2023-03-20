package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.util.UUID;

public class FillUUID implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return UUID.randomUUID();
    }

    @Override
    public Class<?> getClazz() {
        return UUID.class;
    }
}
