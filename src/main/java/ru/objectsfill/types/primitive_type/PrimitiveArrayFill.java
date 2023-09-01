package ru.objectsfill.types.primitive_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.utils.RandomGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.objectsfill.utils.RandomGenerator.randomNum;

/**
 * Class for generating and filling primitive array values.
 */
public class PrimitiveArrayFill {

    /**
     * immutable class
     */
    private PrimitiveArrayFill(){}

    /**
     * array with primitive types name
     */
    public static final List<String> primitiveArrayFieldNames = new ArrayList<>(Arrays.asList("long", "int", "char", "double", "boolean"));

    /**
     * Fill field with random array
     *
     * @param fill the Fill object containing the configuration for generating values
     * @param componentName name of the component
     * @param field for fill
     */
    public static void fillPrimitiveArrayField(Fill fill, String componentName, Field field) throws IllegalAccessException {
        field.setAccessible(true);

        switch (componentName) {
            case "long" -> field.set(fill.getObjectz(), createArrayPrimitiveLong(fill));
            case "int" -> field.set(fill.getObjectz(), createArrayPrimitiveInt(fill));
            case "char" -> field.set(fill.getObjectz(), createArrayPrimitiveChar(fill));
            case "double" -> field.set(fill.getObjectz(), createArrayPrimitiveDouble(fill));
            case "boolean" -> field.set(fill.getObjectz(), createArrayPrimitiveBoolean(fill));
            default -> throw new IllegalStateException("Unsupported primitive type: " + componentName);
        }
    }

    /**
     * Fill long primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     */
    private static long[] createArrayPrimitiveLong(Fill fill) {
        long[] genericArray = new long[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = Long.parseLong(randomNum(fill));
        }
        return genericArray;
    }


    /**
     * Fill int primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     */
    private static int[] createArrayPrimitiveInt(Fill fill) {
        int[] genericArray = new int[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = Integer.parseInt(randomNum(fill));
        }
        return genericArray;
    }

    /**
     * Fill char primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     */
    private static char[] createArrayPrimitiveChar(Fill fill) {
        char[] genericArray = new char[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = RandomGenerator.randomAlphabet(fill).charAt(0);
        }
        return genericArray;
    }

    /**
     * Fill double primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     */
    private static double[] createArrayPrimitiveDouble(Fill fill) {
        double[] genericArray = new double[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = Double.parseDouble(RandomGenerator.randomNum(fill));
        }
        return genericArray;
    }

    /**
     * Fill boolean primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     */
    private static boolean[] createArrayPrimitiveBoolean(Fill fill) {
        boolean[] genericArray = new boolean[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = Math.random() < 0.5;
        }
        return genericArray;
    }
}
