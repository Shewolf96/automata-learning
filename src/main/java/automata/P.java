package automata;

public class P<T1, T2> {
    public T1 first;
    public T2 second;

    public P(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof P)) {
            return false;
        }
        P p = (P) o;
        return p.first.equals(first) && p.second.equals(second);
    }
}
