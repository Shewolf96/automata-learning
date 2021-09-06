package algorithm;

import automata.*;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.*;

public class GenerateAutomaton extends C {

    Teacher teacher;

    public HashMap<Long, StateFunction> states;
    public List<String> letters;
    public StateFunction initialState;
    public MultiKeyMap transitions;
//    public MultiKeyMap<P<StateFunction, String>, StateFunction> transitions;

    private Queue<StateFunction> queue;

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
        initialState = computeStateFunction(new String [] {});
        states.put(initialState.getId(), initialState);
        addSuccessors();
        computeAcceptingStates();
    }

    private void addSuccessors() {
        queue = new LinkedList<>();
        queue.add(initialState);
        while(!queue.isEmpty()) {
            StateFunction state = queue.remove();
            if(state.isVisited()) continue;
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
                        q.setSelector(nextSelector);
                    }
                }
            }
            state.getDescendants().values().stream().filter(s -> !s.isVisited()).forEach(s -> queue.add(s));
        }
    }
    protected StateFunction computeStateFunction(String[] selector) {
        HashMap<InfiniteWordGenerator, Pair<Boolean, Long>> definingFunction = new HashMap<>();
        C.stream().forEach(infWord -> definingFunction.put(
                infWord, new Pair(teacher.membershipQuery(selector, infWord), teacher.loopIndexQuery(selector, infWord))));
        return new StateFunction(id.next(), definingFunction, selector);
    }

    protected void computeAcceptingStates() {
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

    public HashSet<InfiniteWordGenerator> getC() { return C; }

    public void setC(HashSet<InfiniteWordGenerator> c) { C = c; }

    public HashMap<Long, StateFunction> getStates() { return states; }

    public void setStates(HashMap<Long, StateFunction> states) { this.states = states; }

    public List<String> getLetters() { return letters; }

    public void setLetters(List<String> letters) { this.letters = letters; }

    public StateFunction getInitialState() { return initialState; }

    public void setInitialState(StateFunction initialState) { this.initialState = initialState; }

    public MultiKeyMap getTransitions() { return transitions; }

    public void setTransitions(MultiKeyMap transitions) { this.transitions = transitions; }
}
