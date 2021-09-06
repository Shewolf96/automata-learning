package test.java;

import algorithm.Algorithm;
import automata.AuxiliaryFunctions;
import automata.TargetAutomaton;
import org.junit.Test;
import read.ParsingAutomataService;

import java.util.Arrays;

public class TestPerformance {

//    private static String[] testAutomata = new String[] {"automataTestPerformance1", "automataTestPerformance2", "automataTestPerformance3", "automataTestPerformance4", "automataTestPerformance5"};
    private static String[] testAutomata = new String[] {"automataTestPerformance3"};

    private static String path = "src/main/java/test/resources/automata/performance/";

    public static Long measureTime(TargetAutomaton TA) {
        long startTime = System.nanoTime();
        Algorithm.learn(TA);
        long endTime = System.nanoTime();

        return (endTime - startTime);  //divide by 1000000 to get milliseconds.
    }

    public static String printPerformanceTest(String automaton) {
        TargetAutomaton TA = ParsingAutomataService.parseAutomaton(path + automaton);
        Long time = measureTime(TA);
        return String.format("Automaton size: %d, alphabet size: %d, time (in nanoseconds): %d",
                TA.getStates().size(), TA.getLetters().length, time);

    }

    @Test
    public void testPerformance() {
        Arrays.stream(testAutomata).forEach(a -> System.out.println(printPerformanceTest(a)));
    }

    @Test
    public void testPerformanceOnRandomAutomata() {
        Long automaonSize = 100l;
        Long numberOfTests = 1l;
        Long summaryTime = 0l;
        for(int i = 0; i < numberOfTests; i ++) {
            TargetAutomaton TA = new TargetAutomaton(new String[] {"a", "b"}, automaonSize);
            Long time = measureTime(TA)/1000000;//in milliseconds
            summaryTime += time;
            String output = String.format("Automaton size: %d, number of reachable states: %d, alphabet size: %d, time (ms): %d",
                    TA.getStates().size(), AuxiliaryFunctions.getReachableStates(TA).size(), TA.getLetters().length, time);
            System.out.println(output);
        }
        System.out.println(String.format("Avg. time: %d", summaryTime/numberOfTests));

    }

}
