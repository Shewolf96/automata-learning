package algorithm;

public class Saturate {


    //+ na pewno pomocnicza funkcja licząca domknięcie zbioru słów nieskończonych

//procedure SATURATE (x, y, C, T)
//    li := T. LOOPINDEX (x,y)
//    (w, v) := (xy ω )[1,li], (xy ω )[li+1,li+ LENGTH (v)]
//    i := LENGTH (w)+ LENGTH (v)+1
//    repeat
//        C’ := C ∪ cl ({(wv ω [1, i], c) | (, c) ∈ cl (C∪{(w, v)})}
//        Ac := GENERATE AUTOMATON (C)
//        class acc := ACCEPTING (Ac, EVAL (Ac,( wv) ω [1, i]))
//        i := i+1
//    until class acc = T. VALUE (w,v)
//    return C’

}
