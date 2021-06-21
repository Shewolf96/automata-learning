package automata;

import java.util.*;

public class ProductState {

//    public P<State, State> productState;
    private Boolean visited = false;
    private Boolean reachable = false;
    private P<String, ProductState> predecessor;
    private HashMap<String, ProductState> stateTransitions = new HashMap<>();

    public State first;
    public State second;

//    public ProductState(P<State, State> state) {
//        this.productState = state;
//        this.first = state.first;
//        this.second = state.second;
//    }

    public ProductState(State s1, State s2) {
        this.first = s1;
        this.second = s2;
    }

    public Boolean isVisited() { return visited; }

    public void setVisited(Boolean visited) { this.visited = visited; }

    public Boolean isReachable() { return reachable; }

    public void setReachable(Boolean reachable) { this.reachable = reachable; }

    public P<String, ProductState> getPredecessor() { return predecessor; }

    public void setPredecessor(P<String, ProductState> predecessor) { this.predecessor = predecessor; }

    public HashMap<String, ProductState> getStateTransitions() { return stateTransitions; }

    public Collection<ProductState> getDescendants() {
        return stateTransitions.values();
    }

    public Boolean onlyFirstAccepting() {
        return first.isAccepting() && !second.isAccepting();
    }
}
