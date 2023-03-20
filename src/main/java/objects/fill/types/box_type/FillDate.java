package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.util.Date;

public class FillDate implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return new Date(System.currentTimeMillis());
    }

    @Override
    public Class<?> getClazz() {
        return Date.class;
    }
}
