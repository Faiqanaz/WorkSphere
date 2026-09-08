package com.worksphere;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.worksphere.model.AttendanceRecord;

public class SalaryManagementPage {

    public static void show(Stage stage, ObservableList<AttendanceRecord> attendanceRecords) {
        stage.setTitle("WorkSphere - Salary Management");

        // Create stack pane for layering background
        StackPane stackPane = new StackPane();

        // Background with gradient
        Region background = new Region();
        background.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #4a6fa5 0%, #3d5a7f 50%, #2c4159 100%);");

        // Decorative circles
        Circle circle1 = new Circle(200);
        circle1.setFill(Color.web("#00d4aa"));
        circle1.setOpacity(0.3);
        circle1.setTranslateX(-400);
        circle1.setTranslateY(350);

        Circle circle2 = new Circle(150);
        circle2.setFill(Color.web("#00d4aa"));
        circle2.setOpacity(0.2);
        circle2.setTranslateX(-300);
        circle2.setTranslateY(250);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: transparent;");

        /* ===================== SIDEBAR ===================== */
        VBox sidebar = createSidebar(stage, attendanceRecords);
        root.setLeft(sidebar);

        /* ===================== MAIN CONTENT ===================== */
        VBox mainContent = createMainContent();
        root.setCenter(mainContent);

        // Layer everything
        stackPane.getChildren().addAll(background, circle1, circle2, root);
        StackPane.setAlignment(circle1, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(circle2, Pos.BOTTOM_LEFT);

        Scene scene = new Scene(stackPane, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    private static VBox createSidebar(Stage stage, ObservableList<AttendanceRecord> attendanceRecords) {
        VBox sidebar = new VBox(5);
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #1e2a3a;");
        sidebar.setPadding(new Insets(20, 15, 20, 15));

        // Logo Section
        HBox logo = new HBox(10);
        logo.setAlignment(Pos.CENTER_LEFT);
        Circle logoCircle = new Circle(12);
        logoCircle.setFill(Color.web("#00d4aa"));
        Label logoText = new Label("WorkSphere");
        logoText.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        logo.getChildren().addAll(logoCircle, logoText);
        logo.setPadding(new Insets(0, 0, 30, 0));

        // Menu Items
        VBox menuItems = new VBox(5);
        menuItems.getChildren().addAll(
                createMenuItem("💰", "Salary", true, () -> {
                }),
                createMenuItem("📊", "Dashboard", false, () -> {
                }),
                createMenuItem("📄", "Documents", false, () -> {
                }),
                createMenuItem("💼", "Employees", false, () -> {
                }),
                createMenuItem("📅", "Attendance", false, () -> {
                    AttendancePage.show(stage, attendanceRecords);
                }),
                createMenuItem("👥", "Users", false, () -> {
                }));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        HBox logoutItem = createMenuItem("🚪", "Logout", false, () -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Confirm Logout");
            alert.setContentText("Are you sure you want to logout?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    WorkSphereApp.showLoginPage(stage);
                }
            });
        });

        sidebar.getChildren().addAll(logo, menuItems, spacer, logoutItem);
        return sidebar;
    }

    private static HBox createMenuItem(String icon, String text, boolean active, Runnable action) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(12, 15, 12, 15));
        item.setStyle(active ? "-fx-background-color: #2563eb; -fx-background-radius: 8px; -fx-cursor: hand;"
                : "-fx-background-color: transparent; -fx-background-radius: 8px; -fx-cursor: hand;");

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-text-fill: " + (active ? "white" : "#8b92a7") + "; -fx-font-size: 16px;");

        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-text-fill: " + (active ? "white" : "#8b92a7") + "; -fx-font-size: 14px;");

        item.getChildren().addAll(iconLabel, textLabel);

        item.setOnMouseEntered(e -> {
            if (!active)
                item.setStyle("-fx-background-color: #2a3647; -fx-background-radius: 8px; -fx-cursor: hand;");
        });
        item.setOnMouseExited(e -> {
            if (!active)
                item.setStyle("-fx-background-color: transparent; -fx-background-radius: 8px; -fx-cursor: hand;");
        });

        item.setOnMouseClicked(e -> {
            if (action != null) {
                action.run();
            }
        });

        return item;
    }

    private static VBox createMainContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 40, 30, 40));

        HBox header = createHeader();
        HBox titleSection = createTitleSection();
        HBox actionBar = createActionBar();
        VBox table = createTable();

        content.getChildren().addAll(header, titleSection, actionBar, table);
        return content;
    }

    private static HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_RIGHT);

        Label brand = new Label("BitVerse.ai");
        brand.setStyle("-fx-text-fill: #2563eb; -fx-font-size: 18px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Circle avatar = new Circle(18);
        avatar.setFill(Color.web("#e0e7ff"));

        VBox userInfo = new VBox(2);
        Label userName = new Label("Admin");
        userName.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        Label userRole = new Label("Manager");
        userRole.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b7280;");
        userInfo.getChildren().addAll(userName, userRole);

        HBox userSection = new HBox(10);
        userSection.setAlignment(Pos.CENTER);
        userSection.getChildren().addAll(avatar, userInfo);

        header.getChildren().addAll(spacer, brand, new Label("  "), userSection);
        return header;
    }

    private static HBox createTitleSection() {
        HBox titleSection = new HBox();
        titleSection.setAlignment(Pos.CENTER_LEFT);

        VBox titles = new VBox(5);
        Label title = new Label("Salary Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label subtitle = new Label("office system");
        subtitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        titles.getChildren().addAll(title, subtitle);

        titleSection.getChildren().add(titles);
        return titleSection;
    }

    private static HBox createActionBar() {
        HBox actionBar = new HBox(15);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10, 0, 10, 0));

        TextField searchField = new TextField();
        searchField.setPromptText("Search by name/id/address");
        searchField.setPrefWidth(250);
        searchField.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-padding: 10px;");

        ComboBox<String> filterBox = new ComboBox<>();
        filterBox.setPromptText("Filter by month/team/employees");
        filterBox.setPrefWidth(220);
        filterBox.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button generateBtn = new Button("Generate Salary slip");
        generateBtn.setStyle(
                "-fx-background-color: #00d4aa; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 12px 20px; -fx-font-weight: bold; -fx-cursor: hand;");
        generateBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generate Salary Slip");
            alert.setHeaderText(null);
            alert.setContentText("Salary slip generation initiated!");
            alert.showAndWait();
        });

        Button exportBtn = new Button("Export Report");
        exportBtn.setStyle(
                "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 12px 20px; -fx-font-weight: bold; -fx-cursor: hand;");
        exportBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Report");
            alert.setHeaderText(null);
            alert.setContentText("Report export initiated!");
            alert.showAndWait();
        });

        actionBar.getChildren().addAll(searchField, filterBox, spacer, generateBtn, exportBtn);
        return actionBar;
    }

    private static VBox createTable() {
        VBox tableBox = new VBox();
        tableBox.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 25px;");

        HBox tableHeader = new HBox();
        tableHeader.setStyle(
                "-fx-padding: 15px 0; -fx-border-color: transparent transparent #e5e7eb transparent; -fx-border-width: 0 0 2 0;");
        tableHeader.setSpacing(20);

        Label empLabel = createHeaderLabel("Employee", 200);
        Label attendLabel = createHeaderLabel("Attendance Days", 150);
        Label basicLabel = createHeaderLabel("Basic Salary", 150);
        Label deductLabel = createHeaderLabel("Deductions", 150);
        Label totalLabel = createHeaderLabel("Total Salary", 150);

        tableHeader.getChildren().addAll(empLabel, attendLabel, basicLabel, deductLabel, totalLabel);

        VBox rows = new VBox(8);
        rows.setPadding(new Insets(15, 0, 0, 0));

        String[][] data = {
                { "John Smith", "3/10", "Basic Salary", "Bonus", "Total Salary", "#ff4d4d" },
                { "Jane Doe", "3/10", "Basic Salary", "Bonus", "Dexal Salare", "#ff6b6b" },
                { "Mike Johnson", "3/10", "Basic Salary", "Bonus", "Total Salary", "#ffa500" },
                { "Sarah Williams", "3/10", "Basic Salary", "Bonus", "Total Salary", "#00d4aa" },
                { "Tom Brown", "3/10", "Basic Salary", "Bonus", "Total Salary", "#00d4aa" },
                { "Emily Davis", "3/10", "Basic Salary", "Bonus", "Total Salary", "#2563eb" },
                { "Chris Wilson", "3/10", "Basic Salary", "Bonus", "Total Salary", "#2563eb" }
        };

        for (String[] rowData : data) {
            rows.getChildren().add(createTableRow(rowData));
        }

        tableBox.getChildren().addAll(tableHeader, rows);
        return tableBox;
    }

    private static Label createHeaderLabel(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #374151;");
        return label;
    }

    private static HBox createTableRow(String[] data) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-padding: 15px 0; -fx-border-color: transparent transparent #f3f4f6 transparent; -fx-border-width: 0 0 1 0;");

        Label name = createRowLabel(data[0], 200);
        Label attendance = createRowLabel(data[1], 150);
        Label basic = createRowLabel(data[2], 150);

        HBox deductBadge = new HBox();
        deductBadge.setPrefWidth(150);
        deductBadge.setAlignment(Pos.CENTER_LEFT);
        Label deduct = new Label(data[3]);
        deduct.setStyle("-fx-background-color: " + data[5] + "40; -fx-text-fill: " + data[5]
                + "; -fx-padding: 6px 12px; -fx-background-radius: 6px; -fx-font-size: 12px; -fx-font-weight: bold;");
        deductBadge.getChildren().add(deduct);

        Label total = createRowLabel(data[4], 150);
        total.setStyle(total.getStyle() + "-fx-font-weight: bold; -fx-text-fill: " + data[5] + ";");

        row.getChildren().addAll(name, attendance, basic, deductBadge, total);
        return row;
    }

    private static Label createRowLabel(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #4b5563;");
        return label;
    }
}