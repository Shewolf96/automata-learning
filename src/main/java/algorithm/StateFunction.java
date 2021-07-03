package algorithm;

import automata.InfiniteWordGenerator;
import automata.P;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class StateFunction {

    private Long id;
    private HashSet<InfiniteWordGenerator> C = new HashSet<>();
    private HashMap<InfiniteWordGenerator, P<Boolean, Long>> definingFunction = new HashMap<>();
    private String[] selector;
    private HashMap<String, StateFunction> descendants = new HashMap<>();
    private Boolean accepting = false;
    private Boolean visited = false;

    public StateFunction(Long id) {
        this.id = id;
        this.selector = new String[]{};
    }

    public StateFunction(Long id, HashMap<InfiniteWordGenerator, P<Boolean, Long>> definingFunction, String[] selector, HashSet<InfiniteWordGenerator> C) {
        this.id = id;
        this.C = C;
        this.definingFunction = definingFunction;
        this.selector = selector;
    }

    public Boolean isOnAnyAcceptingLoop() {
        HashSet<InfiniteWordGenerator> emptyPrefixWords = C.stream().filter(w -> (w.getW().length == 0)).collect(Collectors.toCollection(HashSet::new));
        for(InfiniteWordGenerator infWord : emptyPrefixWords) {
            if(!definingFunction.get(infWord).first && definingFunction.get(infWord).second == 0) return false;
        }
        return true;
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

    public void setAccepting(Boolean accepting) { this.accepting = accepting; }

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
