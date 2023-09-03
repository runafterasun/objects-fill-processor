package ru.objectsfill.types.primitive_type;

import org.apache.commons.lang3.RandomUtils;
import ru.objectsfill.functions.BinaryFunction;
import ru.objectsfill.object_param.Fill;
import ru.objectsfill.utils.RandomGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;

import static ru.objectsfill.utils.RandomGenerator.randomNum;

/**
 * enum with primitive types name and function to create array
 */
public enum PrimitiveTypeName {

    /**Primitive long for array creation*/
    LONG("long", (fill, field) -> field.set(fill.getObjectz(), createArrayPrimitiveLong(fill))),
    /**Primitive int for array creation*/
    INT("int", (fill, field) -> field.set(fill.getObjectz(), createArrayPrimitiveInt(fill))),
    /**Primitive char for array creation*/
    CHAR("char", (fill, field) -> field.set(fill.getObjectz(), createArrayPrimitiveChar(fill))),
    /**Primitive double for array creation*/
    DOUBLE("double", (fill, field) -> field.set(fill.getObjectz(), createArrayPrimitiveDouble(fill))),
    /**Primitive byte for array creation*/
    BYTE("byte", (fill, field) -> field.set(fill.getObjectz(), createArrayPrimitiveByte(fill))),
    /**Primitive boolean for array creation*/
    BOOLEAN("boolean", (fill, field) -> field.set(fill.getObjectz(), createArrayPrimitiveBoolean(fill)));

    /**
     * type name
     */
    private final String typeName;

    /**
     * the function to create array
     */
    private final BinaryFunction<Fill, Field> createPrimitiveArray;

    /**
     * constructor for primitive array list
     * @param typeName name of primitive
     * @param createPrimitiveArray function for primitive array creation
     */
    PrimitiveTypeName(String typeName, BinaryFunction<Fill, Field> createPrimitiveArray) {
        this.typeName = typeName;
        this.createPrimitiveArray = createPrimitiveArray;
    }

    /**
     * getType name
     * @return type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * get the function to create array
     * @return primitive array function
     */
    public BinaryFunction<Fill, Field> getCreatePrimitiveArray() {
        return createPrimitiveArray;
    }

    /**
     * Find primitive by name
     *
     * @param name the Fill object containing the configuration for generating values
     * @return primitive type name
     */
    public static PrimitiveTypeName getByName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.getTypeName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Fill long primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return primitive long array
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
     * @return primitive int array
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
     * @return primitive char array
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
     * @return primitive double array
     */
    private static double[] createArrayPrimitiveDouble(Fill fill) {
        double[] genericArray = new double[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = Double.parseDouble(RandomGenerator.randomNum(fill));
        }
        return genericArray;
    }

    /**
     * Fill byte primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return primitive byte array
     */
    private static byte[] createArrayPrimitiveByte(Fill fill) {
        return RandomUtils.nextBytes(fill.getCollectionSize());
    }

    /**
     * Fill boolean primitive array
     *
     * @param fill the Fill object containing the configuration for generating values
     * @return primitive boolean array
     */
    private static boolean[] createArrayPrimitiveBoolean(Fill fill) {
        boolean[] genericArray = new boolean[fill.getCollectionSize()];
        for (int i = 0; i < genericArray.length; i++) {
            genericArray[i] = Math.random() < 0.5;
        }
        return genericArray;
    }

}
