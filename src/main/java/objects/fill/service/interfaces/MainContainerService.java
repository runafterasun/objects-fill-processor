package objects.fill.service.interfaces;

import java.util.Map;

public interface MainContainerService<T> {

    Map<Class<?>, T> getContainer();
}
