package automata;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public abstract class Automaton {
//    private LinkedHashMap<Integer, State> States = new LinkedHashMap();
    protected static LinkedHashMap<Long, State> states = new LinkedHashMap<>();
    protected Integer initialStateId;
    protected State initialState;
    protected HashSet<String> letters = new HashSet<>();

    public abstract <B> B foldRight(B z, BiFunction<?, B, B> f);

//    public LinkedHashMap<Integer, State> getStates() {
//        return States;
//    }
//
//    public void setStates(LinkedHashMap<Integer, State> states) {
//        States = states;
//    }

    public static Collection<State> getStateCollection() {
        return states.values();
    }

    public static LinkedHashMap<Long, State> getStates() {
        return states;
    }

    public static void setStates(LinkedHashMap<Long, State> states) {
        Automaton.states = states;
    }

    public HashSet<String> getLetters() {
        return letters;
    }

    public void setLetters(HashSet<String> letters) {
        this.letters = letters;
    }

    public Integer getInitialStateId() {
        return initialStateId;
    }

    public void setInitialStateId(Integer initialStateId) {
        this.initialStateId = initialStateId;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public State getInitialState() {
        return initialState;
    }
}
