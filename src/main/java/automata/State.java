package automata;

import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class State {
    private Long id;
    private Boolean isAccepting;
    private HashMap<String, State> stateTransitions = new HashMap<>();
    private HashMap<String, Long> indexTransitions = new HashMap<>();

    public State(JSONObject state) {
        this.id = (Long) state.get("id");
        this.isAccepting = (Boolean) state.get("accepting");
        JSONObject transitions = (JSONObject) state.get("transitions");
        transitions.keySet().forEach(key -> {
            Long value = (Long) transitions.get(key);
            indexTransitions.put((String) key, value);
        });
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Boolean isAccepting() { return isAccepting; }

    public void setAccepting(Boolean accepting) { isAccepting = accepting; }

    public HashMap<String, State> getStateTransitions() { return stateTransitions; }

    public void setStateTransitions(HashMap<String, State> stateTransitions) { this.stateTransitions = stateTransitions; }

    public HashMap<String, Long> getIndexTransitions() { return indexTransitions; }

    public void setIndexTransitions(HashMap<String, Long> indexTransitions) { this.indexTransitions = indexTransitions; }

    @Override
    public String toString() {
        return String.format("\nState id: %d, \naccepting: %b, \ntransitions: ", id, isAccepting) + Arrays.toString(indexTransitions.entrySet().toArray()) + "\n";
    }
}