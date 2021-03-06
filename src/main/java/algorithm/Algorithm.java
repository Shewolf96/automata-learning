package algorithm;

import automata.InfiniteWordGenerator;
import automata.LearningAutomaton;
import automata.TargetAutomaton;
import automata.Teacher;

import java.util.HashSet;

public class Algorithm {

    public static LearningAutomaton learn(TargetAutomaton TA) {
        HashSet<InfiniteWordGenerator> C = new HashSet<>();
        Teacher teacher = new Teacher(TA);
        GenerateAutomaton GA = new GenerateAutomaton(C, teacher);
        LearningAutomaton LA = new LearningAutomaton(GA);
        InfiniteWordGenerator divergingWord = teacher.equivalenceQuery(LA);
        while (!divergingWord.isEmpty()) {
            GA = Saturate.Saturate(divergingWord, C, teacher);
            LA = new LearningAutomaton(GA);
            divergingWord = teacher.equivalenceQuery(LA);
        }
        return LA;
    }

}
