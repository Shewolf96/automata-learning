package automata;

import com.google.inject.internal.util.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfiniteWordGenerator {

//    public Pair<String [], String []> infiniteWord;
    private String [] w;
    private String [] v;

    public InfiniteWordGenerator() {
    }

    public InfiniteWordGenerator(String [] w, String [] v) {
        this.w = w;
        this.v = v;
    }

    public String [] getPrefix(Long prefix) {
        return Stream.concat(Stream.of(w), Stream.generate(this::getVAsList).flatMap(Collection::stream))
                .limit(prefix).toArray(String[]::new);
    }

    public String [] getW() {
        return w;
    }

    public void setW(String[] w) {
        this.w = w;
    }

    public String [] getV() {
        return v;
    }

    public void setV(String[] v) {
        this.v = v;
    }

    public List<String> getWAsList() {
        return Lists.newArrayList(w);
    }

    public List<String> getVAsList() {
        return Lists.newArrayList(v);
    }
}