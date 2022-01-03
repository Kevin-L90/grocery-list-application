package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroceryListTest {
    GroceryList groceries;
    Grocery a;
    Grocery b;

    @BeforeEach
    void runBefore() {
        groceries = new GroceryList();
        a = new Grocery("a", 5);
        b = new Grocery("b", 3);
    }

    @Test
    void testAddGrocery() {
        groceries.addGrocery(a);
        assertEquals(a, groceries.getGrocery(0));
    }

    @Test
    void testGetGroceries() {
        groceries.addGrocery(a);
        groceries.addGrocery(b);
        assertEquals(2, groceries.getGroceries().size());
    }

    @Test
    void testGetGrocery() {
        groceries.addGrocery(a);
        groceries.addGrocery(b);
        assertEquals(a, groceries.getGrocery(0));
        assertEquals(b, groceries.getGrocery(1));
    }

    @Test
    void testNumGrocery() {
        groceries.addGrocery(a);
        groceries.addGrocery(b);
        assertEquals(2, groceries.numGroceries());
    }

    @Test
    void testRemove() {
        groceries.addGrocery(a);
        groceries.addGrocery(b);
        groceries.addGrocery(a);
        groceries.remove(b);
        assertEquals(2, groceries.getGroceries().size());
        assertEquals(a, groceries.getGrocery(0));
        assertEquals(a, groceries.getGrocery(1));
    }
}

