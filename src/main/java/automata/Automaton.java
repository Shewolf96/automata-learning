package automata;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public abstract class Automaton {
    protected LinkedHashMap<Long, State> states = new LinkedHashMap<>();
    protected Long initialStateId;
    protected State initialState;
    protected String[] letters;
    public abstract <B> B foldRight(B z, BiFunction<?, B, B> f);

    public Collection<State> getStateCollection() {
        return states.values();
    }

    public LinkedHashMap<Long, State> getStates() { return states; }

    public void setStates(LinkedHashMap<Long, State> states) { this.states = states; }

    public Long getInitialStateId() { return initialStateId; }

    public void setInitialStateId(Long initialStateId) { this.initialStateId = initialStateId; }

    public State getInitialState() { return initialState; }

    public void setInitialState(State initialState) { this.initialState = initialState; }

    public String[] getLetters() { return letters; }

    public void setLetters(String[] letters) { this.letters = letters; }
}
