package automata;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public interface Automaton {
    LinkedHashMap<Integer, State> States = new LinkedHashMap();
    Integer initialState = 1;
    HashSet<String> letters = new HashSet<>();

    public abstract <B> B foldRight(B z, BiFunction<?, B, B> f);

}
