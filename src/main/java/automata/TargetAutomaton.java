package automata;

import com.google.inject.internal.util.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.function.BiFunction;

public final class TargetAutomaton extends Automaton {

    public TargetAutomaton(JSONObject automaton, JSONArray alphabet, JSONArray states, Long initialState) {
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

    public TargetAutomaton(String[] letters, Long size) {
        this.letters = letters;
        this.initialState = new State(0l);
        this.initialStateId = 0l;
        this.states.put(initialState.getId(), initialState);
        for(long i = 1; i < size; i++) {
            this.states.put(i, new State(i));
        }
        for(State s : this.states.values()) {
            for(String a : this.letters) {
                s.setAccepting(new Random().nextBoolean());
                Long transitionStateId = (long) (Math.random() * size);
                s.getIndexTransitions().put(a, transitionStateId);
                s.getStateTransitions().put(a, states.get(transitionStateId));
            }
        }
    }

    protected State transition(InfiniteWordGenerator infiniteWord, Long prefix) {
        return this.transition(this.initialState, infiniteWord, prefix);
    }

    protected State transition(State initialState, InfiniteWordGenerator infiniteWord, Long prefix) {
        State currentState = initialState;
        for(String letter : infiniteWord.getPrefix(prefix)) {
            currentState = currentState.getStateTransitions().get(letter);
        }
        return currentState;
    }

    protected List<Long> getRun(String [] infiniteWordPrefix) {
        List<Long> run = Lists.newArrayList(initialStateId);
        State currentState = initialState;
        for(String letter : infiniteWordPrefix) {
            run.add(currentState.getIndexTransitions().get(letter));
            currentState = currentState.getStateTransitions().get(letter);
        }
        return run;
    }

    protected List<Pair> getLetterStateRun(String [] infiniteWordPrefix) {
        List<Pair> run = Lists.newArrayList();
        State currentState = initialState;
        for(String letter : infiniteWordPrefix) {
            run.add(new Pair(letter, currentState.getId()));
            currentState = currentState.getStateTransitions().get(letter);
        }
        return run;
    }

    protected int getSize() {
        return states.size();
    }

    public <B> B foldRight(B z, BiFunction<?, B, B> f) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Target Automaton:");
        sb.append("\ninitialStateId: ");
        sb.append(initialStateId);
        sb.append("\nalphabet: ");
        sb.append(letters);
        sb.append("\nstates: \n");
        sb.append(states);
        return sb.toString();
    }
}
