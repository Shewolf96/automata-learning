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
            Object obj = parser.parse(new FileReader("src/main/java/test/resources/automata/automata5.1"));
            Object obj2 = parser.parse(new FileReader("src/main/java/test/resources/automata/automata5.2"));

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
            Teacher teacher = new Teacher(targetAutomaton);

            LearningAutomaton automaton = new LearningAutomaton(automata2, alphabet2, states2, iniitialState2);
            teacher.equivalenceQuery(automaton);

//            Algorithm.learn(targetAutomaton);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
