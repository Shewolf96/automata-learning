package test.java.unitTests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGenerateAutomaton {

    @BeforeClass
    public static void setUp() {
        System.out.println("setting up");
    }

    @AfterClass
    public static void finished() {
        System.out.println("finished");
    }

    @Test
    public void testHelloWorld() {
        Assert.assertTrue(true);
    }

}
