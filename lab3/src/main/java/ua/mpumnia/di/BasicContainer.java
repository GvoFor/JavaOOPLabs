package ua.mpumnia.di;

import org.fpm.di.Container;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicContainer implements Container {

    private final Map<Class<?>, Class<?>> classesMap;
    private final Map<Class<?>, Object> instancesMap;

    public BasicContainer(Map<Class<?>, Class<?>> classesMap, Map<Class<?>, Object> instancesMap) {
        this.classesMap = classesMap;
        this.instancesMap = instancesMap;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {

        if (instancesMap.containsKey(clazz)) {
            return (T) instancesMap.get(clazz);
        }

        if (classesMap.containsKey(clazz)) {
            Class<T> requestedClass = (Class<T>) classesMap.get(clazz);
            if (!requestedClass.equals(clazz) &&
                    (classesMap.containsKey(requestedClass) ||
                            instancesMap.containsKey(requestedClass))) {
                return getComponent(requestedClass);
            }
            return getInstance(requestedClass);
        } else {
            List<Class<? extends T>> implementations = getAllStoredImplementations(clazz);
            if (implementations.isEmpty()) {
                throw new ContainerException("Unregistered class: %s".formatted(clazz));
            }

            if (implementations.size() > 1) {
                throw new ContainerException("Too many implementations for class %s: %s"
                        .formatted(clazz, implementations));
            }

            return getComponent(implementations.get(0));
        }
    }

    private <T> T getInstance(Class<T> clazz) {
        T instance = null;
        try {
            instance = getInstanceBasedOnInjectConstructor(clazz);
            if (instance == null) {
                instance = getInstanceBasedOnEmptyConstructor(clazz);
            }
        } catch (NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            throw new ContainerException("Couldn't instantiate class %s".formatted(clazz));
        }
        return instance;
    }

    private <T> T getInstanceBasedOnInjectConstructor(Class<T> clazz) throws
            InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getAnnotation(Inject.class) != null) {
                Class<?>[] params = constructor.getParameterTypes();
                Object[] args = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    args[i] = getComponent(params[i]);
                }
                constructor.setAccessible(true);
                T instance = (T) constructor.newInstance(args);
                if (clazz.getAnnotation(Singleton.class) != null) {
                    instancesMap.put(clazz, instance);
                }
                return instance;
            }
        }
        return null;
    }

    private <T> T getInstanceBasedOnEmptyConstructor(Class<T> clazz) throws
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        if (clazz.getAnnotation(Singleton.class) != null) {
            instancesMap.put(clazz, instance);
        }
        return instance;
    }

    private <T> List<Class<? extends T>> getAllStoredImplementations(Class<T> clazz) {
        List<Class<? extends T>> implementations = new ArrayList<>();
        for (Class<?> key : classesMap.keySet()) {
            Class<?> superClass = key;
            while (!superClass.equals(Object.class)) {
                if (superClass.equals(clazz)) {
                    implementations.add((Class<? extends T>) key);
                }
                superClass = superClass.getSuperclass();
            }
        }
        return implementations;
    }
}
