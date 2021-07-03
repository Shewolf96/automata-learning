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
        this.letters = teacher.getLetters();
        states = new HashMap<>();
        transitions = new MultiKeyMap();
        initialState = new StateFunction(id.next());
        states.put(initialState.getId(), initialState);
        addSuccessors();
        computeAcceptingStates();
    }

    private void addSuccessors() {
        queue = new LinkedList<>();
        queue.add(initialState);
        while(!queue.isEmpty()) {
            StateFunction state = queue.remove();
            state.setVisited(true);
            for(String letter : letters) {
                if(transitions.get(state, letter) == null) {
                    String[] nextSelector = Arrays.copyOf(state.getSelector(), state.getSelector().length + 1);
                    nextSelector[nextSelector.length - 1] = letter;
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
        C.stream().forEach(infWord -> definingFunction.put(
                infWord, new P(teacher.membershipQuery(infWord), teacher.loopIndexQuery(selector, infWord))));
        return new StateFunction(id.next(), definingFunction, selector, C);
    }

    private void computeAcceptingStates() {
        states.values().stream().filter(StateFunction::isOnAnyAcceptingLoop).forEach(s -> s.setAccepting(true));
    }

    public StateFunction transition(InfiniteWordGenerator infiniteWord, Long prefix) {
        return this.transition(this.initialState, infiniteWord, prefix);
    }

    public StateFunction transition(StateFunction initialState, InfiniteWordGenerator infWord, Long prefix) {
        StateFunction currentState = initialState;
        for(String letter : infWord.getPrefix(prefix)) {
            currentState = currentState.getDescendants().get(letter);
        }
        return currentState;
    }

    public Teacher getTeacher() { return teacher; }

    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public static HashSet<InfiniteWordGenerator> getC() { return C; }

    public static void setC(HashSet<InfiniteWordGenerator> c) { C = c; }

    public static HashMap<Long, StateFunction> getStates() { return states; }

    public static void setStates(HashMap<Long, StateFunction> states) { GenerateAutomaton.states = states; }

    public static List<String> getLetters() { return letters; }

    public static void setLetters(List<String> letters) { GenerateAutomaton.letters = letters; }

    public static StateFunction getInitialState() { return initialState; }

    public static void setInitialState(StateFunction initialState) { GenerateAutomaton.initialState = initialState; }

    public static MultiKeyMap getTransitions() { return transitions; }

    public static void setTransitions(MultiKeyMap transitions) { GenerateAutomaton.transitions = transitions; }
}
