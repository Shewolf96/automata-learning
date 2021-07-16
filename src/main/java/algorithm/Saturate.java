package algorithm;

import automata.InfiniteWordGenerator;
import automata.LearningAutomaton;
import automata.Teacher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Saturate {

    public static HashSet<InfiniteWordGenerator> Saturate(InfiniteWordGenerator divergingWord, HashSet<InfiniteWordGenerator> C, Teacher teacher) {
        Integer loopIndex = teacher.loopIndexQuery(divergingWord);
        String[] divergingWordPrefix = divergingWord.getPrefix(Long.valueOf(loopIndex + divergingWord.getV().length));

        String[] w = Arrays.copyOfRange(divergingWordPrefix, 0, loopIndex);
        String[] v = Arrays.copyOfRange(divergingWordPrefix, loopIndex, divergingWordPrefix.length);
        InfiniteWordGenerator witnessWord = new InfiniteWordGenerator(w,v);

        Integer i = witnessWord.length();
        Boolean isAccepting;
        do {
            i++;
            String[] witnessWordPrefix = witnessWord.getPrefix(i.longValue() - 1);

            HashSet<InfiniteWordGenerator> C1 = new HashSet<>(C) ;
            C1.add(witnessWord);
            C1 = getClosure(C1).stream().filter(infWord -> infWord.getW().length == 0).collect(Collectors.toCollection(HashSet::new));
            C1.forEach(infWord -> infWord.setW(witnessWordPrefix));

            C.addAll(getClosure(C1));
            GenerateAutomaton GA = new GenerateAutomaton(C, teacher);
            isAccepting = GA.transition(witnessWord, i.longValue()).isAccepting();
        } while (teacher.membershipQuery(witnessWord) != isAccepting);
        return C;//maybe return whole GA instead of C - cause later you compute it second time with the same (C, teacher)
    }

    protected static HashSet<InfiniteWordGenerator> getClosure(HashSet<InfiniteWordGenerator> C) {
        HashSet<InfiniteWordGenerator> closure = new HashSet<>();
        C.stream().forEach(infWord -> closure.addAll(getRotations(infWord)));
        return closure;
    }

    protected static HashSet<InfiniteWordGenerator> getRotations(InfiniteWordGenerator infWord) {
        HashSet<InfiniteWordGenerator> rotations = new HashSet<>();
        List<String> infWordPrefix = infWord.getWAsList();
        List<String> infWordCycle = infWord.getVAsList();
        while(!infWordPrefix.isEmpty()) {
            rotations.add(new InfiniteWordGenerator(infWordPrefix, infWordCycle));
            infWordPrefix.remove(0);
        }
        for (int i = 0; i < infWordCycle.size(); i ++) {
            rotations.add(new InfiniteWordGenerator(new LinkedList<>(), infWordCycle));
            infWordCycle.add(infWordCycle.remove(0));//??
        }
        return rotations;
    }

//procedure SATURATE (x, y, C, T)
//    li := T. LOOPINDEX (x,y)
//    (w, v) := (xy ω )[1,li], (xy ω )[li+1,li+ LENGTH (v)]
//    i := LENGTH (w)+ LENGTH (v)+1
//    repeat
//        C’ := C ∪ cl ({(wv ω [1, i], c) | (, c) ∈ cl (C∪{(w, v)})}
//        Ac := GENERATE AUTOMATON (C)
//        class acc := ACCEPTING (Ac, EVAL (Ac,( wv) ω [1, i]))
//        i := i+1
//    until class acc = T. VALUE (w,v)
//    return C’

}
