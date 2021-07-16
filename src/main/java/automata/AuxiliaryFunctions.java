package automata;

import one.util.streamex.EntryStream;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AuxiliaryFunctions {

    public static List<ProductState> getReachableStates(ProductAutomaton productAutomaton) {
        List<ProductState> reachableStates = new LinkedList<>();
        dfs(productAutomaton.initialState, reachableStates);
        for(ProductState s : reachableStates) {
            s.setVisited(false);
            s.setReachable(true);
        }
        return reachableStates;
    }

    public static void dfs(ProductState currentState, List<ProductState> reachableStates) {
        if(currentState.isVisited()) return;
        currentState.setVisited(true);
        reachableStates.add(currentState);
        for (Map.Entry<String, ProductState> transition : currentState.getStateTransitions().entrySet()) {
            ProductState nextState = transition.getValue();
            if(nextState.getPredecessor() == null) nextState.setPredecessor(new P(transition.getKey(), currentState));
            dfs(transition.getValue(), reachableStates);
        }
    }

    public static Boolean checkAllCycles(ProductState initialState, ProductState currentState, Stack<String> currentCycle) {
        currentState.setVisited(true);
        for(Map.Entry<String, ProductState> transition : currentState.getStateTransitions().entrySet()) {
            String letter = transition.getKey();
            ProductState nextState = transition.getValue();
            if(nextState == initialState) {
                currentCycle.push(letter);
                if (!checkCycle(initialState.second, currentCycle)) {
                    return false;
                }
                currentCycle.pop();
                continue;
            }
            if(nextState == currentState || nextState.isVisited()) continue;

            currentCycle.push(letter);
            if(!checkAllCycles(initialState, nextState, currentCycle)) return false;
        }
        if(!currentCycle.empty()) currentCycle.pop();
        return true;
    }

    public static Boolean checkCycle(State initialState, Stack<String> cycle) {
        State currentState = initialState;
        for(String letter : cycle) {
            if(currentState.isAccepting()) return true;
            currentState = currentState.getStateTransitions().get(letter);
        }
        return false;
    }

    public static InfiniteWordGenerator getDivergingWord(ProductAutomaton automaton, ProductState currentState, Stack<String> cycle) {
        List<String> reversedPrefix = new LinkedList<>();
        while(currentState != automaton.getInitialState()) {
            reversedPrefix.add(currentState.getPredecessor().first);
            currentState = currentState.getPredecessor().second;
        }
        Collections.reverse(reversedPrefix);
        InfiniteWordGenerator divergingWord = new InfiniteWordGenerator(reversedPrefix, cycle.stream().collect(Collectors.toList()));
        return findShortestCycle(divergingWord);
    }

    public static InfiniteWordGenerator findShortestCycle(InfiniteWordGenerator infWord) {
        List<String> v = infWord.getVAsList();
        List<Integer> cycleLengths = EntryStream.of(v)
                .filterValues(letter -> letter.equals(v.get(0)))
                .keys()
                .mapFirst(key -> key + 1)
                .collect(Collectors.toList());

        for(Integer index : cycleLengths) {
            List<String> cycle = v.subList(0, index);
            List<List<String>> sublists = ListUtils.partition(v, index);
            if(sublists.stream().allMatch(sublist -> sublist.equals(cycle)))
                return new InfiniteWordGenerator(infWord.getWAsList(), cycle);
        }
        return infWord;
    }


}
