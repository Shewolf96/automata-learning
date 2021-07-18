package test.java.services;

import automata.TargetAutomaton;
import automata.Teacher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ParseAutomataService {

    private static JSONParser parser = new JSONParser();

    public static TargetAutomaton parseTargetAutomaton(String jsonFileName) {
        try {

            Object obj = parser.parse(new FileReader("src/main/java/test/resources/automata/" + jsonFileName));

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject automata = (JSONObject) jsonObject.get("automata");
            JSONArray alphabet = (JSONArray) automata.get("alphabet");
            JSONArray states = (JSONArray) automata.get("states");
            Long iniitialState = (Long) automata.get("initialState");

            return new TargetAutomaton(automata, alphabet, states, iniitialState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Teacher getTeacher(String jsonFileName) {
        return new Teacher(parseTargetAutomaton(jsonFileName));
    }
}
