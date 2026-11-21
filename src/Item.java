
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private static ObservableList<Item> itemList =
            FXCollections.observableArrayList();

    private String name;
    private String category;
    private String description;
    private String location;
    private String date;
    private String type;
    private String status;

    public Item(String name, String category,
            String description, String location,
            String date, String type) {

        this.name = name;
        this.category = category;
        this.description = description;
        this.location = location;
        this.date = date;
        this.type = type;
        this.status = "Active";
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ObservableList<Item> getItemList() {
        return itemList;
    }
}
