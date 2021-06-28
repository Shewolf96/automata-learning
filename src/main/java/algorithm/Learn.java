package algorithm;

import automata.TargetAutomaton;

public class Learn {

    TargetAutomaton targetAutomaton;

    public Learn(TargetAutomaton targetAutomaton) {
        this.targetAutomaton = targetAutomaton;
    }

    //tutaj w zasadzie filozofii nie ma żadnej, jak napiszesz inne rzeczy, to nic ciekawego się już nie dzieje
    //ew. gdzieś trzeba stworzyć ten podział/modularność, żeby oddzielić LA od TA

//procedure LEARN (T)
//
//    C := ∅
//    A := GENERATE AUTOMATON (C, T)
//        while T. EQUIVALENCE (A) 6 = true do
//            (w,v) := T. EQUIVALENCE (A)
//    C := SATURATE (w, v, C, T)
//    A := GENERATE AUTOMATON (C, T)
//    return A

}
