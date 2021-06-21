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

    public static List<ProductState> getReachableStates(ProductAutomaton productAutomaton) {
        List<ProductState> reachableStates = new LinkedList<>();
        dfs(productAutomaton, productAutomaton.initialState, reachableStates);
        for(ProductState s : reachableStates) {
            s.setVisited(false);
            s.setReachable(true);
        }
        return reachableStates;
    }

    public static void dfs(ProductAutomaton productAutomaton, ProductState currentState, List<ProductState> reachableStates) {
        if(currentState.isVisited()) return;
        currentState.setVisited(true);
        reachableStates.add(currentState);
        for (Map.Entry<String, ProductState> transition : currentState.getStateTransitions().entrySet()) {
            ProductState nextState = transition.getValue();
            if(nextState.getPredecessor() == null) nextState.setPredecessor(new P(transition.getKey(), currentState));
//            transition.getValue().setPredecessor(new P(transition.getKey(), currentState));
            dfs(productAutomaton, transition.getValue(), reachableStates);
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

    public static Boolean checkAllCycles(ProductAutomaton productAutomaton, ProductState initialState, ProductState currentState, Stack<String> currentCycle) {
        currentState.setVisited(true);
        for(Map.Entry<String, ProductState> transition : currentState.getStateTransitions().entrySet()) {
            String letter = transition.getKey();
            ProductState nextState = transition.getValue();
            if(nextState == initialState) {
                currentCycle.push(letter);
                if (!checkCycle(productAutomaton, initialState.second, currentCycle)) {
                    return false;
                }
                currentCycle.pop();
                continue;
            }

            if(nextState == currentState || nextState.isVisited()) continue;

            currentCycle.push(letter);
            if(!checkAllCycles(productAutomaton, initialState, nextState, currentCycle)) return false;
        }
        if(!currentCycle.empty()) currentCycle.pop();
        return true;
        //        productAutomaton.productStates.values().stream().filter(p -> p.isReachable()).collect(Collectors.toList())
    }

    public static Boolean checkCycle(ProductAutomaton productAutomaton, State initialState, Stack<String> cycle) {
        State currentState = initialState;
        for(String letter : cycle) {
            if(currentState.isAccepting()) return true;
            currentState = currentState.getStateTransitions().get(letter);
        }
        return false;
    }

    public static InfiniteWordGenerator getDivergingWord(Automaton automaton, ProductState currentState, Stack<String> cycle) {
        List<String> reversedPrefix = new LinkedList<>();
        while(currentState.first != automaton.getInitialState()) {
            reversedPrefix.add(currentState.getPredecessor().first);
            currentState = currentState.getPredecessor().second;
        }
        Collections.reverse(reversedPrefix);
        return new InfiniteWordGenerator(reversedPrefix, cycle.stream().collect(Collectors.toList()));
    }


}
