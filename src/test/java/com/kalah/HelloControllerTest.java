package com.kalah;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class HelloControllerTest {
    private HelloController helloController;

    @Before
    public void init() {
        helloController = new HelloController();
    }

    @Test
    public void testRootIndex() {

        String value = helloController.index();
        assertEquals(value, "Hello World!");

    }

    @After
    public void tearDown() {
        helloController = null;
    }
}