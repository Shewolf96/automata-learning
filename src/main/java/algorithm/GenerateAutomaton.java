package algorithm;

import automata.*;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.*;

public class GenerateAutomaton {

    Teacher teacher;

    public static HashSet<InfiniteWordGenerator> C;
    public static HashMap<Long, StateFunction> states;
    public static List<String> letters;
    public static StateFunction initialState;
    public static MultiKeyMap transitions;
//    public static MultiKeyMap<P<StateFunction, String>, StateFunction> transitions;

    private static Queue<StateFunction> queue;

    private Iterator<Long> id = new Iterator<Long>() {
        private Long currentId = 0l;

        @Override
        public boolean hasNext() { return true; }

        @Override
        public Long next() { return currentId++; }
    };

    public GenerateAutomaton(HashSet<InfiniteWordGenerator> C, Teacher teacher) {
        this.teacher = teacher;
        this.C = C;
        states = new HashMap<>();
        transitions = new MultiKeyMap();
        initialState = new StateFunction(id.next());
        states.put(initialState.getId(), initialState);
        queue = new LinkedList<>();
        queue.add(initialState);
        addSuccessors();
    }

//        for any
//        state q and letter a such that the value of δ(q, a) is not set, take the
//        selector w q and compute q a such that for all (r, c) ∈ C we have
//        q a (r, c) =(T. VALUE (warc ω ), ∗ T (wa, r, c)). If q a is in the set of
//        states, then δ(q, a) .. = q a . Otherwise, add q a to the set of states, set
//        δ(q, a) .. = q a and w q a .. = wa.

    private void addSuccessors() {
        while(!queue.isEmpty()) {
            StateFunction state = queue.remove();
            state.setVisited(true);
            for(String letter : letters) {
                if(transitions.get(state, letter) == null) {
                    String[] nextSelector = Arrays.copyOf(state.getSelector(), state.getSelector().length + 1);
                    StateFunction q = computeStateFunction(nextSelector);
                    Optional<StateFunction> existingState = states.values().stream().filter(a -> a.equals(q)).findFirst();
                    if(existingState.isPresent()) {
                        transitions.put(state, letter, existingState.get());
                        state.getDescendants().put(letter, existingState.get());
                    } else {
                        states.put(q.getId(), q);
                        state.getDescendants().put(letter, q);
                        transitions.put(state, letter, q);
                        nextSelector[nextSelector.length -1] = letter;
                        q.setSelector(nextSelector);
                    }
                }
            }
            state.getDescendants().values().stream().filter(s -> !s.getVisited()).forEach(s -> queue.add(s));
        }
    }
    private StateFunction computeStateFunction(String[] selector) {
        HashMap<InfiniteWordGenerator, P<Boolean, Long>> definingFunction = new HashMap<>();
        C.stream().forEach(infWord -> definingFunction.put(infWord, new P(teacher.membershipQuery(infWord), teacher.loopIndexQuery(infWord))));
        //todo: compute if accepting (probably after the whole bfs)
        return new StateFunction(id.next(), definingFunction, selector, C);
    }

    //todo:
    //procedure GENERATE AUTOMATON (C, T)
//    A := (Σ, {q 0 }, q 0 , {}, {})
//            while A 6 = ADD SUCCESSORS (A) do
//                A := ADD SUCCESSORS (A)
//    A := COMPUTE ACCEPTING STATES (A)
//return A

    //czyli tutaj głównie to add_successors - czyli bfs po stano-funkcjach
    //no i potem które są akceptujące (dopytaj JMi jak to jest z tym loopIndex)

}
