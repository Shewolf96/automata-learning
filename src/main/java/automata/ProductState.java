package automata;

import java.util.*;

public class ProductState {

    private Boolean visited = false;
    private Boolean reachable = false;
    private Pair<String, ProductState> predecessor;
    private HashMap<String, ProductState> stateTransitions = new HashMap<>();

    public State state1;
    public State state2;

    public ProductState(State s1, State s2) {
        this.state1 = s1;
        this.state2 = s2;
    }

    public Boolean isVisited() { return visited; }

    public void setVisited(Boolean visited) { this.visited = visited; }

    public Boolean isReachable() { return reachable; }

    public void setReachable(Boolean reachable) { this.reachable = reachable; }

    public Pair<String, ProductState> getPredecessor() { return predecessor; }

    public void setPredecessor(Pair<String, ProductState> predecessor) { this.predecessor = predecessor; }

    public HashMap<String, ProductState> getStateTransitions() { return stateTransitions; }

    public Collection<ProductState> getDescendants() {
        return stateTransitions.values();
    }

    public Boolean onlyFirstAccepting() {
        return state1.isAccepting() && !state2.isAccepting();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof ProductState)) {
            return false;
        }
        ProductState p = (ProductState) o;

        return p.state1.equals(this.state1) && p.state2.equals(this.state2);
    }
}
