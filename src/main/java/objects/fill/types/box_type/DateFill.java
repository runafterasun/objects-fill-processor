package objects.fill.types.box_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;

import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.generateRandomDate;

public class DateFill implements BoxTypeFill {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return generateRandomDate();
    }

    @Override
    public Class<?> getClazz() {
        return Date.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> generateRandomDate());
    }

    @Override
    public int hashCode() {
        return getClazz().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BoxTypeFill boxTypeFill) {
            return this.getClazz().equals(boxTypeFill.getClazz());
        }
        return false;
    }

}