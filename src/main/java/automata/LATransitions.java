package automata;

public class LATransitions {

    private LearningAutomaton learningAutomaton;

    public LATransitions(LearningAutomaton learningAutomaton) {
        this.learningAutomaton = learningAutomaton;
    }

    public State transition(InfiniteWordGenerator infiniteWord, Long prefix) {
        return this.transition(learningAutomaton.getInitialState(), infiniteWord, prefix);
    }

    public State transition(State initialState, InfiniteWordGenerator infiniteWord, Long prefix) {
        State currentState = initialState;
        for(String letter : infiniteWord.getPrefix(prefix)) {
            currentState = currentState.getStateTransitions().get(letter);
        }
        return currentState;
    }

}
