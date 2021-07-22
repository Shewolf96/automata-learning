package automata;

import com.google.inject.internal.util.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class InfiniteWordGenerator {

    private String [] w;
    private String [] v;

    public InfiniteWordGenerator() {
    }

    public InfiniteWordGenerator(String [] w, String [] v) {
        this.w = w;
        this.v = v;
    }

    public InfiniteWordGenerator(List<String> w, List<String> v) {
        this.w = w.toArray(new String[0]);
        this.v = v.toArray(new String[0]);
    }

    public InfiniteWordGenerator(String w, String v) {
        this.w = w.split(",|;");
        this.v = v.split(",|;");
    }

    public String [] getPrefix(Long prefix) {
        return Stream.concat(Stream.of(w), Stream.generate(this::getVAsList).flatMap(Collection::stream))
                .limit(prefix).toArray(String[]::new);
    }

    public String [] getW() { return w; }

    public void setW(String[] w) { this.w = w; }

    public String [] getV() { return v; }

    public void setV(String[] v) { this.v = v; }

    public List<String> getWAsList() { return Lists.newArrayList(w); }

    public List<String> getVAsList() { return Lists.newArrayList(v); }

    public Boolean isEmpty() { return (v == null || v.length == 0); }

    public Integer length() { return w.length + v.length; }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof InfiniteWordGenerator)) {
            return false;
        }
        InfiniteWordGenerator infiniteWord = (InfiniteWordGenerator) o;
        return Arrays.equals(infiniteWord.getW(), this.getW()) && Arrays.equals(infiniteWord.getV(), this.getV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(w), Arrays.hashCode(v));
    }
}
