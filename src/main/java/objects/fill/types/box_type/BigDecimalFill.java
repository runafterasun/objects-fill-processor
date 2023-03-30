package objects.fill.types.box_type;

import objects.fill.object_param.Fill;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomNum;

public class BigDecimalFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return BigDecimal.valueOf(Long.parseLong(randomNum(fill)));
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> BigDecimal.valueOf(Long.parseLong(randomNum(fill))));
    }

}
