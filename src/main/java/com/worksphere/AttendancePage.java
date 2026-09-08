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
import com.worksphere.controller.AttendanceController;
import com.worksphere.model.AttendanceRecord;

import java.time.LocalDate;

public class AttendancePage {

        public static void show(Stage stage, ObservableList<AttendanceRecord> attendanceRecords) {
                stage.setTitle("WorkSphere - Attendance Management");

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
                VBox mainContent = createMainContent(stage, attendanceRecords);
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
                                createMenuItem("💰", "Salary", false, () -> {
                                        SalaryManagementPage.show(stage, attendanceRecords);
                                }),
                                createMenuItem("📊", "Dashboard", false, () -> {
                                }),
                                createMenuItem("📄", "Documents", false, () -> {
                                }),
                                createMenuItem("💼", "Employees", false, () -> {
                                }),
                                createMenuItem("📅", "Attendance", true, () -> {
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

        private static VBox createMainContent(Stage stage, ObservableList<AttendanceRecord> attendanceRecords) {
                VBox content = new VBox(20);
                content.setPadding(new Insets(30, 40, 30, 40));

                // Header
                HBox header = createHeader();

                // Title Section
                HBox titleSection = createTitleSection();

                // Filter and Action Buttons
                HBox actionBar = createActionBar();

                // Table and Calendar Section
                HBox tableSection = createTableSection(attendanceRecords);

                content.getChildren().addAll(header, titleSection, actionBar, tableSection);
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
                Label title = new Label("Attendance Management");
                title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                Label subtitle = new Label("track employee attendance");
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
                searchField.setPromptText("Search by name/id");
                searchField.setPrefWidth(250);
                searchField.setStyle(
                                "-fx-background-color: white; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-padding: 10px;");

                ComboBox<String> filterBox = new ComboBox<>();
                filterBox.setPromptText("Filter by status");
                filterBox.getItems().addAll("All", "Present", "Absent", "Late");
                filterBox.setPrefWidth(180);
                filterBox.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Button markAttendanceBtn = new Button("Mark Attendance");
                markAttendanceBtn.setStyle(
                                "-fx-background-color: #00d4aa; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 12px 20px; -fx-font-weight: bold; -fx-cursor: hand;");

                Button exportBtn = new Button("Export Report");
                exportBtn.setStyle(
                                "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 12px 20px; -fx-font-weight: bold; -fx-cursor: hand;");

                actionBar.getChildren().addAll(searchField, filterBox, spacer, markAttendanceBtn, exportBtn);
                return actionBar;
        }

        private static HBox createTableSection(ObservableList<AttendanceRecord> attendanceRecords) {
                HBox tableSection = new HBox(20);
                tableSection.setAlignment(Pos.TOP_LEFT);

                // Table
                VBox tableBox = new VBox(10);
                tableBox.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 25px;");
                HBox.setHgrow(tableBox, Priority.ALWAYS);

                Label tableTitle = new Label("Employee Attendance Records");
                tableTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #374151;");

                TableView<AttendanceRecord> tableView = new TableView<>();
                tableView.setPrefHeight(400);
                tableView.setStyle("-fx-background-color: transparent;");

                TableColumn<AttendanceRecord, String> colName = new TableColumn<>("Employee Name");
                colName.setCellValueFactory(data -> data.getValue().getEmployeeNameProperty());
                colName.setPrefWidth(250);

                TableColumn<AttendanceRecord, String> colTimeIn = new TableColumn<>("Time In");
                colTimeIn.setCellValueFactory(data -> data.getValue().getTimeInProperty());
                colTimeIn.setPrefWidth(150);

                TableColumn<AttendanceRecord, String> colTimeOut = new TableColumn<>("Time Out");
                colTimeOut.setCellValueFactory(data -> data.getValue().getTimeOutProperty());
                colTimeOut.setPrefWidth(150);

                tableView.getColumns().addAll(colName, colTimeIn, colTimeOut);
                tableView.setItems(attendanceRecords);

                tableBox.getChildren().addAll(tableTitle, tableView);

                // Calendar Section
                VBox calendarBox = new VBox(15);
                calendarBox.setPrefWidth(300);
                calendarBox.setAlignment(Pos.TOP_CENTER);
                calendarBox.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 25px;");

                Label calendarLabel = new Label("Select Date");
                calendarLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #374151;");

                DatePicker calendar = new DatePicker(LocalDate.now());
                calendar.setPrefWidth(250);
                calendar.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

                // Stats Section
                VBox statsBox = new VBox(10);
                statsBox.setPadding(new Insets(15, 0, 0, 0));

                HBox presentBox = createStatItem("Present Today", "12", "#00d4aa");
                HBox absentBox = createStatItem("Absent Today", "3", "#ff4d4d");
                HBox lateBox = createStatItem("Late Arrivals", "2", "#ffa500");

                statsBox.getChildren().addAll(presentBox, absentBox, lateBox);

                calendarBox.getChildren().addAll(calendarLabel, calendar, statsBox);

                // Initialize controller
                new AttendanceController(tableView, calendar, attendanceRecords);

                tableSection.getChildren().addAll(tableBox, calendarBox);
                return tableSection;
        }

        private static HBox createStatItem(String label, String value, String color) {
                HBox statBox = new HBox(10);
                statBox.setAlignment(Pos.CENTER_LEFT);
                statBox.setPadding(new Insets(10));
                statBox.setStyle("-fx-background-color: " + color + "20; -fx-background-radius: 8px;");

                VBox textBox = new VBox(2);
                Label statLabel = new Label(label);
                statLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
                Label statValue = new Label(value);
                statValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
                textBox.getChildren().addAll(statValue, statLabel);

                statBox.getChildren().add(textBox);
                return statBox;
        }
}