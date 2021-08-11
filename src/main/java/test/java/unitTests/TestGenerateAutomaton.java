package test.java.unitTests;

import algorithm.GenerateAutomaton;
import automata.InfiniteWordGenerator;
import automata.TargetAutomaton;
import automata.Teacher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileReader;
import java.util.HashSet;

public class TestGenerateAutomaton extends GenerateAutomaton{

    private static JSONParser parser;
    private static Teacher teacher;
    private static HashSet<InfiniteWordGenerator> C;

    public TestGenerateAutomaton() {
        super(C, teacher);
    }

    @BeforeClass
    public static void setup() {
        parser = new JSONParser();

        C = new HashSet<>();
        C.add(new InfiniteWordGenerator(new String [] {}, new String [] {"a", "a"}));
        C.add(new InfiniteWordGenerator(new String [] {"a", "b"}, new String [] {"b"}));
        C.add(new InfiniteWordGenerator(new String [] {"a", "b"}, new String [] {"a"}));
        C.add(new InfiniteWordGenerator(new String [] {}, new String [] {"b"}));
        C.add(new InfiniteWordGenerator(new String [] {"b"}, new String [] {"b", "a"}));

        try {

            Object obj = parser.parse(new FileReader("src/main/java/test/resources/automata/automata3.1"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject automata = (JSONObject) jsonObject.get("automata");
            JSONArray alphabet = (JSONArray) automata.get("alphabet");
            JSONArray states = (JSONArray) automata.get("states");
            Long iniitialState = (Long) automata.get("initialState");

            TargetAutomaton TA = new TargetAutomaton(automata, alphabet, states, iniitialState);
            teacher = new Teacher(TA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TargetAutomaton parseTargetAutomaton(String jsonFileName) {
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

    @Test
    public void testComputeStateFunction() {
//        TargetAutomaton TA = parseTargetAutomaton("automaton6.1");
//        Teacher teacher = new Teacher(TA);
//        GenerateAutomaton GA = new GenerateAutomaton(new HashSet<>(), teacher);

//        StateFunction stateFunction = super.computeStateFunction(new String [] {"a", "b", "b"});
//        Assert.assertTrue(stateFunction.getDescendants().containsKey(""));

    }

    @Test
    public void testStateSet() {
    }

    @Test
    public void testTransition() {
    }

    @Test
    public void testAcceptingStates() {
    }

    @Test
    public void testAddSuccessors() {
    }

}
