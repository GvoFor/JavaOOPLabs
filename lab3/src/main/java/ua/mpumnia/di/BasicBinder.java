package ua.mpumnia.di;

import org.fpm.di.Binder;

import java.util.Map;

public class BasicBinder implements Binder {

    private final Map<Class<?>, Class<?>> classesMap;
    private final Map<Class<?>, Object> instancesMap;

    public BasicBinder(Map<Class<?>, Class<?>> classesMap, Map<Class<?>, Object> instancesMap) {
        this.classesMap = classesMap;
        this.instancesMap = instancesMap;
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        bind(clazz, clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        if (classesMap.containsKey(clazz) || instancesMap.containsKey(clazz)) {
            throw new BindException(
                    "Rebinding is not allowed: class %s was already bound"
                            .formatted(clazz));
        }
        classesMap.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        if (classesMap.containsKey(clazz) || instancesMap.containsKey(clazz)) {
            throw new BindException(
                    "Rebinding is not allowed: class %s was already bound"
                            .formatted(clazz));
        }
        instancesMap.put(clazz, instance);
    }
}
