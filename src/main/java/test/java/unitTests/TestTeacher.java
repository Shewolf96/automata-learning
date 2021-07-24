package test.java.unitTests;

import automata.InfiniteWordGenerator;
import automata.LearningAutomaton;
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
    public void testAutomata0() {
        Teacher teacher = ParseAutomataService.getTeacher("automata0.1");

        Assert.assertEquals(0, getLoopIndex(teacher, ",", "a"));
        Assert.assertEquals(0, getLoopIndex(teacher, "a", "a"));
        Assert.assertEquals(0, getLoopIndex(teacher, "a,a,a,a,a", "a"));
    }

    @Test
    public void testAutomata1() {
        Teacher teacher = ParseAutomataService.getTeacher("automata1.1");

        Assert.assertEquals(2, getLoopIndex(teacher, "a,a,a,a,a", "a"));
        Assert.assertEquals(3, getLoopIndex(teacher, "b,a", "c"));
        Assert.assertEquals(0, getLoopIndex(teacher, "a,c,a", "c,a"));
        Assert.assertEquals(0, getLoopIndex(teacher, ",", "c,a,b,c,b,b,c,c,b,a,b"));
        Assert.assertEquals(2, getLoopIndex(teacher, "c,c", "c,a,b,c,b,b,c,c,b,a,b"));
        Assert.assertEquals(2, getLoopIndex(teacher, "a", "c,a,b,c,b,b,c,c,b,a,b"));
        Assert.assertEquals(7, getLoopIndex(teacher, "c,c,c,a,b,b", "c,a,b,c,b,b,c,c,b,a,b"));
    }

    @Test
    public void testAutomata3() {
        Teacher teacher = ParseAutomataService.getTeacher("automata3.1");

        Assert.assertEquals(0, getLoopIndex(teacher, "b,a", "a,b,a"));
        Assert.assertEquals(2, getLoopIndex(teacher, "b,a", "a"));
        Assert.assertEquals(2, getLoopIndex(teacher, "b,a,a,a,a", "a"));
        Assert.assertEquals(5, getLoopIndex(teacher, "b,b,a,b,b", "b"));
        Assert.assertEquals(0, getLoopIndex(teacher, "b,b,a,b,b", "a,b,b"));
        Assert.assertEquals(1, getLoopIndex(teacher, "a,b,b,a,b,b", "a,b,b"));
    }

    @Test
    public void testEquivalenceQuery0() {
        Teacher teacher = ParseAutomataService.getTeacher("automata0.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata0.0");
        Assert.assertTrue(teacher.equivalenceQuery(LA).isEmpty());

    }

    @Test
    public void testEquivalenceQuery1() {
        Teacher teacher = ParseAutomataService.getTeacher("automata1.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata1.2");
        Assert.assertFalse(teacher.equivalenceQuery(LA).isEmpty());

    }

    @Test
    public void testEquivalenceQuery2() {
        Teacher teacher = ParseAutomataService.getTeacher("automata2");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata2");
        Assert.assertTrue(teacher.equivalenceQuery(LA).isEmpty());

    }

    @Test
    public void testEquivalenceQuery3() {
        Teacher teacher = ParseAutomataService.getTeacher("automata3.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata3.2");
        Assert.assertFalse(teacher.equivalenceQuery(LA).isEmpty());

    }

    @Test
    public void testEquivalenceQuery4() {
        Teacher teacher = ParseAutomataService.getTeacher("automata4.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata4.2");
        Assert.assertTrue(teacher.equivalenceQuery(LA).isEmpty());

    }

    @Test
    public void testEquivalenceQuery5() {
        Teacher teacher = ParseAutomataService.getTeacher("automata5.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata5.2");
        Assert.assertFalse(teacher.equivalenceQuery(LA).isEmpty());

    }

    @Test
    public void testEquivalenceQuery6() {
        Teacher teacher = ParseAutomataService.getTeacher("automata6.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata6.2");
        Assert.assertFalse(teacher.equivalenceQuery(LA).isEmpty());

    }

    private int getLoopIndex(Teacher teacher, String w, String v) {
        return teacher.loopIndexQuery(new InfiniteWordGenerator(w,v));
    }
}
