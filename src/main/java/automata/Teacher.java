package automata;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

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
                return AuxiliaryFunctions.getDivergingWord(productAutomaton, initialState, cycle);
        }
        return new InfiniteWordGenerator();
    }

    private Integer getLoopIndexUpperBound(InfiniteWordGenerator infiniteWord) {
        int N = targetAutomaton.getSize();
        int cycleLength = infiniteWord.getV().length;
        return infiniteWord.getW().length + 2 * N * cycleLength;
    }

    private List<StateLetterPair> getRun(InfiniteWordGenerator infiniteWord, int loopIndexUpperBound) {
        String [] prefix = infiniteWord.getPrefix(Long.valueOf(loopIndexUpperBound + 1));
        return targetAutomaton.getLetterStateRun(prefix);
    }

    private Integer getLoopSize(InfiniteWordGenerator infiniteWord, List<StateLetterPair> run, int loopIndexUpperBound) {
        int loopSize = infiniteWord.getV().length;
        while (!run.subList(loopIndexUpperBound - loopSize + 1, loopIndexUpperBound + 1).equals(run.subList(loopIndexUpperBound - 2 * loopSize + 1, loopIndexUpperBound - loopSize + 1)))
            loopSize += infiniteWord.getV().length;
        return loopSize;
    }

    private Integer getLoopIndex(List<StateLetterPair> run, int loopSize, int loopIndexUpperBound) {
        int loopIndex = loopIndexUpperBound - 2 * loopSize;
        while (loopIndex >= 0 && run.subList(loopIndex, loopIndex + loopSize).equals(run.subList(loopIndex + loopSize, loopIndex + 2 * loopSize))) {
            loopIndex --;
        }
        return loopIndex + 1;
    }

    public Integer loopIndexQuery(InfiniteWordGenerator infiniteWord)
    {
        int loopIndexUpperBound = getLoopIndexUpperBound(infiniteWord);
        List<StateLetterPair> run = getRun(infiniteWord, loopIndexUpperBound);
        int loopSize = getLoopSize(infiniteWord, run, loopIndexUpperBound);
        return getLoopIndex(run, loopSize, loopIndexUpperBound);
    }

    public Long loopIndexQuery(String[] prefix, InfiniteWordGenerator infiniteWord)
    {
        String [] newPrefix = Stream.concat(
                Arrays.stream(prefix),
                Arrays.stream(infiniteWord.getW()))
                .toArray(String[]::new);
        InfiniteWordGenerator infWordWithPrefix = new InfiniteWordGenerator(newPrefix, infiniteWord.getV());
        int loopIndexUpperBound = getLoopIndexUpperBound(infWordWithPrefix);
        List<StateLetterPair> run = getRun(infWordWithPrefix, loopIndexUpperBound);
        int loopSize = getLoopSize(infWordWithPrefix, run, loopIndexUpperBound);
        return Long.valueOf(Math.max(0, getLoopIndex(run, loopSize, loopIndexUpperBound) - prefix.length));
    }

    public Boolean membershipQuery(InfiniteWordGenerator infiniteWord)
    {
        int loopIndexUpperBound = getLoopIndexUpperBound(infiniteWord);
        List<StateLetterPair> run = getRun(infiniteWord, loopIndexUpperBound);
        int loopSize = getLoopSize(infiniteWord, run, loopIndexUpperBound);
        int loopIndex = getLoopIndex(run, loopSize, loopIndexUpperBound);
        List<StateLetterPair> loopRun = run.subList(loopIndex, loopIndex + loopSize);
        for (StateLetterPair p : loopRun) {
            if (targetAutomaton.getStates().get(p.getStateId()).isAccepting())
                return true;
        }
        return false;
    }

    public Boolean membershipQuery(String[] prefix, InfiniteWordGenerator infiniteWord) {

        String [] newPrefix = Stream.concat(
                Arrays.stream(prefix),
                Arrays.stream(infiniteWord.getW()))
                .toArray(String[]::new);
        InfiniteWordGenerator infWordWithPrefix = new InfiniteWordGenerator(newPrefix, infiniteWord.getV());
        return membershipQuery(infWordWithPrefix);
    }

    public List<String> getLetters() {
        return List.of(targetAutomaton.letters);
    }

}
