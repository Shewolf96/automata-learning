package automata;

import com.google.inject.internal.util.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class TargetAutomaton extends Automaton {
    //make all fields and methods static
    private static Long initialStateId;
    private static State initialState;

    private static JSONArray alphabet;

    private static LinkedHashMap<Long, State> states = new LinkedHashMap<>();
    private static JSONArray statesJSON;

    public TargetAutomaton(JSONObject automaton) {
        this.alphabet = (JSONArray) automaton.get("alphabet");
        this.statesJSON = (JSONArray) automaton.get("states");
        this.initialStateId = (Long) automaton.get("initialState");
    }

    public TargetAutomaton(JSONObject automaton, JSONArray alphabet, JSONArray states, Long initialState) {
        this.initialStateId = (Long) automaton.get("initialState");
        this.alphabet = (JSONArray) automaton.get("alphabet");
        Iterator<JSONObject> iterator = states.iterator();
        while(iterator.hasNext()) {
            State state = new State(iterator.next());
            this.states.put(state.getId(), state);
        }
        this.initialState = this.states.get(initialStateId);
        for(State s: this.states.values()) {
            for(Map.Entry<String, Long> sigma : s.getIndexTransitions().entrySet())
                s.getStateTransitions().put(sigma.getKey(), this.states.get(sigma.getValue()));
        }
    }
//
//    BiConsumer<Long, State> initiateStateTransitions =
//            (key, value) -> {
//
//            }

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

    public List<Long> getRun(String [] infiniteWordPrefix) {
        List<Long> run = Lists.newArrayList(initialStateId);
        State currentState = initialState;
        for(String letter : infiniteWordPrefix) {
            run.add(currentState.getIndexTransitions().get(letter));
            currentState = currentState.getStateTransitions().get(letter);
        }
        return run;
    }

    public List<Pair> getLetterStateRun(String [] infiniteWordPrefix) {
        List<Pair> run = Lists.newArrayList( new Pair("", initialStateId));
        State currentState = initialState;
        for(String letter : infiniteWordPrefix) {
            run.add(new Pair(letter, currentState.getIndexTransitions().get(letter)));
            currentState = currentState.getStateTransitions().get(letter);
        }
        return run;
    }
//
//    public List<State> getRun(String [] infiniteWordPrefix) {
//        List<State> run = List.of(initialState);
//        State currentState = initialState;
//        for(String letter : infiniteWordPrefix) {
//            run.add(currentState.getStateTransitions().get(letter));
//        }
//        return run;
//    }

    ///maybe not public - protected and then Teacher.java could extend it

//    @Override
//    public static Long getInitialStateId() {
//        return initialStateId;
//    }
//
//    public static State getInitialState() {
//        return initialState;
//    }

    public static JSONArray getAlphabet() {
        return alphabet;
    }

//    public static LinkedHashMap<Long, State> getStates() {
//        return states;
//    }

    public static JSONArray getStatesJSON() {
        return statesJSON;
    }

    //////////

    public int getSize() {
        return states.size();
    }

    @Override
    public <B> B foldRight(B z, BiFunction<?, B, B> f) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Target Automaton:");
        sb.append("\ninitialStateId: ");
        sb.append(initialStateId);
        sb.append("\nalphabet: ");
        sb.append(alphabet);
        sb.append("\nstates: \n");
        sb.append(states);
        return sb.toString();
    }
}
