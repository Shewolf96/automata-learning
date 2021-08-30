package algorithm;

import automata.*;
import read.ParsingAutomataService;

public class Main {
    public static void main(String[] args) {

        String exampleRun = "src/main/java/test/resources/automata/automataPerformanceTest";
        String path = args.length > 0 ? args[0] : exampleRun;

        TargetAutomaton TA = ParsingAutomataService.parseAutomaton(path);
        LearningAutomaton LA = Algorithm.learn(TA);

        System.out.println(LA);
    }
}
