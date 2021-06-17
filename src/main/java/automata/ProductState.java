package automata;

import java.util.*;
import java.util.stream.Collectors;

public class ProductState {

    public P<State, State> productState;
    private Boolean visited = false;

    public State first;
    public State second;

    public ProductState(P<State, State> state) {
        this.productState = state;
        this.first = state.first;
        this.second = state.second;
    }

    public ProductState(State s1, State s2) {
        new ProductState(new P(s1, s2));
    }

    public Boolean isVisited() { return visited; }

    public void setVisited(Boolean visited) { this.visited = visited; }

//    public List<ProductState> getDescendants() {
//        List<ProductState> descendants = new LinkedList<>();
//        for(Map.Entry<String, State> entrySet: productState.first.getStateTransitions().entrySet()) {
//            State s1 = entrySet.getValue();
//            State s2 = productState.second.getStateTransitions().get(entrySet.getKey());
//            descendants.add(new ProductState(new P(s1, s2)));
//        }
//        return descendants;
//    }

    public Map<String, ProductState> getDescendants() {
        Map<String, ProductState> descendants = new HashMap<>();
        for(Map.Entry<String, State> entrySet: productState.first.getStateTransitions().entrySet()) {
            State s1 = entrySet.getValue();
            State s2 = productState.second.getStateTransitions().get(entrySet.getKey());
            descendants.put(entrySet.getKey(), new ProductState(new P(s1, s2)));
        }
        return descendants;
    }
}