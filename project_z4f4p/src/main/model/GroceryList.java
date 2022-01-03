package model;

// Constructor and methods for GroceryList

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents Grocery List with a list of grocery objects
public class GroceryList implements Writable {
    private final List<Grocery> groceries;

    // EFFECTS: constructs GroceryList with an empty list of groceries
    public GroceryList() {
        groceries = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds Grocery to this GroceryList
    public void addGrocery(Grocery grocery) {
        EventLog.getInstance().logEvent(new Event("Added " + grocery.toString() + " to Grocery List"));
        groceries.add(grocery);
    }

    // EFFECTS: returns an unmodifiable list of groceries in this GroceryList
    public List<Grocery> getGroceries() {
        return Collections.unmodifiableList(groceries);
    }

    // EFFECTS: returns grocery at index
    public Grocery getGrocery(int i) {
        return groceries.get(i);
    }

    // EFFECTS: returns number of groceries in this GroceryList
    public int numGroceries() {
        return groceries.size();
    }

    // EFFECTS: makes JSON file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("groceries", itemsToJson());
        return json;
    }

    // EFFECTS: prints groceries to file
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Grocery g : groceries) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: removes the first occurrence of the given grocery
    public void remove(Grocery g) {
        EventLog.getInstance().logEvent(new Event("Removed " + g.toString() + " from Grocery List"));
        groceries.remove(g);
    }
}
