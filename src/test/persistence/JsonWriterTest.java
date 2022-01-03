package persistence;

import model.Grocery;
import model.GroceryList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GroceryList gl = new GroceryList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGroceryList() {
        try {
            GroceryList gl = new GroceryList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGroceryList.json");
            writer.open();
            writer.write(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGroceryList.json");
            gl = reader.read();
            assertEquals(0, gl.numGroceries());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGroceryList() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("1", 1));
            gl.addGrocery(new Grocery("2", 2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGroceryList.json");
            writer.open();
            writer.write(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGroceryList.json");
            gl = reader.read();
            List<Grocery> groceries = gl.getGroceries();
            assertEquals(2, groceries.size());
            checkGrocery("1", 1, groceries.get(0));
            checkGrocery("2", 2, groceries.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
