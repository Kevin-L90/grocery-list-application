package persistence;

import model.Grocery;
import model.GroceryList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads GroceryList from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads GroceryList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GroceryList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGroceryList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses GroceryList from JSON object and returns it
    private GroceryList parseGroceryList(JSONObject jsonObject) {
        GroceryList gl = new GroceryList();
        addGroceries(gl, jsonObject);
        return gl;
    }

    // MODIFIES: gl
    // EFFECTS: parses groceries from JSON object and adds them to GroceryList
    private void addGroceries(GroceryList gl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("groceries");
        for (Object json : jsonArray) {
            JSONObject nextGrocery = (JSONObject) json;
            addGrocery(gl, nextGrocery);
        }
    }

    // MODIFIES: gl
    // EFFECTS: parses grocery from JSON object and adds it to GroceryList
    private void addGrocery(GroceryList gl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int quantity = jsonObject.getInt("quantity");
        Grocery grocery = new Grocery(name, quantity);
        gl.addGrocery(grocery);
    }
}
