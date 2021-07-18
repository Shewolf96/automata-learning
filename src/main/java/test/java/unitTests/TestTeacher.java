package test.java.unitTests;

import automata.InfiniteWordGenerator;
import automata.Teacher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.java.services.ParseAutomataService;

public class TestTeacher {

    @BeforeClass
    public static void setup() {

    }

    @Test
    public void testAutomata1() {
        Teacher teacher = ParseAutomataService.getTeacher("automata1.1");

//        Assert.assertEquals(getLoopIndex(teacher, "b,a", "a,b,a"), 0);
    }

    @Test
    public void testAutomata3() {
        Teacher teacher = ParseAutomataService.getTeacher("automata3.1");

        Assert.assertEquals(getLoopIndex(teacher, "b,a", "a,b,a"), 0);
        Assert.assertEquals(getLoopIndex(teacher, "b,a", "a"), 2);
        Assert.assertEquals(getLoopIndex(teacher, "b,a,a,a,a", "a"), 2);
        Assert.assertEquals(getLoopIndex(teacher, "b,b,a,b,b", "b"), 5);
        Assert.assertEquals(getLoopIndex(teacher, "b,b,a,b,b", "a,b,b"), 0);
        Assert.assertEquals(getLoopIndex(teacher, "a,b,b,a,b,b", "a,b,b"), 1);
    }

    private int getLoopIndex(Teacher teacher, String w, String v) {
        return teacher.loopIndexQuery(new InfiniteWordGenerator(w,v));
    }
}
