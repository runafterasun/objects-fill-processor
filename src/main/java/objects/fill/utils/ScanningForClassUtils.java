package objects.fill.utils;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

public class ScanningForClassUtils {

    private ScanningForClassUtils() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings({"unchecked","unused"})
    public static <T extends Annotation, K> List<K> scanProjectForAnnotationAndCastToInterface(Class<T> annotationClazz, Class<K> castInterface) {
        Class<?> aClass = deduceMainApplicationClass();
        assert aClass != null;
        Reflections reflections = new Reflections(aClass.getPackageName());

        Set<Class<?>> annotated =
                reflections.get(SubTypes.of(TypesAnnotated.with(annotationClazz)).asClass());
        return annotated.stream()
                .map(ss -> {
                    try {
                        return (K) ss.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> scanClassImplInterface(Class<T> castInterface, String packagePath) {
        Reflections reflections = new Reflections(packagePath);

        Set<Class<?>> subTypes =
                reflections.get(SubTypes.of(castInterface).asClass());

        return subTypes.stream()
                .map(ss -> {
                    try {
                        return (T) ss.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

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
