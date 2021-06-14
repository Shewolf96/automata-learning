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
        List<State> reachableStates = AuxiliaryFunctions.getReachableStates(targetAutomaton);
        for(State initialState : reachableStates) {
            targetAutomaton.getStateCollection().stream().forEach(s -> s.setVisited(false));
            Stack cycle = new Stack<>();
            if(initialState.isAccepting() && !AuxiliaryFunctions.checkAllCycles(targetAutomaton, initialState, initialState, cycle))
                // return false; // .findNextCycle would be cooler
                return new InfiniteWordGenerator();
        }
        //(i wszystko to ofc trzeba zrobić w drugą stronę)
        //teraz funkcja, która bierze te RAStates, TA i automat produktowy... i ...
        //kolejno:
        //  dla każdego RAState: znajduje kolejno wszystkie cykle
        //      dla każdego znalezionego cyklu: (czyli stany + jakimi literami przeszłaś - to nie graf ostatecznie!)
        //          przechodzisz tę ścieżkę w automacie produktowym i jeśli nie znajdziesz żadnego acc na drugiej współrzędnej, to kończysz całe szukanie - automaty nie są równoważne

        return new InfiniteWordGenerator();//tu jakiś optional musi być, a nie słowo puste!!
    }

    // *dokładniejsza myśl, żeby nie uciekła: odkładasz sobie ten potencjalny cykl na stos i jeśli następny wierzchołek wraca do początkowego, to...
    //  przeglądamy ten produktowy automat po tym cyklu (ale nie możesz go stracić jeszcze - tylko ostatni wierzchołek ew. ściągnąć) i szukamy acc, jak nie znajdziemy to zwracamy false (nie równoważne)
    // a jak znajdziemy to zwracamy true - że jest ok i można szukać kolejnego cyklu tam, gdzie skończyliśmy
    // a jak przejrzymy wszystkich sąsiadów i jest ok cały czas (true ze wszystkich wywołań rek), to zwracamy true, że ogólnie ok

    public Integer getLoopIndexUpperBound(InfiniteWordGenerator infiniteWord) {
        int N = targetAutomaton.getSize();
        int cycleLength = infiniteWord.getV().length;
        return infiniteWord.getW().length + 2 * N * cycleLength;
    }

    public List<Pair> getRun(InfiniteWordGenerator infiniteWord, int loopIndexUpperBound) {
        String [] prefix = infiniteWord.getPrefix(Long.valueOf(loopIndexUpperBound));
        return targetAutomaton.getLetterStateRun(prefix);
    }

    public Integer getLoopSize(InfiniteWordGenerator infiniteWord, List<Pair> run, int loopIndexUpperBound) {
        int loopSize = infiniteWord.getV().length;
        while (!run.subList(loopIndexUpperBound - loopSize, loopIndexUpperBound).equals(run.subList(loopIndexUpperBound - 2 * loopSize, loopIndexUpperBound - loopSize)))
            loopSize += infiniteWord.getV().length;
        return loopSize;
    }

    public Integer getLoopIndex(List<Pair> run, int loopSize) {
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
        return getLoopIndex(run, loopSize);
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
