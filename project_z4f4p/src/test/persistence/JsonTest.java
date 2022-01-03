package persistence;

import model.Grocery;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGrocery(String name, int quantity, Grocery grocery) {
        assertEquals(name, grocery.getName());
        assertEquals(quantity, grocery.getQuantity());
    }
}
