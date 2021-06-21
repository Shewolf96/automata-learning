package automata;

import java.util.*;
import org.apache.commons.collections4.map.MultiKeyMap;

public class ProductAutomaton {

    protected Automaton automaton1;
    protected Automaton automaton2;

    protected MultiKeyMap<Long, ProductState> productStates = new MultiKeyMap();

    protected ProductState initialState;
    protected String[] letters;

    public ProductAutomaton(Automaton automaton1, Automaton automaton2) {
        this.automaton1 = automaton1;
        this.automaton2 = automaton2;
        setLetters(automaton1.letters);
        initProductStates();
        setInitialState(productStates.get(automaton1.initialStateId, automaton2.getInitialStateId()));
    }

    private void initProductStates() {
        for(Map.Entry<Long, State> s1 : automaton1.getStates().entrySet()) {
            for(Map.Entry<Long, State> s2 : automaton2.getStates().entrySet()) {
                ProductState productState = new ProductState(s1.getValue(), s2.getValue());
                productStates.put(s1.getKey(), s2.getKey(), productState);
            }
        }
        for(ProductState s : productStates.values()) {
            for(String letter : letters) {
                Long transition1 = s.first.getIndexTransitions().get(letter);
                Long transition2 = s.second.getIndexTransitions().get(letter);
                s.getStateTransitions().put(letter, productStates.get(transition1, transition2));
            }
        }
    }

    public ProductState getInitialState() { return initialState; }

    public void setInitialState(ProductState initialState) { this.initialState = initialState; }

    public String[] getLetters() { return letters; }

    public void setLetters(String[] letters) { this.letters = letters; }

}
