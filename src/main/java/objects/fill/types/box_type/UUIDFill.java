package objects.fill.types.box_type;

import objects.fill.core.GlobalParameters;
import objects.fill.object_param.FillObjectParams;

import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class UUIDFill implements BoxTypeFill {

    @Override
    public Object generate(FillObjectParams fillObjectParams) {
        return UUID.randomUUID();
    }

    @Override
    public Class<?> getClazz() {
        return UUID.class;
    }

    @Override
    public Stream<Object> fillStream() {
        return IntStream
                .range(0, GlobalParameters.objectCount.getValue())
                .mapToObj(i -> UUID.randomUUID());
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
