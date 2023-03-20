package objects.fill.types.box_type;

import objects.fill.object_param.FillObjectParams;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static objects.fill.core.RandomValueObjectFill.objectCount;

public class FillDate implements FillBoxType {

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
                .range(0, objectCount)
                .mapToObj(i -> generateRandomDate());
    }

    private Date generateRandomDate() {
        Random r = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(java.util.Calendar.MONTH, Math.abs(r.nextInt()) % 12);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, Math.abs(r.nextInt()) % 30);
        calendar.setLenient(true);
        return calendar.getTime();
    }
}
