package algorithm;

import automata.InfiniteWordGenerator;
import automata.P;

import java.util.HashMap;
import java.util.HashSet;

public class StateFunction {

    private Long id;
//    private State state;
    private HashSet<InfiniteWordGenerator> C;
    private HashMap<InfiniteWordGenerator, P<Boolean, Long>> definingFunction;
    private String[] selector;
    private HashMap<String, StateFunction> descendants = new HashMap<>();
    private Boolean accepting = false;
    private Boolean visited = false;

    public StateFunction(Long id) { this.id = id; }

    public StateFunction(Long id, HashMap<InfiniteWordGenerator, P<Boolean, Long>> definingFunction, String[] selector, HashSet<InfiniteWordGenerator> C) {
        this.id = id;
        this.C = C;
        this.definingFunction = definingFunction;
        this.selector = selector;
        this.accepting = accepting;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public HashMap<InfiniteWordGenerator, P<Boolean, Long>> getDefiningFunction() { return definingFunction; }

    public void setDefiningFunction(HashMap<InfiniteWordGenerator, P<Boolean, Long>> definingFunction) { this.definingFunction = definingFunction; }

    public String[] getSelector() { return selector; }

    public void setSelector(String[] selector) { this.selector = selector; }

    public HashMap<String, StateFunction> gettDescendants() { return descendants; }

    public void setDescendants(HashMap<String, StateFunction> descendants) { this.descendants = descendants; }

    public Boolean isAccepting() { return accepting; }

    public void setAccepting(Boolean accepting) { accepting = accepting; }

    public HashMap<String, StateFunction> getDescendants() { return descendants; }

    public Boolean getVisited() { return visited; }

    public void setVisited(Boolean visited) { this.visited = visited; }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StateFunction)) {
            return false;
        }
        StateFunction s = (StateFunction) o;
        for(InfiniteWordGenerator infWord : C) {
            if(!definingFunction.get(infWord).equals(s.definingFunction.get(infWord)))
                return false;
        }
        return true;
    }
}
