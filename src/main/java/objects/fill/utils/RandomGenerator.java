package objects.fill.utils;

import objects.fill.core.GlobalParameters;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class RandomGenerator {

    public static String randomNum() {
        return randomNumeric(GlobalParameters.valueLength.getValue());
    }

    public static String randomAlphabet() {
        return randomAlphabetic(GlobalParameters.valueLength.getValue());
    }

    public static Date generateRandomDate() {
        Random r = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(java.util.Calendar.MONTH, Math.abs(r.nextInt()) % 12);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, Math.abs(r.nextInt()) % 30);
        calendar.setLenient(true);
        return calendar.getTime();
    }
}
