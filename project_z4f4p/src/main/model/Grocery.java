package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a grocery having a name and quantity
public class Grocery implements Writable {
    private final String name;
    private int quantity;

    // EFFECTS: constructs a thingy with a name and category
    public Grocery(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // EFFECTS: returns quantity
    public int getQuantity() {
        return quantity;
    }

    // EFFECTS: returns string representation of this thingy
    public String toString() {
        return quantity + " " + name;
    }

    // EFFECTS: writes grocery to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("quantity", quantity);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: increase quantity by plus
    public void increaseQuantity(int plus) {
        EventLog.getInstance().logEvent(new Event("Increased Quantity of " + this.name + " by " + plus));
        quantity += plus;
    }

    // MODIFIES: this
    // EFFECTS: decrease quantity by minus and return true. Set quantity to 1 if quantity < 0 and return false
    public boolean decreaseQuantity(int minus) {
        quantity -= minus;
        if (quantity <= 0) {
            EventLog.getInstance().logEvent(new Event("Set Quantity of " + this.name + " to 1"));
            quantity = 1;
            return false;
        }
        EventLog.getInstance().logEvent(new Event("Decreased Quantity of " + this.name + " by " + minus));
        return true;
    }
}
