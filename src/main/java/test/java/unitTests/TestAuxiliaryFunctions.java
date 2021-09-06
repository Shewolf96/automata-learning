package test.java.unitTests;

import automata.*;
import org.junit.Assert;
import org.junit.Test;
import test.java.services.ParseAutomataService;

import java.util.*;

public class TestAuxiliaryFunctions {

    @Test
    public void testSimpleAutomataReachableStates() {
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata0.1");
        ProductAutomaton productAutomaton = new ProductAutomaton(LA, LA);
        HashMap<Long, State> states = LA.getStates();

        List<ProductState> expectedResult = new LinkedList<>();
        expectedResult.add(new ProductState(states.get(0l),states.get(0l)));
        List<ProductState> actualResult = AuxiliaryFunctions.getReachableStates(productAutomaton);

        Assert.assertTrue(mutuallyContains(expectedResult, actualResult));

    }

    @Test
    public void testAutomata7ReachableStates() {
        TargetAutomaton TA = ParseAutomataService.parseTargetAutomaton("automata7.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata7.2");
        ProductAutomaton productAutomaton = new ProductAutomaton(TA, LA);
        HashMap<Long, State> states = TA.getStates();

        List<ProductState> expectedResult = new LinkedList<>();
        expectedResult.add(new ProductState(states.get(0l),states.get(1l)));
        expectedResult.add(new ProductState(states.get(1l),states.get(2l)));
        expectedResult.add(new ProductState(states.get(2l),states.get(3l)));
        expectedResult.add(new ProductState(states.get(3l),states.get(0l)));
        List<ProductState> actualResult = AuxiliaryFunctions.getReachableStates(productAutomaton);

        Assert.assertTrue(mutuallyContains(expectedResult, actualResult));

    }

    @Test
    public void testAutomata8ReachableStates() {
        TargetAutomaton TA = ParseAutomataService.parseTargetAutomaton("automata8.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata8.2");
        ProductAutomaton productAutomaton = new ProductAutomaton(TA, LA);
        HashMap<Long, State> states1 = TA.getStates();
        HashMap<Long, State> states2 = LA.getStates();

        List<ProductState> expectedResult = new LinkedList<>();
        expectedResult.add(new ProductState(states1.get(0l),states2.get(0l)));
        expectedResult.add(new ProductState(states1.get(1l),states2.get(1l)));
        expectedResult.add(new ProductState(states1.get(2l),states2.get(2l)));
        expectedResult.add(new ProductState(states1.get(3l),states2.get(1l)));
        expectedResult.add(new ProductState(states1.get(4l),states2.get(1l)));
        List<ProductState> actualResult = AuxiliaryFunctions.getReachableStates(productAutomaton);

        Assert.assertTrue(mutuallyContains(expectedResult, actualResult));

    }

    @Test
    public void testDfsPredecessors1()
    {
        TargetAutomaton TA = ParseAutomataService.parseTargetAutomaton("automata0.0");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata0.1");
        ProductAutomaton productAutomaton = new ProductAutomaton(TA, LA);
        List<ProductState> reachableStates = new LinkedList<>();
        AuxiliaryFunctions.dfs(productAutomaton.getInitialState(), reachableStates);
        reachableStates.forEach(s ->
                    Assert.assertTrue(isPredecessor(s, s.getPredecessor())));
    }

    @Test
    public void testDfsPredecessors2()
    {
        TargetAutomaton TA = ParseAutomataService.parseTargetAutomaton("automata7.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata7.2");
        ProductAutomaton productAutomaton = new ProductAutomaton(TA, LA);
        List<ProductState> reachableStates = new LinkedList<>();
        AuxiliaryFunctions.dfs(productAutomaton.getInitialState(), reachableStates);
        reachableStates.forEach(s ->
                Assert.assertTrue(isPredecessor(s, s.getPredecessor())));

    }

    @Test
    public void testDfsPredecessors3()
    {
        TargetAutomaton TA = ParseAutomataService.parseTargetAutomaton("automata5.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata5.2");
        ProductAutomaton productAutomaton = new ProductAutomaton(TA, LA);
        List<ProductState> reachableStates = new LinkedList<>();
        AuxiliaryFunctions.dfs(productAutomaton.getInitialState(), reachableStates);
        reachableStates.forEach(s ->
                Assert.assertTrue(isPredecessor(s, s.getPredecessor())));

    }

    @Test
    public void testDfsPredecessors4()
    {
        TargetAutomaton TA = ParseAutomataService.parseTargetAutomaton("automata8.1");
        LearningAutomaton LA = ParseAutomataService.parseLearningAutomaton("automata8.2");
        ProductAutomaton productAutomaton = new ProductAutomaton(TA, LA);
        List<ProductState> reachableStates = new LinkedList<>();
        AuxiliaryFunctions.dfs(productAutomaton.getInitialState(), reachableStates);
        reachableStates.stream()
                .filter(s -> !s.equals(productAutomaton.getInitialState()))
                .forEach(s ->
                    Assert.assertTrue(isPredecessor(s, s.getPredecessor())));
    }

    @Test
    public void testShortestCycle1() {
        InfiniteWordGenerator actualWord = AuxiliaryFunctions.findShortestCycle(new InfiniteWordGenerator("a,b,a","a,a,a,a"));
        InfiniteWordGenerator expectedWord = new InfiniteWordGenerator("a,b,a", "a");
        Assert.assertEquals(expectedWord, actualWord);;
    }

    @Test
    public void testShortestCycle2() {
        InfiniteWordGenerator actualWord = AuxiliaryFunctions.findShortestCycle(new InfiniteWordGenerator("","a,a,b,a,a,b,a,a,b"));
        InfiniteWordGenerator expectedWord = new InfiniteWordGenerator("", "a,a,b");
        Assert.assertEquals(expectedWord, actualWord);;
    }

    @Test
    public void testShortestCycle3() {
        InfiniteWordGenerator actualWord = AuxiliaryFunctions.findShortestCycle(new InfiniteWordGenerator("a,b,a","a,a,b,a,a,b,a"));
        InfiniteWordGenerator expectedWord = new InfiniteWordGenerator("a,b,a", "a,a,b,a,a,b,a");
        Assert.assertEquals(expectedWord, actualWord);;
    }

    private boolean isPredecessor(ProductState productState, Pair<String, ProductState> transition) {
        String letter = transition.first;
        ProductState predecessor = transition.second;
        return predecessor.state1.getStateTransitions().get(letter).equals(productState.state1) &&
                predecessor.state2.getStateTransitions().get(letter).equals(productState.state2);
    }

    private <E> boolean mutuallyContains(List<E> list1, List<E> list2) {
        return list1.containsAll(list2) && list2.containsAll(list1);
    }


}

