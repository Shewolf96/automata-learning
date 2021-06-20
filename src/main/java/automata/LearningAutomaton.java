package automata;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;

public class LearningAutomaton extends Automaton {

    private static JSONArray alphabet;

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
