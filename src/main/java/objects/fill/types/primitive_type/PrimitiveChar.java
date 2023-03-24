package objects.fill.types.primitive_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.Fill;
import objects.fill.types.box_type.BoxTypeFill;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.utils.RandomGenerator.randomAlphabet;

public class PrimitiveChar implements BoxTypeFill {
    @Override
    public Object generate(Fill fill) {
        return randomAlphabet().charAt(0);
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> randomAlphabet().charAt(0));
    }

    @Override
    public Class<?> getClazz() {
        return char.class;
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
