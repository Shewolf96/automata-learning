package algorithm;

import automata.*;
import read.ParsingAutomataService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        ParsingAutomataService.parseInput(args);

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/java/test/resources/automata/automata6.2"));
            Object obj2 = parser.parse(new FileReader("src/main/java/test/resources/automata/automata6.1"));

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject automata = (JSONObject) jsonObject.get("automata");
            JSONArray alphabet = (JSONArray) automata.get("alphabet");
            JSONArray states = (JSONArray) automata.get("states");
            Long iniitialState = (Long) automata.get("initialState");

            JSONObject jsonObject2 = (JSONObject) obj2;

            JSONObject automata2 = (JSONObject) jsonObject2.get("automata");
            JSONArray alphabet2 = (JSONArray) automata2.get("alphabet");
            JSONArray states2 = (JSONArray) automata2.get("states");
            Long iniitialState2 = (Long) automata2.get("initialState");

            TargetAutomaton targetAutomaton = new TargetAutomaton(automata, alphabet, states, iniitialState);
//            String[] w = {"a", "b", "a", "b", "b", "c", };
//            String[] v = {"b"};
//            InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(w,v);
//            State s = targetAutomaton.transition(infiniteWord, 10l);
//            String [] infiniteWordPrefix = infiniteWord.getPrefix(25l);
//            List<Long> run = targetAutomaton.getRun(infiniteWordPrefix);
//            List<Pair> run2 = targetAutomaton.getLetterStateRun(infiniteWordPrefix);
//            System.out.println(targetAutomaton);
//            for(String letter : infiniteWordPrefix) {
//                System.out.print(letter + ", ");
//            }
//            System.out.println("\n" + run);

            Teacher teacher = new Teacher(targetAutomaton);
//            int loopIndex = teacher.loopIndexQuery(infiniteWord);
//            boolean membership = teacher.membershipQuery(infiniteWord);

            LearningAutomaton automaton = new LearningAutomaton(automata2, alphabet2, states2, iniitialState2);
            teacher.equivalenceQuery(automaton);

            String[] w = {"a"};
            String[] v = {"a"};
            InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(w,v);
            teacher.loopIndexQuery(infiniteWord);

            Algorithm.learn(targetAutomaton);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
