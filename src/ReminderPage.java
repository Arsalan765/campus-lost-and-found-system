
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReminderPage {

    private Label timerLabel;
    private Label statusLabel;
    private Label activeItemsLabel;

 
private int seconds = 60;
private volatile boolean running = false;
private Thread timerThread;
private int timerVersion = 0;


    public void show(Stage stage) {

        // Page title
        Label title = new Label("Item Reminder");
        title.setStyle(
                "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
        );

        VBox header = new VBox(title);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #1e3a5f;");

        // Timer display
        timerLabel = new Label("01:00");
        timerLabel.setStyle(
                "-fx-font-size: 65px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #2563eb;"
        );

        statusLabel = new Label("Timer is ready.");
        statusLabel.setStyle("-fx-font-size: 16px;");

        activeItemsLabel = new Label();
        activeItemsLabel.setStyle(
                "-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
        );

        updateActiveItems();

        // Buttons
        Button startButton = new Button("Start Reminder");
        Button stopButton = new Button("Stop Reminder");
        Button resetButton = new Button("Reset Timer");
        Button backButton = new Button("Back to Dashboard");

        startButton.setStyle(
                "-fx-background-color: #16a34a;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        stopButton.setStyle(
                "-fx-background-color: #dc2626;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        resetButton.setStyle(
                "-fx-background-color: #2563eb;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        backButton.setStyle(
                "-fx-background-color: #1e3a5f;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20;"
        );

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(
                startButton,
                stopButton,
                resetButton
        );

        VBox content = new VBox(25);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        content.getChildren().addAll(
                activeItemsLabel,
                timerLabel,
                statusLabel,
                buttons,
                backButton
        );

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(content);
        root.setStyle("-fx-background-color: #f4f6f9;");

        Scene scene = new Scene(root, 1000, 650);

        // Start timer
        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                startTimer();
            }
        });

        // Stop timer
        stopButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stopTimer();
            }
        });

        // Reset timer
        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                stopTimer();
                seconds = 60;
                timerLabel.setText("01:00");
                statusLabel.setText("Timer has been reset.");
                updateActiveItems();
            }
        });

        // Back to Dashboard
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                stopTimer();

                Dashboard dashboard = new Dashboard();
                dashboard.show(stage);
            }
        });

        stage.setTitle("Campus Lost and Found - Reminder");
        stage.setScene(scene);
        stage.show();
    }

    // Count items that have not been returned
    private void updateActiveItems() {

        int count = 0;

        for (Item item : Item.getItemList()) {

            if (item.getStatus().equals("Active")) {
                count++;
            }
        }

        activeItemsLabel.setText(
                "Active Items: " + count
        );
    }

    // Start countdown using a background thread

private synchronized void startTimer() {

    if (running || seconds <= 0) {
        return;
    }

    running = true;
    timerVersion++;

    final int currentVersion = timerVersion;

    statusLabel.setText("Reminder is running...");

    timerThread = new Thread(new Runnable() {

        @Override
        public void run() {

            while (running && currentVersion == timerVersion) {

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException exception) {
                    return;
                }

                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {

                        if (!running
                                || currentVersion != timerVersion) {
                            return;
                        }

                        seconds--;

                        int minutes = seconds / 60;
                        int remainingSeconds = seconds % 60;

                        timerLabel.setText(
                                String.format(
                                        "%02d:%02d",
                                        minutes,
                                        remainingSeconds
                                )
                        );

                        if (seconds <= 0) {

                            seconds = 0;
                            running = false;
                            timerVersion++;

                            timerLabel.setText("00:00");

                            updateActiveItems();

                            statusLabel.setText(
                                    "Reminder completed!"
                            );

                            Alert alert = new Alert(
                                    Alert.AlertType.INFORMATION
                            );

                            alert.setTitle("Item Reminder");
                            alert.setHeaderText(
                                    "Time to check your items!"
                            );

                            alert.setContentText(
                                    activeItemsLabel.getText()
                            );

                            alert.show();
                        }
                    }
                });
            }
        }
    });

    timerThread.setDaemon(true);
    timerThread.start();
}


    // Stop the countdown
 
private synchronized void stopTimer() {

    running = false;
    timerVersion++;

    if (timerThread != null) {
        timerThread.interrupt();
        timerThread = null;
    }

    statusLabel.setText("Timer stopped.");
}

}
