package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

public class FillBoolean implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Math.random() < 0.5;
    }

    @Override
    public Class<?> getClazz() {
        return Boolean.class;
    }
}
