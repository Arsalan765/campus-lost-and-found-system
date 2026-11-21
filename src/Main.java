
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Step 6: Load saved items when application starts
        try {
            FileManager.loadItems();

        } catch (java.io.IOException | ClassNotFoundException exception) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Load Error");
            alert.setHeaderText("Could not load saved items");
            alert.setContentText(exception.getMessage());

            alert.showAndWait();
        }

        // Open Dashboard
        Dashboard dashboard = new Dashboard();
        dashboard.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
