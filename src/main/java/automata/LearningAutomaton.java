package automata;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public class LearningAutomaton extends Automaton {

    LinkedHashMap<Integer, State> States;
    State initialState;
    HashSet<String> letters;

    public State transition(InfiniteWordGenerator infiniteWord, Long prefix) {
        return this.transition(this.initialState, infiniteWord, prefix);
    }

    public State transition(State initialState, InfiniteWordGenerator infiniteWord, Long prefix) {
        State currentState = initialState;
        for(String letter : infiniteWord.getPrefix(prefix)) {
            currentState = currentState.getStateTransitions().get(letter);
        }
        return currentState;
    }

    @Override
    public <B> B foldRight(B z, BiFunction<?, B, B> f) {
        return null;
    }

    public LinkedHashMap<Integer, State> getStates() {
        return States;
    }

    public void setStates(LinkedHashMap<Integer, State> states) {
        States = states;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public HashSet<String> getLetters() {
        return letters;
    }

    public void setLetters(HashSet<String> letters) {
        this.letters = letters;
    }
}
