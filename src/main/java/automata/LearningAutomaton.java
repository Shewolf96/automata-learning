package automata;

import algorithm.GenerateAutomaton;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;

public class LearningAutomaton extends Automaton {

    public LearningAutomaton(JSONObject automaton, JSONArray alphabet, JSONArray states, Long initialState) {
        this.initialStateId = (Long) automaton.get("initialState");
        Iterator<JSONObject> iterator = states.iterator();
        while(iterator.hasNext()) {
            State state = new State(iterator.next());
            this.states.put(state.getId(), state);
        }
        letters = (String[]) alphabet.stream().toArray(String[]::new);
        this.initialState = this.states.get(initialStateId);
        for(State s: this.states.values()) {
            for(Map.Entry<String, Long> sigma : s.getIndexTransitions().entrySet())
                s.getStateTransitions().put(sigma.getKey(), this.states.get(sigma.getValue()));
        }
    }

    public LearningAutomaton(JSONArray alphabet) {
        letters = (String[]) alphabet.stream().toArray(String[]::new);
    }

    public LearningAutomaton(GenerateAutomaton GA) {
        this.letters = GA.getLetters().toArray(String[]::new);
        GA.getStates().forEach((letter, state) -> this.states.put(letter, new State(state)));
        this.initialStateId = GA.initialState.getId();
        this.initialState = states.get(initialStateId);
        states.values().forEach(
                state -> state.getIndexTransitions().forEach(
                        (letter, stateId) ->
                                state.getStateTransitions().put(letter, states.get(stateId))));// xxd to chyba wcale nie jest czytelne
    }

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

    public <B> B foldRight(B z, BiFunction<?, B, B> f) {
        return null;
    }

}
