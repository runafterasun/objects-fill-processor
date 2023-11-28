package ru.objectsfill.utils;

import org.reflections.Reflections;
import ru.objectsfill.annotation_processor.exceptions.FillException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;
/**
 * Utility class for scanning classes and retrieving annotated instances or implementations of interfaces.
 */
public class ScanningForClassUtils {

    private ScanningForClassUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Scans the project for classes annotated with a specific annotation and casts them to a given interface.
     *
     * @param annotationClazz the class object representing the annotation to scan for
     * @param castInterface   the class object representing the interface to cast the annotated classes to
     * @param <T>             the type of the annotation
     * @param <K>             the type of the interface
     * @return a list of instances of the annotated classes casted to the specified interface
     */
    @SuppressWarnings({"unchecked","unused"})
    public static <T extends Annotation, K> List<K> scanProjectForAnnotationAndCastToInterface(Class<T> annotationClazz, Class<K> castInterface) {
        Class<?> aClass = deduceMainApplicationClass();
        assert aClass != null;
        Reflections reflections = new Reflections(aClass.getPackageName());

        Set<Class<?>> annotated =
                reflections.get(SubTypes.of(TypesAnnotated.with(annotationClazz)).asClass());
        return annotated.stream()
                .map(castClass -> {
                    try {
                        return (K) castClass.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new FillException(e.getMessage());
                    }
                })
                .toList();
    }
    /**
     * Scans the classpath for classes implementing a specific interface.
     *
     * @param castInterface the class object representing the interface to scan for implementations
     * @param packagePath   the package path to scan for classes
     * @param <T>           the type of the interface
     * @return a list of instances of the classes implementing the specified interface
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> scanClassImplInterface(Class<T> castInterface, String packagePath) {
        Reflections reflections = new Reflections(packagePath);

        Set<Class<?>> subTypes =
                reflections.get(SubTypes.of(castInterface).asClass());

        return subTypes.stream()
                .map(castClass -> {
                    try {
                        return (T) castClass.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new FillException(e.getMessage());
                    }
                })
                .toList();
    }
    /**
     * Deduces the main application class by inspecting the stack trace.
     *
     * @return the main application class, or null if it cannot be deduced
     */
    public static Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        }
        catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }
}
