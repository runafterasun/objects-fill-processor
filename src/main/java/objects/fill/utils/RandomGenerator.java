package objects.fill.utils;

import objects.fill.object_param.Fill;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * Utility class for generating random values.
 */
public class RandomGenerator {

    /**
     * Private constructor to prevent instantiation of the utility class.
     * Throws an IllegalStateException if called.
     */
    private RandomGenerator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates a random numeric string of the specified length.
     *
     * @param fill the Fill object containing the value length
     * @return a random numeric string
     */
    public static String randomNum(Fill fill) {
        return randomNumeric(fill.getValueLength());
    }

    /**
     * Generates a random alphabetic string of the specified length.
     *
     * @param fill the Fill object containing the value length
     * @return a random alphabetic string
     */
    public static String randomAlphabet(Fill fill) {
        return randomAlphabetic(fill.getValueLength());
    }

    /**
     * Generates a random Date object representing a random date.
     *
     * @return a random Date object
     */
    public static Date generateRandomDate() {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Math.abs(random.nextInt()) % 12);
        calendar.set(Calendar.DAY_OF_MONTH, Math.abs(random.nextInt()) % 30);
        calendar.setLenient(true);
        return calendar.getTime();
    }
}
