package me.decentos;

import me.decentos.annotations.After;
import me.decentos.annotations.Before;
import me.decentos.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestMain {
    private String className;
    private int count = 0;
    private int passed = 0;
    private List<Method> testingMethods = new ArrayList<>();
    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();

    TestMain(String className) {
        this.className = className;
    }

    void run() {
        Class classForTest;
        try {
            classForTest = Class.forName(className);
            fillMethod(classForTest);

            for (Method method : testingMethods) {
                Object instanceOfClassForTest = getInstance(classForTest);
                invokeMethod(method, instanceOfClassForTest);
            }
        } catch (ClassNotFoundException e) {
            System.out.printf("Class %s not found: %s %n", className, e.getCause());
        }
        System.out.printf("%nResult: Total count: %d, Passed: %d, Failed: %d%n", count, passed, count - passed);
    }

    private Object getInstance(Class classForTest) {
        Object instanceOfClassForTest = null;
        try {
            instanceOfClassForTest = classForTest.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.printf("Class %s cannot be instantiated: %s %n", classForTest.getName(), e.getCause());
        }
        return instanceOfClassForTest;
    }

    private void invokeMethod(Method method, Object instanceOfClassForTest) {
        try {
            try {
                invokeMethods(instanceOfClassForTest, beforeMethods);
                method.invoke(instanceOfClassForTest);
            } finally {
                invokeMethods(instanceOfClassForTest, afterMethods);
            }
            System.out.printf("%s - Test '%s' - passed %n", ++count, method.getName());
            passed++;

        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.printf("%s - Test '%s' - failed: %s %n", ++count, method.getName(), e.getCause());
        }
    }

    private void invokeMethods(Object instance, Collection<Method> method) throws IllegalAccessException, InvocationTargetException {
        for (Method m : method) {
            m.invoke(instance);
        }
    }

    private void fillMethod(Class classForTest) {
        for (Method method : classForTest.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {

                if (annotation instanceof Test) {
                    testingMethods.add(method);
                }
                else if (annotation instanceof Before) {
                    beforeMethods.add(method);
                }
                else if (annotation instanceof After) {
                    afterMethods.add(method);
                }
            }
        }
    }
}