package automata;

import algorithm.StateFunction;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class State {
    private Long id;
    private Boolean isAccepting;
    private HashMap<String, State> stateTransitions = new HashMap<>();
    private HashMap<String, Long> indexTransitions = new HashMap<>();
    private Boolean visited = false;
    private StateLetterPair predecessor;

    public State(JSONObject state) {
        this.id = (Long) state.get("id");
        this.isAccepting = (Boolean) state.get("accepting");
        JSONObject transitions = (JSONObject) state.get("transitions");
        transitions.keySet().forEach(key -> {
            Long value = (Long) transitions.get(key);
            indexTransitions.put((String) key, value);
        });
    }

    public State(StateFunction stateFunction) {
        this.id = stateFunction.getId();
        this.isAccepting = stateFunction.isAccepting();
        stateFunction.getDescendants().forEach((letter, descendant) -> this.indexTransitions.put(letter, descendant.getId()));
    }

    public State(Long id) {
        this.id = id;;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Boolean isAccepting() { return isAccepting; }

    public void setAccepting(Boolean accepting) { isAccepting = accepting; }

    public HashMap<String, State> getStateTransitions() { return stateTransitions; }

    public void setStateTransitions(HashMap<String, State> stateTransitions) { this.stateTransitions = stateTransitions; }

    public HashMap<String, Long> getIndexTransitions() { return indexTransitions; }

    public void setIndexTransitions(HashMap<String, Long> indexTransitions) { this.indexTransitions = indexTransitions; }

    public Boolean isVisited() { return visited; }

    public void setVisited(Boolean visited) { this.visited = visited; }

    public StateLetterPair getPredecessor() { return predecessor; }

    public void setPredecessor(StateLetterPair predecessor) { this.predecessor = predecessor; }

    public List<State> getDescendants() {
        return stateTransitions.values().stream().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("\nState id: %d, \naccepting: %b, \ntransitions: ", id, isAccepting) + Arrays.toString(indexTransitions.entrySet().toArray()) + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof State)) {
            return false;
        }
        State s = (State) o;

        return s.id == this.id;
    }
}
