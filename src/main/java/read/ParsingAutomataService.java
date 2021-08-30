package read;

import automata.TargetAutomaton;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.FileReader;

public class ParsingAutomataService {

    private static Logger LOGGER = LoggerFactory.getLogger(ParsingAutomataService.class);

    public static TargetAutomaton parseAutomaton(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject automata = (JSONObject) jsonObject.get("automata");
            JSONArray alphabet = (JSONArray) automata.get("alphabet");
            JSONArray states = (JSONArray) automata.get("states");
            Long initialState = (Long) automata.get("initialState");

            TargetAutomaton TA = new TargetAutomaton(automata, alphabet, states, initialState);
            return TA;

        } catch (Exception e) {
            LOGGER.error(e, () -> e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
