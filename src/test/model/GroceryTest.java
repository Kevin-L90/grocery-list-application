package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroceryTest {
    Grocery grocery;

    @BeforeEach
    void runBefore() {
        grocery = new Grocery("E", 3);
    }

    @Test
    void testGetNamme() {
        assertEquals("E", grocery.getName());
    }

    @Test
    void testGetQuantity() {
        assertEquals(3, grocery.getQuantity());
    }

    @Test
    void testToString() {
        assertEquals("3 E", grocery.toString());
    }

    @Test
    void testIncreaseQuantity() {
        grocery.increaseQuantity(2);
        assertEquals(5, grocery.getQuantity());
    }

    @Test
    void testDecreaseQuantityGreater() {
        grocery.decreaseQuantity(2);
        assertEquals(1, grocery.getQuantity());
    }

    @Test
    void testDecreaseQuantityLess() {
        grocery.decreaseQuantity(5);
        assertEquals(1, grocery.getQuantity());
    }
}
