package ua.mpumnia.di;

import org.fpm.di.Binder;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class BasicBinder implements Binder {

    private final Map<Class<?>, Class<?>> classesMap;
    private final Map<Class<?>, Object> instancesMap;

    public BasicBinder(Map<Class<?>, Class<?>> classesMap, Map<Class<?>, Object> instancesMap) {
        this.classesMap = classesMap;
        this.instancesMap = instancesMap;
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz == null) {
            throw new BindException("Binding null is not allowed");
        }
        bind(clazz, clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        if (classesMap.containsKey(clazz) || instancesMap.containsKey(clazz)) {
            throw new BindException(
                    "Rebinding is not allowed: class %s was already bound"
                            .formatted(clazz));
        }

        int implMod = implementation.getModifiers();
        if (Modifier.isAbstract(implMod) || Modifier.isInterface(implMod)) {
            throw new BindException("Implementation cannot be abstract");
        }

        int injectAnnotationsCount = findAllInjectConstructors(implementation).size();
        if (injectAnnotationsCount > 1) {
            throw new BindException("Implementation cannot have multiple constructor injection");
        }

        checkForCircularInjection(implementation);

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

    private List<Constructor<?>> findAllInjectConstructors(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).filter((constructor) ->
                constructor.getAnnotation(Inject.class) != null).toList();
    }

    private Optional<Constructor<?>> findAnyInjectConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).filter((constructor) ->
                constructor.getAnnotation(Inject.class) != null).findAny();
    }

    private void checkForCircularInjection(Class<?> clazz) {
        Queue<Class<?>> toBeCheckedQueue = new ArrayDeque<>();
        List<Class<?>> checked = new ArrayList<>();
        toBeCheckedQueue.add(clazz);
        while (!toBeCheckedQueue.isEmpty()) {
            Class<?> toBeChecked = toBeCheckedQueue.poll();
            Optional<Constructor<?>> constructorOpt =
                    findAnyInjectConstructor(toBeChecked);
            if (constructorOpt.isEmpty()) {
                continue;
            }
            Constructor<?> constructor = constructorOpt.get();
            Collections.addAll(toBeCheckedQueue, constructor.getParameterTypes());
            checked.add(toBeChecked);
            if (checked.stream().distinct().count() != checked.size()) {
                throw new CircularInjectException("There is circular inject in class %s".formatted(clazz));
            }
        }
    }
}
