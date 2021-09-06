package algorithm;

import automata.InfiniteWordGenerator;
import automata.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class Saturate {

    public static GenerateAutomaton Saturate(InfiniteWordGenerator divergingWord, HashSet<InfiniteWordGenerator> C, Teacher teacher) {
        Integer loopIndex = teacher.loopIndexQuery(divergingWord);
        String[] divergingWordPrefix = divergingWord.getPrefix(Long.valueOf(loopIndex + divergingWord.getV().length));

        String[] w = Arrays.copyOfRange(divergingWordPrefix, 0, loopIndex);
        String[] v = Arrays.copyOfRange(divergingWordPrefix, loopIndex, divergingWordPrefix.length);
        InfiniteWordGenerator witnessWord = new InfiniteWordGenerator(w,v);

        Integer i = witnessWord.length();
        Boolean isAccepting;
        GenerateAutomaton GA;
        do {
            i++;
            String[] witnessWordPrefix = witnessWord.getPrefix(i.longValue() - 1);
            //ok - to tutaj mogłabyś nie brać domknięcia i potem filtrować, tylko wziąć wszystkie v i ich rotacje - a potem dokleić ten witnessWordPrefix

            HashSet<InfiniteWordGenerator> C1 = new HashSet<>(C);
            C1.add(witnessWord);
            C1 = getClosure(C1).stream().filter(infWord -> infWord.getW().length == 0).collect(Collectors.toCollection(HashSet::new));
            C1.forEach(infWord -> infWord.setW(witnessWordPrefix));

            C.addAll(getClosure(C1));
            GA = new GenerateAutomaton(C, teacher);
            isAccepting = GA.transition(witnessWord, i.longValue()).isAccepting();
        } while (teacher.membershipQuery(witnessWord) != isAccepting);
        return GA;
    }

    protected static Set<InfiniteWordGenerator> getClosure(HashSet<InfiniteWordGenerator> C) {
        HashSet<InfiniteWordGenerator> closure = new HashSet<>();
        C.stream().forEach(infWord -> closure.addAll(getRotations(infWord)));
        return closure;
    }

    protected static Set<InfiniteWordGenerator> getRotations(InfiniteWordGenerator infWord) {
        HashSet<InfiniteWordGenerator> rotations = new HashSet<>();
        List<String> infWordPrefix = infWord.getWAsList();
        List<String> infWordCycle = infWord.getVAsList();
        while(!infWordPrefix.isEmpty()) {
            rotations.add(new InfiniteWordGenerator(infWordPrefix, infWordCycle));
            infWordPrefix.remove(0);
        }
        for (int i = 0; i < infWordCycle.size(); i ++) {
            rotations.add(new InfiniteWordGenerator(new LinkedList<>(), infWordCycle));
            infWordCycle.add(infWordCycle.remove(0));
        }
        return rotations;
    }

}
