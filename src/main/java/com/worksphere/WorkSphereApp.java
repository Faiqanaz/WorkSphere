package com.worksphere;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkSphereApp extends Application {

    @Override
    public void start(Stage stage) {

        // Root container
        StackPane root = new StackPane();
        root.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #667eea, #764ba2);"
        );

        // Main container
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(40));

        // LEFT TEXT SECTION
        VBox leftBox = new VBox(15);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Office\nManagement\nSystem");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button getStarted = new Button("Get Started");
        getStarted.setStyle(
            "-fx-background-color: #4299e1; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-background-radius: 25; -fx-padding: 12 30 12 30; -fx-cursor: hand;"
        );

        leftBox.getChildren().addAll(title, getStarted);
        mainLayout.setLeft(leftBox);

        // LOGIN CARD
        VBox loginCard = new VBox(15);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(20));
        loginCard.setStyle(
            "-fx-background-color: #f7fafc; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 25, 0, 0, 10);"
        );

        Label logo = new Label("Modern");
        logo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setStyle(
            "-fx-background-color: white; -fx-background-radius: 25; -fx-border-color: #e2e8f0; -fx-padding: 12 20 12 20;"
        );

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setStyle(
            "-fx-background-color: white; -fx-background-radius: 25; -fx-border-color: #e2e8f0; -fx-padding: 12 20 12 20;"
        );

        Hyperlink link = new Hyperlink("Input Employee UI");
        link.setStyle("-fx-text-fill: #4299e1; -fx-font-size: 13px;");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle(
            "-fx-background-color: #1a202c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 15 0 15 0;"
        );

        Label role = new Label("admin / employee");
        role.setStyle("-fx-font-size: 13px; -fx-text-fill: #4a5568;");

        loginCard.getChildren().addAll(logo, username, password, link, loginBtn, role);
        mainLayout.setCenter(loginCard);

        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1100, 600);
        stage.setTitle("Office Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
