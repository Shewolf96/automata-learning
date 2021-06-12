package automata;

import java.util.LinkedList;
import java.util.List;

public class AuxiliaryFunctions {

    public static List<State> getReachableStates(Automaton automaton) {
        List<State> reachableStates = new LinkedList<>();
        dfs(automaton, automaton.getInitialState(), reachableStates);
        for(State s : reachableStates)
            s.setVisited(false);
        return reachableStates;
    }

    public static void dfs(Automaton automaton, State currentState, List<State> reachableStates) {
        if(currentState.isVisited()) return;
        currentState.setVisited(true);
        reachableStates.add(currentState);
        for (State s : currentState.getDescendants()) {
            dfs(automaton, s, reachableStates);
        }
    }

    public static Boolean checkAllCycles(Automaton automaton, State initialState) {
        return null;
    }




}
