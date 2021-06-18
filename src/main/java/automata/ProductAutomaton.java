package automata;

import java.util.*;
import org.apache.commons.collections4.map.MultiKeyMap;

public class ProductAutomaton {

    protected Automaton automaton1;
    protected Automaton automaton2;

    protected MultiKeyMap<Long, ProductState> productStates = new MultiKeyMap();

    protected ProductState initialState;
    protected HashSet<String> letters = new HashSet<>();

    public ProductAutomaton(Automaton automaton1, Automaton automaton2) {
        this.automaton1 = automaton1;
        this.automaton2 = automaton2;
        setInitialState(new ProductState(automaton1.initialState, automaton2.initialState));
        setLetters(automaton1.letters);
        initProductStates();
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
                s.getStateTransitions().put(letter, productStates.get(s.first, s.second));
            }
        }

//        productStates.values().forEach(ProductState::setStateTransitions);
    } //teraz jeszcze musisz zmienić getDescendatns
    // no i podmienić w kodzie wszędzie te jakieś tuple automatów na taką klaskę

    public Automaton getAutomaton1() { return automaton1; }

    public void setAutomaton1(Automaton automaton1) { this.automaton1 = automaton1; }

    public Automaton getAutomaton2() { return automaton2; }

    public void setAutomaton2(Automaton automaton2) { this.automaton2 = automaton2; }

    public ProductState getInitialState() { return initialState; }

    public void setInitialState(ProductState initialState) { this.initialState = initialState; }

    public HashSet<String> getLetters() { return letters; }

    public void setLetters(HashSet<String> letters) { this.letters = letters; }

}
