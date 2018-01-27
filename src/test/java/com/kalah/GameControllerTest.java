package com.kalah;

import com.kalah.controller.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GameControllerTest {
    private GameController helloController;

    @Before
    public void init() {
        helloController = new GameController();
    }
//
//    @Test
//    public void testRootIndex() {
//
//        String value = helloController.index();
//        assertEquals(value, "Hello World!");
//
//    }

    @After
    public void tearDown() {
        helloController = null;
    }
}