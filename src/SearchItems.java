
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchItems {

    private TableView<Item> table;
    private TextField searchField;
    private ComboBox<String> categoryBox;
    private ComboBox<String> typeBox;

    public void show(Stage stage) {

        // Page title
        Label title = new Label("Search Items");
        title.setStyle(
                "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
        );

        VBox header = new VBox(title);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1e3a5f;");

        // Search field
        searchField = new TextField();
        searchField.setPromptText("Search by item name...");
        searchField.setPrefWidth(220);

        // Category filter
        categoryBox = new ComboBox<String>();

        categoryBox.getItems().addAll(
                "All Categories",
                "Electronics",
                "Books and Documents",
                "Personal Items",
                "Clothing",
                "Accessories",
                "Other"
        );

        categoryBox.setValue("All Categories");

        // Item type filter
        typeBox = new ComboBox<String>();

        typeBox.getItems().addAll(
                "All Types",
                "Lost",
                "Found"
        );

        typeBox.setValue("All Types");

        // Search button
        Button searchButton = new Button("Search");

        // Clear button
        Button clearButton = new Button("Clear");

        HBox searchBox = new HBox(12);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(
                searchField,
                categoryBox,
                typeBox,
                searchButton,
                clearButton
        );

        // Create table
        table = new TableView<Item>();

        // Item name column
        TableColumn<Item, String> nameColumn =
                new TableColumn<Item, String>("Item Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("name")
        );

        // Category column
        TableColumn<Item, String> categoryColumn =
                new TableColumn<Item, String>("Category");

        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("category")
        );

        // Location column
        TableColumn<Item, String> locationColumn =
                new TableColumn<Item, String>("Location");

        locationColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("location")
        );

        // Date column
        TableColumn<Item, String> dateColumn =
                new TableColumn<Item, String>("Date");

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("date")
        );

        // Type column
        TableColumn<Item, String> typeColumn =
                new TableColumn<Item, String>("Type");

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("type")
        );

        // Status column
        TableColumn<Item, String> statusColumn =
                new TableColumn<Item, String>("Status");

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("status")
        );

        // Add columns to table
        table.getColumns().addAll(
                nameColumn,
                categoryColumn,
                locationColumn,
                dateColumn,
                typeColumn,
                statusColumn
        );

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        table.setPrefHeight(400);

        // Display all reported items
        table.setItems(Item.getItemList());

        // Bottom buttons
        Button detailsButton = new Button("View Details");
        Button returnedButton = new Button("Mark as Returned");
        Button backButton = new Button("Back to Dashboard");

        detailsButton.setStyle(
                "-fx-background-color: #2563eb;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        returnedButton.setStyle(
                "-fx-background-color: #16a34a;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        backButton.setStyle(
                "-fx-background-color: #1e3a5f;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        buttonBox.getChildren().addAll(
                detailsButton,
                returnedButton,
                backButton
        );

        // Search button event
        searchButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                searchItems();
            }
        });

        // Clear button event
        clearButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                searchField.clear();

                categoryBox.setValue("All Categories");
                typeBox.setValue("All Types");

                table.setItems(Item.getItemList());
            }
        });

        // View Details button event
        detailsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Item selectedItem =
                        table.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {

                    showMessage(
                            Alert.AlertType.WARNING,
                            "No Item Selected",
                            "Please select an item first."
                    );

                } else {

                    ItemDetails details = new ItemDetails();

                    details.show(stage, selectedItem);
                }
            }
        });

        // Mark as Returned button event
        returnedButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Item selectedItem =
                        table.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {

                    showMessage(
                            Alert.AlertType.WARNING,
                            "No Item Selected",
                            "Please select an item first."
                    );

                } else {

                   
String previousStatus = selectedItem.getStatus();

selectedItem.setStatus("Returned");

try {
    FileManager.saveItems();

    table.refresh();

} catch (java.io.IOException exception) {

    selectedItem.setStatus(previousStatus);
    table.refresh();

    showMessage(
            Alert.AlertType.ERROR,
            "Save Error",
            "Could not save the status change: "
            + exception.getMessage()
    );

    return;
}


                    showMessage(
                            Alert.AlertType.INFORMATION,
                            "Success",
                            "Item marked as returned successfully."
                    );
                }
            }
        });

        // Back to Dashboard event
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Dashboard dashboard = new Dashboard();

                dashboard.show(stage);
            }
        });

        // Main content
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));

        content.getChildren().addAll(
                searchBox,
                table,
                buttonBox
        );

        // Main layout
        BorderPane root = new BorderPane();

        root.setTop(header);
        root.setCenter(content);

        root.setStyle("-fx-background-color: #f4f6f9;");

        // Scene
        Scene scene = new Scene(root, 1050, 650);

        stage.setTitle("Campus Lost and Found - Search Items");
        stage.setScene(scene);
        stage.show();
    }

    // Search and filter items
    private void searchItems() {

        String keyword =
                searchField.getText().trim().toLowerCase();

        String category = categoryBox.getValue();
        String type = typeBox.getValue();

        ObservableList<Item> results =
                FXCollections.observableArrayList();

        for (Item item : Item.getItemList()) {

            boolean matchesName =
                    item.getName().toLowerCase().contains(keyword);

            boolean matchesCategory =
                    category.equals("All Categories")
                    || item.getCategory().equals(category);

            boolean matchesType =
                    type.equals("All Types")
                    || item.getType().equals(type);

            if (matchesName && matchesCategory && matchesType) {

                results.add(item);
            }
        }

        table.setItems(results);

        if (results.isEmpty()) {

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Search Results",
                    "No matching items were found."
            );
        }
    }

    // Display alert messages
    private void showMessage(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
