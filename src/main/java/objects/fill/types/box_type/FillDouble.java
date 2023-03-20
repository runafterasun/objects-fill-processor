package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class FillDouble implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return Double.parseDouble(randomNumeric(valueLength));
    }

    @Override
    public Class<?> getClazz() {
        return Double.class;
    }
}
