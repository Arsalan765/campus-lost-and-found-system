
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class ReportItem {

    public void show(Stage stage) {

        // Main layout
        BorderPane root = new BorderPane();

        root.setStyle("-fx-background-color: #F4F6FA;");

        // ==============================
        // TOP SECTION
        // ==============================

        VBox header = new VBox(8);

        header.setPadding(new Insets(25));

        header.setStyle("-fx-background-color: #172C49;");

        Label title = new Label("Report a Lost or Found Item");

        title.setStyle(
                "-fx-font-size: 25px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
        );

        Label subtitle = new Label(
                "Please provide the details of the item."
        );

        subtitle.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-text-fill: #AFC4DD;"
        );

        header.getChildren().addAll(title, subtitle);

        // ==============================
        // FORM COMPONENTS
        // ==============================

        Label nameLabel = new Label("Item Name:");

        TextField nameField = new TextField();

        nameField.setPromptText("Example: Laptop Charger");

        Label categoryLabel = new Label("Category:");

        ComboBox<String> categoryBox = new ComboBox<>();

        categoryBox.getItems().addAll(
                "Electronics",
                "Books and Documents",
                "Personal Items",
                "Clothing",
                "Accessories",
                "Other"
        );

        categoryBox.setPromptText("Select a category");

        categoryBox.setMaxWidth(Double.MAX_VALUE);

        Label descriptionLabel = new Label("Description:");

        TextArea descriptionField = new TextArea();

        descriptionField.setPromptText(
                "Describe the item, its color, brand, or other details..."
        );

        descriptionField.setPrefRowCount(3);

        descriptionField.setWrapText(true);

        Label locationLabel = new Label("Location:");

        TextField locationField = new TextField();

        locationField.setPromptText("Example: University Library");

        Label dateLabel = new Label("Date:");

        DatePicker datePicker = new DatePicker();

        datePicker.setValue(LocalDate.now());

        datePicker.setMaxWidth(Double.MAX_VALUE);

        // ==============================
        // LOST OR FOUND RADIO BUTTONS
        // ==============================

        Label typeLabel = new Label("Item Type:");

        RadioButton lostRadio = new RadioButton("Lost");

        RadioButton foundRadio = new RadioButton("Found");

        ToggleGroup typeGroup = new ToggleGroup();

        lostRadio.setToggleGroup(typeGroup);

        foundRadio.setToggleGroup(typeGroup);

        lostRadio.setSelected(true);

        HBox typeBox = new HBox(25);

        typeBox.getChildren().addAll(
                lostRadio,
                foundRadio
        );

        // ==============================
        // FORM LAYOUT
        // ==============================

        GridPane form = new GridPane();

        form.setHgap(20);

        form.setVgap(18);

        form.setPadding(new Insets(30));

        form.setAlignment(Pos.CENTER);

        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        form.add(categoryLabel, 0, 1);
        form.add(categoryBox, 1, 1);

        form.add(descriptionLabel, 0, 2);
        form.add(descriptionField, 1, 2);

        form.add(locationLabel, 0, 3);
        form.add(locationField, 1, 3);

        form.add(dateLabel, 0, 4);
        form.add(datePicker, 1, 4);

        form.add(typeLabel, 0, 5);
        form.add(typeBox, 1, 5);

        nameField.setPrefWidth(350);

        descriptionField.setPrefWidth(350);

        // ==============================
        // BUTTONS
        // ==============================

        Button submitButton = new Button("Submit Report");

        Button backButton = new Button("Back to Dashboard");

        submitButton.setPrefWidth(150);
        submitButton.setPrefHeight(40);

        backButton.setPrefWidth(170);
        backButton.setPrefHeight(40);

        submitButton.setStyle(
                "-fx-background-color: #315B89;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8;"
                + "-fx-cursor: hand;"
        );

        backButton.setStyle(
                "-fx-background-color: #64748B;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8;"
                + "-fx-cursor: hand;"
        );

        HBox buttonBox = new HBox(15);

        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.setPadding(new Insets(20));

        buttonBox.getChildren().addAll(
                submitButton,
                backButton
        );

        // ==============================
        // SUBMIT BUTTON EVENT
        // ==============================

        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    String itemName = nameField.getText().trim();

                    String category = categoryBox.getValue();

                    String description = descriptionField.getText().trim();

                    String location = locationField.getText().trim();

                    LocalDate date = datePicker.getValue();

                    // Validate item name
                    if (itemName.isEmpty()) {

                        throw new IllegalArgumentException(
                                "Please enter the item name."
                        );
                    }

                    // Validate category
                    if (category == null) {

                        throw new IllegalArgumentException(
                                "Please select an item category."
                        );
                    }

                    // Validate description
                    if (description.isEmpty()) {

                        throw new IllegalArgumentException(
                                "Please enter the item description."
                        );
                    }

                    // Validate location
                    if (location.isEmpty()) {

                        throw new IllegalArgumentException(
                                "Please enter the location."
                        );
                    }

                    // Validate date
                    if (date == null) {

                        throw new IllegalArgumentException(
                                "Please select a date."
                        );
                    }

                    // Get item type
                    String itemType = "";

                    if (lostRadio.isSelected()) {

                        itemType = "Lost";

                    } else if (foundRadio.isSelected()) {

                        itemType = "Found";
                    }

                    if (itemType.isEmpty()) {

                        throw new IllegalArgumentException(
                                "Please select Lost or Found."
                        );
                    }
// Create a new item
Item newItem = new Item(
        itemName,
        category,
        description,
        location,
        date.toString(),
        itemType
);

// Add item to shared list
Item.getItemList().add(newItem);

try {
    FileManager.saveItems();

} catch (java.io.IOException exception) {

    Item.getItemList().remove(newItem);

    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle("Save Error");
    errorAlert.setHeaderText(null);
    errorAlert.setContentText(
            "The item could not be saved: "
            + exception.getMessage()
    );
    errorAlert.showAndWait();

    return;
}

                    // Display success message
                    Alert success = new Alert(
                            Alert.AlertType.INFORMATION
                    );

                    success.setTitle("Report Successful");

                    success.setHeaderText("Item Report Submitted");

                    success.setContentText(
                            "Item Name: " + itemName
                            + "\nCategory: " + category
                            + "\nDescription: " + description
                            + "\nLocation: " + location
                            + "\nDate: " + date
                            + "\nType: " + itemType
                    );

                    success.showAndWait();

                    // Clear form after submission
                    nameField.clear();

                    categoryBox.setValue(null);

                    descriptionField.clear();

                    locationField.clear();

                    datePicker.setValue(LocalDate.now());

                    lostRadio.setSelected(true);

                } catch (IllegalArgumentException exception) {

                    Alert error = new Alert(
                            Alert.AlertType.ERROR
                    );

                    error.setTitle("Invalid Information");

                    error.setHeaderText("Please check your form");

                    error.setContentText(exception.getMessage());

                    error.showAndWait();
                }
            }
        });

        // ==============================
        // BACK BUTTON EVENT
        // ==============================

        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Dashboard dashboard = new Dashboard();

                dashboard.show(stage);
            }
        });

        // ==============================
        // MAIN LAYOUT
        // ==============================

        VBox centerContent = new VBox(15);

        centerContent.setAlignment(Pos.CENTER);

        centerContent.getChildren().addAll(
                form,
                buttonBox
        );

        root.setTop(header);

        root.setCenter(centerContent);

        // ==============================
        // CREATE SCENE
        // ==============================

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("Report an Item - Campus Lost and Found");

        stage.setScene(scene);

        stage.show();
    }
}
