package objects.fill.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ищем и создаем объект с списком классов с уникальным поведением.
 * */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ObjectType {

    Class<?> clazz();
}
