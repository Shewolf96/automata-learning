package algorithm;

import automata.*;
import read.ParsingAutomataService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParsingAutomataService.parseInput(args);

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/java/test/resources/automata/automata1"));

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject automata = (JSONObject) jsonObject.get("automata");
            JSONArray alphabet = (JSONArray) automata.get("alphabet");
            JSONArray states = (JSONArray) automata.get("states");
            Long iniitialState = (Long) automata.get("initialState");

            TargetAutomaton targetAutomaton = new TargetAutomaton(automata, alphabet, states, iniitialState);
            String[] w = {"a", "b", "a", "b", "b", "c", };
            String[] v = {"b"};
            InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(w,v);
            State s = targetAutomaton.transition(infiniteWord, 10l);
            String [] infiniteWordPrefix = infiniteWord.getPrefix(25l);
            List<Long> run = targetAutomaton.getRun(infiniteWordPrefix);
            List<Pair> run2 = targetAutomaton.getLetterStateRun(infiniteWordPrefix);
            System.out.println(targetAutomaton);
            for(String letter : infiniteWordPrefix) {
                System.out.print(letter + ", ");
            }
            System.out.println("\n" + run);

            Teacher teacher = new Teacher(targetAutomaton);
            int loopIndex = teacher.loopIndexQuery(infiniteWord);
            boolean membership = teacher.membershipQuery(infiniteWord);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        //read input
        //create target automata (TA)
        //create basic learning automata (LA)
        //asking three types of queries
        //implement expanding LA so that it's equivalent to TA
        //Idk, print some message
        //create some tests
        //i elo
}
