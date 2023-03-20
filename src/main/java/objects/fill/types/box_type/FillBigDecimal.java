package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.math.BigDecimal;

import static objects.fill.core.RandomValueObjectFill.valueLength;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class FillBigDecimal implements FillBoxType {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return BigDecimal.valueOf(Long.parseLong(randomNumeric(valueLength)));
    }

    @Override
    public Class<?> getClazz() {
        return BigDecimal.class;
    }
}
