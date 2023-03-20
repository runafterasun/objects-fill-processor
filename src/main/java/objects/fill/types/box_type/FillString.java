package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FillString implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return randomAlphabetic(valueLength);
    }

    @Override
    public Class<?> getClazz() {
        return String.class;
    }
}
