package automata;

import java.util.function.BiFunction;

public class LearningAutomaton extends Automaton {

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

}
