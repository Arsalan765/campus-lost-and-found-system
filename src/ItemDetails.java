
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ItemDetails {

    public void show(Stage stage, Item item) {

        // Page title
        Label title = new Label("Item Details");
        title.setStyle(
                "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
        );

        VBox header = new VBox(title);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #1e3a5f;");

        // Display item information
        GridPane detailsGrid = new GridPane();
        detailsGrid.setPadding(new Insets(30));
        detailsGrid.setHgap(25);
        detailsGrid.setVgap(20);

        addDetail(detailsGrid, 0, "Item Name:", item.getName());
        addDetail(detailsGrid, 1, "Category:", item.getCategory());
        addDetail(detailsGrid, 2, "Description:", item.getDescription());
        addDetail(detailsGrid, 3, "Location:", item.getLocation());
        addDetail(detailsGrid, 4, "Date Reported:", item.getDate());
        addDetail(detailsGrid, 5, "Item Type:", item.getType());
        addDetail(detailsGrid, 6, "Status:", item.getStatus());

        // Back button
        Button backButton = new Button("Back to Search Items");
        backButton.setStyle(
                "-fx-background-color: #1e3a5f;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;"
        );

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SearchItems search = new SearchItems();
                search.show(stage);
            }
        });

        VBox content = new VBox(25);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(25));
        content.getChildren().addAll(detailsGrid, backButton);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(content);
        root.setStyle("-fx-background-color: #f4f6f9;");

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("Campus Lost and Found - Item Details");
        stage.setScene(scene);
        stage.show();
    }

    // Helper method to display one detail row
    private void addDetail(
            GridPane grid, int row, String fieldName, String value) {

        Label fieldLabel = new Label(fieldName);
        fieldLabel.setStyle(
                "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #1e3a5f;"
        );

        Label valueLabel = new Label(value);
        valueLabel.setWrapText(true);
        valueLabel.setStyle(
                "-fx-font-size: 16px;"
                + "-fx-text-fill: #333333;"
        );

        grid.add(fieldLabel, 0, row);
        grid.add(valueLabel, 1, row);
    }
}
