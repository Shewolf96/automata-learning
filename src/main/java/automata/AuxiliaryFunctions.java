package automata;

import org.codehaus.plexus.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AuxiliaryFunctions {

//    public static List<State> getReachableStates(Automaton automaton) {
//        List<State> reachableStates = new LinkedList<>();
//        dfs(automaton, automaton.getInitialState(), reachableStates);
//        for(State s : reachableStates)
//            s.setVisited(false);
//        return reachableStates;
//    }
//
//    public static void dfs(Automaton automaton, State currentState, List<State> reachableStates) {
//        if(currentState.isVisited()) return;
//        currentState.setVisited(true);
//        reachableStates.add(currentState);
//        for (State s : currentState.getDescendants()) {
//            dfs(automaton, s, reachableStates);
//        }
//    }

    public static List<ProductState> getReachableStates(P<Automaton, Automaton> productAutomaton) {
        List<ProductState> reachableStates = new LinkedList<>();
        ProductState initialStatePair = new ProductState(productAutomaton.first.getInitialState(), productAutomaton.second.getInitialState());
        dfs(productAutomaton, initialStatePair, reachableStates);
        for(ProductState s : reachableStates) {
            s.setVisited(false);//po co to???
        }
        return reachableStates;
    }

    public static void dfs(P<Automaton, Automaton> productAutomaton, ProductState currentState, List<ProductState> reachableStates) {
        if(currentState.isVisited()) return;
        currentState.setVisited(true);
        reachableStates.add(currentState);
        for (Map.Entry<String, ProductState> descendant : currentState.getDescendants().entrySet()) {
            descendant.getValue().first.setPredecessor(new Pair(descendant.getKey(), currentState.first.getId()));
            descendant.getValue().second.setPredecessor(new Pair(descendant.getKey(), currentState.second.getId()));
            dfs(productAutomaton, descendant.getValue(), reachableStates);
        }
    }

//    public static Boolean checkAllCycles(Automaton automaton, State initialState, State currentState, Stack<Pair> currentCycle) {
//        currentState.setVisited(true);
//        for(Map.Entry<String, State> transition : initialState.getStateTransitions().entrySet()) {
//            String letter = transition.getKey();
//            State nextState = transition.getValue();
//
//            if(nextState == initialState) {
//                if (!checkCycle(automaton, currentCycle)) {
//                    return false;
//                } nextState.setVisited(true);
//                //we have a cycle in stack
//                //check now the path in second automaton if there is any accepting state - if there is, pop nextState from the stack and continue
//                //in other case - return false (and you can create the counterexample from what's on the stack)
//            }
//
//            if(nextState == currentState || nextState.isVisited()) continue;
//
//            currentCycle.push(new Pair(letter, nextState.getId()));
//            if(!checkAllCycles(automaton, initialState, nextState, currentCycle)) return false;
//        }
//        return true;
//    }

    public static Boolean checkAllCycles(P<Automaton, Automaton> productAutomaton, ProductState initialState, ProductState currentState, Stack<String> currentCycle) {
        currentState.first.setVisited(true);
        currentState.second.setVisited(true);
        for(Map.Entry<String, State> transition : currentState.first.getStateTransitions().entrySet()) {
            String letter = transition.getKey();
            ProductState nextState = new ProductState(transition.getValue(),
                    currentState.second.getStateTransitions().get(letter));

            if(nextState == initialState) {
                if (!checkCycle(productAutomaton, initialState.second, currentCycle)) {
                    return false;
                }
            }

            if(nextState == currentState || (nextState.first.isVisited() && nextState.first.isVisited())) continue;

            currentCycle.push(letter);
            if(!checkAllCycles(productAutomaton, initialState, nextState, currentCycle)) return false;
        }
        currentCycle.pop();
        return true;
    }

//    public static Boolean checkCycle(Automaton automaton, Stack<Pair> cycle) {
//        return true;
//    }

    public static Boolean checkCycle(P<Automaton, Automaton> productAutomaton, State initialState, Stack<String> cycle) {
        State currentState = initialState;
        for(String letter : cycle) {//tylko w jakiej kolejnosic iteruje sie po stosie???
            if(currentState.isAccepting()) return true;
            currentState = currentState.getStateTransitions().get(letter);
        }
        return false;
    }

    public static InfiniteWordGenerator getDivergingWord(Automaton automaton, State currentState, Stack<String> cycle) {
        List<String> reversedPrefix = new LinkedList<>();
        List<String> reversedCycle = cycle.stream().collect(Collectors.toList());
        while(currentState != automaton.getInitialState()) {
            reversedPrefix.add(currentState.getPredecessor().getLetter());
        }

        Collections.reverse(reversedCycle);
        Collections.reverse(reversedPrefix);
        return new InfiniteWordGenerator(reversedPrefix, reversedCycle);
    }


}
