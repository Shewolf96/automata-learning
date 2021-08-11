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

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject automata = (JSONObject) jsonObject.get("automata");
            JSONArray alphabet = (JSONArray) automata.get("alphabet");
            JSONArray states = (JSONArray) automata.get("states");
            Long iniitialState = (Long) automata.get("initialState");

            TargetAutomaton targetAutomaton = new TargetAutomaton(automata, alphabet, states, iniitialState);
            Teacher teacher = new Teacher(targetAutomaton);

            LearningAutomaton LA = Algorithm.learn(targetAutomaton);
            System.out.println(LA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
