package automata;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

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

    public static Boolean checkAllCycles(Automaton automaton, State initialState, State currentState, Stack<Pair> currentCycle) {
        currentState.setVisited(true);
        for(Map.Entry<String, State> transition : initialState.getStateTransitions().entrySet()) {
            String letter = transition.getKey();
            State nextState = transition.getValue();

            if(nextState == initialState) {
                if (!checkCycle(automaton, currentCycle)) {
                    return false;
                } nextState.setVisited(true);
                //we have a cycle in stack
                //check now the path in second automaton if there is any accepting state - if there is, pop nextState from the stack and continue
                //in other case - return false (and you can create the counterexample from what's on the stack)
            }

            if(nextState == currentState || nextState.isVisited()) continue;

            currentCycle.push(new Pair(letter, nextState.getId()));
            if(!checkAllCycles(automaton, initialState, nextState, currentCycle)) return false;
        }
        return true;
    }

    public static Boolean checkAllCycles(P<Automaton, Automaton> productAutomaton, P<State, State> initialState, P<State, State> currentState, Stack<String> currentCycle) {
        currentState.first.setVisited(true);
        currentState.second.setVisited(true);
        for(Map.Entry<String, State> transition : initialState.first.getStateTransitions().entrySet()) {
            String letter = transition.getKey();
            P<State, State> nextState = new P(transition.getValue(),
                    currentState.second.getStateTransitions().get(letter));

            if(nextState == initialState) {
                if (!checkCycle2(productAutomaton, initialState.second, currentCycle)) {
                    return false;
                }
                nextState.first.setVisited(true);
                nextState.second.setVisited(true);
            }

            if(nextState == currentState || (nextState.first.isVisited() && nextState.first.isVisited())) continue;

            currentCycle.push(letter);
            if(!checkAllCycles(productAutomaton, initialState, nextState, currentCycle)) return false;
        }
        return true;
    }

    public static Boolean checkCycle(Automaton automaton, Stack<Pair> cycle) {
        return true;
    }

    public static Boolean checkCycle2(P<Automaton, Automaton> productAutomaton, State initialState, Stack<String> cycle) {
        State currentState = initialState;
        for(String letter : cycle) {//tylko w jakiej kolejnosic iteruje sie po stosie???
            if(currentState.isAccepting()) return true;
            currentState = currentState.getStateTransitions().get(letter);
        }
        return false;
    }




}
