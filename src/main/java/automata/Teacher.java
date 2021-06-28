package automata;

import java.util.List;
import java.util.Stack;

public class Teacher {

    private TargetAutomaton targetAutomaton;

    public Teacher(TargetAutomaton targetAutomaton) {
        this.targetAutomaton = targetAutomaton;
    }

    public InfiniteWordGenerator equivalenceQuery(LearningAutomaton learningAutomaton)
    {
        InfiniteWordGenerator differingWord = automataInclusion(targetAutomaton, learningAutomaton);
        if(differingWord.isEmpty()) differingWord = automataInclusion(learningAutomaton, targetAutomaton);
        return differingWord;
    }

    private InfiniteWordGenerator automataInclusion(Automaton automaton1, Automaton automaton2) {
        ProductAutomaton productAutomaton = new ProductAutomaton(automaton1, automaton2);
        List<ProductState> reachableProductStates = AuxiliaryFunctions.getReachableStates(productAutomaton);

        for(ProductState initialState : reachableProductStates) {
            reachableProductStates.stream().forEach(s -> s.setVisited(false));
            Stack cycle = new Stack<>();
            if(initialState.onlyFirstAccepting() && !AuxiliaryFunctions.checkAllCycles(initialState, initialState, cycle))
                return AuxiliaryFunctions.getDivergingWord(productAutomaton, initialState, cycle);//todo: make sure that in (w,v) v is the shortest possible word
        }
        return new InfiniteWordGenerator();
    }

    private Integer getLoopIndexUpperBound(InfiniteWordGenerator infiniteWord) {
        int N = targetAutomaton.getSize();
        int cycleLength = infiniteWord.getV().length;
        return infiniteWord.getW().length + 2 * N * cycleLength;
    }

    private List<Pair> getRun(InfiniteWordGenerator infiniteWord, int loopIndexUpperBound) {
        String [] prefix = infiniteWord.getPrefix(Long.valueOf(loopIndexUpperBound));
        return targetAutomaton.getLetterStateRun(prefix);
    }

    private Integer getLoopSize(InfiniteWordGenerator infiniteWord, List<Pair> run, int loopIndexUpperBound) {
        int loopSize = infiniteWord.getV().length;
        while (!run.subList(loopIndexUpperBound - loopSize, loopIndexUpperBound).equals(run.subList(loopIndexUpperBound - 2 * loopSize, loopIndexUpperBound - loopSize)))
            loopSize += infiniteWord.getV().length;
        return loopSize;
    }

    private Integer getLoopIndex(List<Pair> run, int loopSize) {
        int loopIndex = 0;
        while (!run.subList(loopIndex, loopIndex + loopSize).equals(run.subList(loopIndex + loopSize, loopIndex + 2 * loopSize))) {
            loopIndex += 1;
        }
        return loopIndex;
    }

    public Integer loopIndexQuery(InfiniteWordGenerator infiniteWord)
    {
        int loopIndexUpperBound = getLoopIndexUpperBound(infiniteWord);
        List<Pair> run = getRun(infiniteWord, loopIndexUpperBound);
        int loopSize = getLoopSize(infiniteWord, run, loopIndexUpperBound);
        return getLoopIndex(run, loopSize);//todo: what about loop starting straight away? We get result 1 (maybe should be 0?)
    }

    public Boolean membershipQuery(InfiniteWordGenerator infiniteWord)
    {
        int loopIndexUpperBound = getLoopIndexUpperBound(infiniteWord);
        List<Pair> run = getRun(infiniteWord, loopIndexUpperBound);
        int loopSize = getLoopSize(infiniteWord, run, loopIndexUpperBound);
        int loopIndex = getLoopIndex(run, loopSize);
        List<Pair> loopRun = run.subList(loopIndex, loopIndex + loopSize);
        for (Pair p : loopRun) {
            if (targetAutomaton.getStates().get(p.getStateId()).isAccepting())
                return true;
        }
        return false;
    }

}
