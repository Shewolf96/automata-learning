package automata;

import org.codehaus.plexus.util.StringUtils;

public class Pair {

    private String letter;
    private Long stateId;

    public Pair(String letter, Long stateId) {
        this.letter = letter;
        this.stateId = stateId;
    }

    public String getLetter() { return letter; }

    public void setLetter(String letter) { this.letter = letter; }

    public Long getStateId() { return stateId; }

    public void setStateId(Long stateId) { this.stateId = stateId; }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) o;
        return stateId == pair.stateId
                && StringUtils.equals(letter, pair.letter);
    }
}
