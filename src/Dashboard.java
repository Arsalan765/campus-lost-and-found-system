
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Dashboard {

    private static final String NAVY = "#14263D";
    private static final String BLUE = "#2563EB";
    private static final String BACKGROUND = "#F5F7FB";
    private static final String TEXT = "#172B4D";
    private static final String MUTED = "#64748B";

    public void show(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BACKGROUND + ";");

        // ======================================
        // LEFT SIDEBAR
        // ======================================

        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(28, 18, 25, 18));
        sidebar.setPrefWidth(235);
        sidebar.setMinWidth(235);
        sidebar.setStyle("-fx-background-color: " + NAVY + ";");

        // Logo area
        Rectangle logoShape = new Rectangle(38, 38);
        logoShape.setArcWidth(12);
        logoShape.setArcHeight(12);
        logoShape.setFill(Color.web(BLUE));

        Label logoLetter = new Label("C");
        logoLetter.setStyle(
                "-fx-text-fill: white;"
                + "-fx-font-size: 22px;"
                + "-fx-font-weight: bold;"
        );

        javafx.scene.layout.StackPane logo =
                new javafx.scene.layout.StackPane();

        logo.getChildren().addAll(logoShape, logoLetter);

        Label appName = new Label("Campus Lost");
        appName.setStyle(
                "-fx-text-fill: white;"
                + "-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
        );

        Label appSubtitle = new Label("AND FOUND");
        appSubtitle.setStyle(
                "-fx-text-fill: #AFC1D8;"
                + "-fx-font-size: 10px;"
                + "-fx-font-weight: bold;"
        );

        VBox brandText = new VBox(2);
        brandText.getChildren().addAll(appName, appSubtitle);

        HBox brand = new HBox(12);
        brand.setAlignment(Pos.CENTER_LEFT);
        brand.getChildren().addAll(logo, brandText);

        Label menuTitle = new Label("MAIN MENU");
        menuTitle.setStyle(
                "-fx-text-fill: #8DA4BE;"
                + "-fx-font-size: 11px;"
                + "-fx-font-weight: bold;"
        );

        VBox.setMargin(menuTitle, new Insets(30, 0, 8, 12));

        // Navigation buttons
        Button dashboardButton = createNavButton(
                "Dashboard", "D", true
        );

        Button reportButton = createNavButton(
                "Report an Item", "+", false
        );

        Button searchButton = createNavButton(
                "Search Items", "S", false
        );

        Button reminderButton = createNavButton(
                "Item Reminder", "R", false
        );

        Button exitButton = createNavButton(
                "Exit Application", "X", false
        );

        // Navigation events
        dashboardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Dashboard().show(stage);
            }
        });

        reportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ReportItem().show(stage);
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new SearchItems().show(stage);
            }
        });

        reminderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ReminderPage().show(stage);
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        Region sidebarSpace = new Region();
        VBox.setVgrow(sidebarSpace, Priority.ALWAYS);

        Label sidebarFooter = new Label("CAMPUS MANAGEMENT SYSTEM");
        sidebarFooter.setWrapText(true);
        sidebarFooter.setStyle(
                "-fx-text-fill: #8DA4BE;"
                + "-fx-font-size: 10px;"
        );

        sidebar.getChildren().addAll(
                brand,
                menuTitle,
                dashboardButton,
                reportButton,
                searchButton,
                reminderButton,
                sidebarSpace,
                exitButton,
                sidebarFooter
        );

        // ======================================
        // CALCULATE STATISTICS
        // ======================================

        int totalCount = Item.getItemList().size();
        int lostCount = 0;
        int foundCount = 0;
        int returnedCount = 0;

        for (Item item : Item.getItemList()) {

            if (item.getType().equals("Lost")) {
                lostCount++;
            }

            if (item.getType().equals("Found")) {
                foundCount++;
            }

            if (item.getStatus().equals("Returned")) {
                returnedCount++;
            }
        }

        // ======================================
        // MAIN CONTENT
        // ======================================

        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(30, 35, 30, 35));

        // Header
        Label pageTitle = new Label("Dashboard Overview");
        pageTitle.setStyle(
                "-fx-font-size: 29px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: " + TEXT + ";"
        );

        Label welcomeText = new Label(
                "Welcome back! Here's what's happening on campus."
        );
        welcomeText.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-text-fill: " + MUTED + ";"
        );

        VBox headerText = new VBox(7);
        headerText.getChildren().addAll(pageTitle, welcomeText);

        String currentDate = LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd MMM yyyy")
        );

        Label dateLabel = new Label(currentDate);
        dateLabel.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
                + "-fx-background-color: white;"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 10;"
                + "-fx-padding: 12 16 12 16;"
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(
                headerText,
                headerSpacer,
                dateLabel
        );

        // ======================================
        // STATISTICS CARDS
        // ======================================

        HBox statistics = new HBox(16);

        VBox totalCard = createStatCard(
                "TOTAL ITEMS",
                String.valueOf(totalCount),
                "All reported items",
                "#2563EB",
                "T"
        );

        VBox lostCard = createStatCard(
                "LOST ITEMS",
                String.valueOf(lostCount),
                "Reported as lost",
                "#F59E0B",
                "L"
        );

        VBox foundCard = createStatCard(
                "FOUND ITEMS",
                String.valueOf(foundCount),
                "Reported as found",
                "#0D9488",
                "F"
        );

        VBox returnedCard = createStatCard(
                "RETURNED ITEMS",
                String.valueOf(returnedCount),
                "Successfully returned",
                "#16A34A",
                "R"
        );

        HBox.setHgrow(totalCard, Priority.ALWAYS);
        HBox.setHgrow(lostCard, Priority.ALWAYS);
        HBox.setHgrow(foundCard, Priority.ALWAYS);
        HBox.setHgrow(returnedCard, Priority.ALWAYS);

        statistics.getChildren().addAll(
                totalCard,
                lostCard,
                foundCard,
                returnedCard
        );

        // ======================================
        // QUICK ACTIONS
        // ======================================

        Label quickTitle = new Label("Quick Actions");
        quickTitle.setStyle(
                "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: " + TEXT + ";"
        );

        Label quickSubtitle = new Label(
                "Access the most frequently used features."
        );
        quickSubtitle.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
        );

        VBox quickHeader = new VBox(5);
        quickHeader.getChildren().addAll(
                quickTitle, quickSubtitle
        );

        Button reportAction = createActionButton(
                "Report an Item",
                "Submit a lost or found item",
                "#2563EB"
        );

        Button searchAction = createActionButton(
                "Search Items",
                "Find reported belongings",
                "#0D9488"
        );

        Button reminderAction = createActionButton(
                "Item Reminder",
                "Check unreturned items",
                "#7C3AED"
        );

        reportAction.setOnAction(reportButton.getOnAction());
        searchAction.setOnAction(searchButton.getOnAction());
        reminderAction.setOnAction(reminderButton.getOnAction());

        HBox actionButtons = new HBox(15);

        HBox.setHgrow(reportAction, Priority.ALWAYS);
        HBox.setHgrow(searchAction, Priority.ALWAYS);
        HBox.setHgrow(reminderAction, Priority.ALWAYS);

        actionButtons.getChildren().addAll(
                reportAction,
                searchAction,
                reminderAction
        );

        VBox quickSection = new VBox(15);
        quickSection.getChildren().addAll(
                quickHeader,
                actionButtons
        );

        // ======================================
        // RECENT ACTIVITY TABLE
        // ======================================

        Label recentTitle = new Label("Recent Activity");
        recentTitle.setStyle(
                "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: " + TEXT + ";"
        );

        Label recentSubtitle = new Label(
                "The five most recently reported items."
        );
        recentSubtitle.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
        );

        VBox recentHeader = new VBox(5);
        recentHeader.getChildren().addAll(
                recentTitle, recentSubtitle
        );

        TableView<Item> recentTable = new TableView<Item>();
        recentTable.setPrefHeight(245);
        recentTable.setPlaceholder(
                new Label("No items have been reported yet.")
        );

        TableColumn<Item, String> nameColumn =
                new TableColumn<Item, String>("Item Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("name")
        );

        TableColumn<Item, String> categoryColumn =
                new TableColumn<Item, String>("Category");

        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("category")
        );

        TableColumn<Item, String> typeColumn =
                new TableColumn<Item, String>("Type");

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("type")
        );

        TableColumn<Item, String> dateColumn =
                new TableColumn<Item, String>("Date");

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("date")
        );

        TableColumn<Item, String> statusColumn =
                new TableColumn<Item, String>("Status");

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("status")
        );

        recentTable.getColumns().addAll(
                nameColumn,
                categoryColumn,
                typeColumn,
                dateColumn,
                statusColumn
        );

        recentTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        // Copy items and reverse the reporting order
        ArrayList<Item> recentItems =
                new ArrayList<Item>(Item.getItemList());

        Collections.reverse(recentItems);

        ObservableList<Item> latestFive =
                FXCollections.observableArrayList();

        for (int i = 0; i < recentItems.size() && i < 5; i++) {
            latestFive.add(recentItems.get(i));
        }

        recentTable.setItems(latestFive);

        VBox recentSection = new VBox(15);
        recentSection.setPadding(new Insets(22));

        recentSection.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 14;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 14;"
        );

        recentSection.getChildren().addAll(
                recentHeader,
                recentTable
        );

        // ======================================
        // ASSEMBLE DASHBOARD
        // ======================================

        mainContent.getChildren().addAll(
                header,
                statistics,
                quickSection,
                recentSection
        );

        javafx.scene.control.ScrollPane scrollPane =
                new javafx.scene.control.ScrollPane(mainContent);

        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: " + BACKGROUND + ";"
                + "-fx-background-color: transparent;"
        );

        root.setLeft(sidebar);
        root.setCenter(scrollPane);

      
Scene scene = new Scene(root, 1000, 650);

stage.setTitle("Campus Lost and Found - Dashboard");
stage.setScene(scene);

stage.setMinWidth(850);
stage.setMinHeight(550);

stage.setWidth(1000);
stage.setHeight(650);

stage.centerOnScreen();
stage.show();

    }

    // ======================================
    // CREATE SIDEBAR BUTTON
    // ======================================

    private Button createNavButton(
            String text, String symbol, boolean active) {

        Button button = new Button(symbol + "     " + text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(44);
        button.setAlignment(Pos.CENTER_LEFT);

        String normalStyle =
                "-fx-background-color: transparent;"
                + "-fx-text-fill: #D5E2F0;"
                + "-fx-font-size: 14px;"
                + "-fx-background-radius: 9;"
                + "-fx-padding: 10 15 10 15;"
                + "-fx-cursor: hand;";

        String activeStyle =
                "-fx-background-color: #2563EB;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 9;"
                + "-fx-padding: 10 15 10 15;"
                + "-fx-cursor: hand;";

        if (active) {
            button.setStyle(activeStyle);
        } else {
            button.setStyle(normalStyle);

            button.setOnMouseEntered(
                    new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    button.setStyle(
                            "-fx-background-color: #243D5B;"
                            + "-fx-text-fill: white;"
                            + "-fx-font-size: 14px;"
                            + "-fx-background-radius: 9;"
                            + "-fx-padding: 10 15 10 15;"
                            + "-fx-cursor: hand;"
                    );
                }
            });

            button.setOnMouseExited(
                    new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    button.setStyle(normalStyle);
                }
            });
        }

        return button;
    }

    // ======================================
    // CREATE STATISTICS CARD
    // ======================================

    private VBox createStatCard(
            String title,
            String number,
            String description,
            String accentColor,
            String symbol) {

        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setMinWidth(0);
        card.setMaxWidth(Double.MAX_VALUE);

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 14;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 14;"
        );

        Circle iconCircle = new Circle(18);
        iconCircle.setFill(Color.web(accentColor));

        Label iconLabel = new Label(symbol);
        iconLabel.setStyle(
                "-fx-text-fill: white;"
                + "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
        );

        javafx.scene.layout.StackPane icon =
                new javafx.scene.layout.StackPane();

        icon.getChildren().addAll(iconCircle, iconLabel);

        Label cardTitle = new Label(title);
        cardTitle.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 11px;"
                + "-fx-font-weight: bold;"
        );

        Label cardNumber = new Label(number);
        cardNumber.setStyle(
                "-fx-text-fill: " + TEXT + ";"
                + "-fx-font-size: 31px;"
                + "-fx-font-weight: bold;"
        );

        Label cardDescription = new Label(description);
        cardDescription.setWrapText(true);
        cardDescription.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 11px;"
        );

        card.getChildren().addAll(
                icon,
                cardTitle,
                cardNumber,
                cardDescription
        );

        return card;
    }

    // ======================================
    // CREATE QUICK ACTION BUTTON
    // ======================================

    private Button createActionButton(
            String title,
            String description,
            String backgroundColor) {

        Button button = new Button(
                title + "\n" + description
        );

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(90);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setWrapText(true);

        button.setStyle(
                "-fx-background-color: " + backgroundColor + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 16 20 16 20;"
                + "-fx-cursor: hand;"
        );

        return button;
    }
}
