package objects.fill.utils;

import objects.fill.object_param.Fill;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class RandomGenerator {

    private RandomGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String randomNum(Fill fill) {
        return randomNumeric(fill.getValueLength());
    }

    public static String randomAlphabet(Fill fill) {
        return randomAlphabetic(fill.getValueLength());
    }

    public static Date generateRandomDate() {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(java.util.Calendar.MONTH, Math.abs(random.nextInt()) % 12);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, Math.abs(random.nextInt()) % 30);
        calendar.setLenient(true);
        return calendar.getTime();
    }
}
