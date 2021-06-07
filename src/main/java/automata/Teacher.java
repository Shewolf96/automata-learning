package automata;

import java.util.List;

public class Teacher {

    private TargetAutomaton targetAutomaton;

    public Teacher(TargetAutomaton targetAutomaton) {
        this.targetAutomaton = targetAutomaton;
    }

    public InfiniteWordGenerator equivalenceQuery(LearningAutomaton learningAutomaton)
    {
        //to jednak jest easy
        //stan początkowy w jednym i w drugim mamy
        //dla każdego przejścia daną literką (bo to deterministyczne, więc wszystko wiadomo)
        //przyrównujesz sobie kolejne stany
        //no i jak trafisz na jakiś ??
        //tzn gdzieś musisz pamiętać sobie, czy stan jest odwiedzony czy nie (taki bfs będzie)
        //i jaki jest numerek równoważnego stanu
        //i nie musi ich być tyle samo ALE
        //analogiczne stany muszą być albo oba accept albo reject
        //no i zakładamy, że ten learning jest mniejszy równy
        //czyli kilka stanów tego targeta może odpowiadać jednemu - ale chyba wtedy wszystkie muszą być też odpowiednio akceptujące lub nie
        //no i co z przejściami?
        //to chyba i tak jakieś klasy abstrakcji będą
        //w sensie wszystkie przejścia z danej grupy daną literką muszą być do innej klasy abstrakcji (grupy), która odpowiada takiemu stanowi, do którego tąże literką przechodzisz z LA
        //czy coś
        //Tylko nie wiem czy to oznacza, że dwa razy tego bfsa robisz? Czy da się to ogarnąć od razu?
        //Bo teraz to mi się wydaje, że nie wiesz, które stany będą w danej klasie abstrakcji, toteż nie możesz od razu dla każdego z nich sprawdzić jakiegoś przejścia (daną literką)
        //jutro to jeszcze trzeba będzie rozrysować
        //plus porobić testy (też narysuj je sobie wcześniej)
        //no i wreszcie załóż repo na githubie, żeby zapisywać zmiany
        //i....
        //możesz zacząć implementować faktyczny algo ^^^
        //(w którymś momencie trzeba będzie też ten podział na moduły zrobić)

        return new InfiniteWordGenerator();//tu jakiś optional musi być, a nie słowo puste!!
    }

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
