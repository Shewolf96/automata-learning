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
            transition.getValue().setPredecessor(new P(transition.getKey(), currentState));
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
//        currentState.first.setVisited(true);
//        currentState.second.setVisited(true);//??
        currentState.setVisited(true);
//        for(Map.Entry<String, State> transition : currentState.first.getStateTransitions().entrySet()) {
        for(Map.Entry<String, ProductState> transition : currentState.getStateTransitions().entrySet()) {
            String letter = transition.getKey();
            ProductState nextState = transition.getValue();
//            ProductState nextState = new ProductState(transition.getValue(),
//                    currentState.second.getStateTransitions().get(letter));
            if(nextState == initialState) {
                if (!checkCycle(productAutomaton, initialState.second, currentCycle)) {
                    return false;
                }
                continue;
            }

            if(nextState == currentState || nextState.isVisited()) continue;

            currentCycle.push(letter);
            if(!checkAllCycles(productAutomaton, initialState, nextState, currentCycle)) return false;
        }
        if(!currentCycle.empty()) currentCycle.pop();
        return true;
    }

    public static Boolean checkCycle(ProductAutomaton productAutomaton, State initialState, Stack<String> cycle) {
        State currentState = initialState;
        if(currentState.isAccepting()) return true;
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
