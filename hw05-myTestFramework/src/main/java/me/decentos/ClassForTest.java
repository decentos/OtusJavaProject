package me.decentos;

import me.decentos.annotations.After;
import me.decentos.annotations.Before;
import me.decentos.annotations.Test;

public class ClassForTest {

    @Test
    public void testMethod1() {
        System.out.println("Start test method 1!");
    }

    @Test
    public void testException() {
        throw new RuntimeException("Test method with exception");
    }

    @Test
    public void testMethod2() {
        System.out.println("Start test method 2!");
    }

    @Before
    public void beforeMethod1() {
        System.out.println("Start before method 1!");
    }

    @Before
    public void beforeMethod2() {
        System.out.println("Start before method 2!");
    }

    @After
    public void afterMethod1() {
        System.out.println("Start after method 1!");
    }

    @After
    public void afterMethod2() {
        System.out.println("Start after method 2!");
    }
}
