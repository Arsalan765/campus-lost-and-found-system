
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "items.dat";

    // Save all items to a file
    public static void saveItems() throws IOException {

        ArrayList<Item> items =
                new ArrayList<Item>(Item.getItemList());

        ObjectOutputStream output =
                new ObjectOutputStream(
                        new FileOutputStream(FILE_NAME));

        try {
            output.writeObject(items);
        } finally {
            output.close();
        }
    }

    // Load saved items from a file
    public static void loadItems()
            throws IOException, ClassNotFoundException {

        java.io.File file = new java.io.File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        ObjectInputStream input =
                new ObjectInputStream(
                        new FileInputStream(FILE_NAME));

        try {
            Object data = input.readObject();

            if (!(data instanceof ArrayList)) {
                throw new IOException("Invalid saved data format.");
            }

            ArrayList<?> savedItems = (ArrayList<?>) data;
            ArrayList<Item> loadedItems = new ArrayList<Item>();

            for (Object object : savedItems) {

                if (!(object instanceof Item)) {
                    throw new IOException("Invalid item in saved file.");
                }

                loadedItems.add((Item) object);
            }

            Item.getItemList().setAll(loadedItems);

        } finally {
            input.close();
        }
    }
}
