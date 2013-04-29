package org.injector;

import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Date: 4/27/13
 * Time: 5:04 PM
 * IoC container (JSR-330 implementation).
 *
 * @author Artem Prigoda
 */
public class Injector {

    private final Map<Class, Object> singletons = new HashMap<Class, Object>();

    private Injector() {
    }


    public static Injector create() {
        return new Injector();
    }

    /**
     * Get an instance of the class
     *
     * @param clazz class metadata
     * @param <T>   class type
     * @return an instance of the class
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        if (isSingleton(clazz)) {
            synchronized (singletons) {
                Object instance = singletons.get(clazz);
                if (instance == null) {
                    instance = createInstance(clazz);
                    singletons.put(clazz, instance);
                }
                return (T) instance;
            }
        }
        return (T) createInstance(clazz);
    }

    /**
     * Create a new instance of class
     * Process @Inject on fields or on a constructor with params
     *
     * @param clazz object class
     * @return a new instance
     */
    private Object createInstance(Class clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                return injectByFields(constructor);
            }
            for (Annotation an : constructor.getDeclaredAnnotations()) {
                if (!an.annotationType().equals(Inject.class)) {
                    continue;
                }
                return injectByConstructor(constructor);
            }
        }
        throw new IllegalArgumentException("No appropriate constructors in " + clazz);
    }


    /**
     * Inject dependencies in constructor with params
     *
     * @param constructor constructor with params
     * @return object with dependencies
     */
    private Object injectByConstructor(Constructor constructor) {
        Class<?>[] params = constructor.getParameterTypes();
        Object[] args = new Object[params.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = get(params[i]);
        }
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Process fields with @Inject annotations
     *
     * @param constructor default constructor
     */
    private Object injectByFields(Constructor constructor) {
        try {
            constructor.setAccessible(true);
            Object object = constructor.newInstance();
            for (Field field : constructor.getDeclaringClass().getDeclaredFields()) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation an : annotations) {
                    if (!an.annotationType().equals(Inject.class)) {
                        continue;
                    }
                    Class<?> fieldType = field.getType();
                    Object fieldValue = get(fieldType);
                    field.setAccessible(true);
                    field.set(object, fieldValue);
                }
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Check whether class is declared as singleton
     *
     * @param clazz class metadata
     * @return true, if class is singleton
     */
    private boolean isSingleton(Class clazz) {
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(Singleton.class)) {
                return true;
            }
        }
        return false;
    }
}
