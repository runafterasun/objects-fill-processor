package objects.fill.types.box_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.Fill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.*;

public class LongFill implements BoxTypeFill {

    @Override
    public Object generate(Fill fill) {
        return Long.parseLong(randomNum());
    }

    @Override
    public Class<?> getClazz() {
        return Long.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> Long.parseLong(randomNum()));
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
