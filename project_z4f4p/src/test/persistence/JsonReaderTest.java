package persistence;

import model.Grocery;
import model.GroceryList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/DNE.json");
        try {
            GroceryList wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGroceryList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGroceryList.json");
        try {
            GroceryList gl = reader.read();
            assertEquals(0, gl.numGroceries());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGroceryList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGroceryList.json");
        try {
            GroceryList wr = reader.read();
            List<Grocery> groceries = wr.getGroceries();
            assertEquals(3, groceries.size());
            checkGrocery("1", 1, groceries.get(0));
            checkGrocery("2", 2, groceries.get(1));
            checkGrocery("3", 3, groceries.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
