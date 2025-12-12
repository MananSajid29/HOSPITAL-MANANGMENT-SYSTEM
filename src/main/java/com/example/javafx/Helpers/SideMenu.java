package com.example.javafx.Helpers;

import com.example.javafx.*;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class SideMenu extends Stage {

    private VBox sidebar;
    private boolean isOpen = false;

    public SideMenu() {


        sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(220);
        sidebar.setPrefHeight(700);
        sidebar.setStyle("-fx-background-color: rgba(71,126,31,0.84);");


        Button home = createMenuButton("Home");
        home.setOnAction(e -> new HomePage().show());

        Button patients = createMenuButton("Patients");
        patients.setOnAction(e -> new AddPatientPage());

        Button doctors = createMenuButton("Doctors");
        doctors.setOnAction(e -> new DoctorsDetailPage().show());

        Button billing = createMenuButton("Billing");
        billing.setOnAction(e -> new BillingPage().show());

        Button pharmacy = createMenuButton("Pharmacy");
        pharmacy.setOnAction(e -> new PharmacyPage().show());

        Button exit = createMenuButton("LogOut");
        exit.setOnAction(event -> {
            for (Window window : Window.getWindows()) {
                if (window instanceof Stage stage) {
                    // Don't close the login page we just opened
                        stage.close();
                    }
                    new LoginPage().show();
                }


        });


        sidebar.getChildren().addAll(home, patients, doctors, billing, pharmacy, exit);


        sidebar.setTranslateX(-220);


        Scene scene = new Scene(sidebar, 220, 700);
        this.setScene(scene);
        this.setResizable(false);
        this.initStyle(StageStyle.UNDECORATED);
    }

    private Button createMenuButton(String text) {
        Button b = new Button(text);
        b.setPrefWidth(190);
        b.setBackground(null);
        b.setPrefHeight(40);
        b.setStyle("-fx-background-color: rgba(44,110,80,0.84); -fx-text-fill: white; -fx-font-size: 16;");
        b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: rgba(44,110,80,0.84); -fx-text-fill: white;"));
        b.setOnMouseExited(e -> b.setStyle("-fx-background-color: rgba(44,110,80,0.84); -fx-text-fill: white;"));
        return b;
    }


    public void openMenu(double x, double y) {
        this.setX(x);
        this.setY(y);
        this.show();

        TranslateTransition openAnim = new TranslateTransition(Duration.millis(250), sidebar);
        openAnim.setToX(0);
        openAnim.play();

        isOpen = true;
    }

    public void closeMenu() {
        TranslateTransition closeAnim = new TranslateTransition(Duration.millis(250), sidebar);
        closeAnim.setToX(-220);
        closeAnim.setOnFinished(e -> this.hide());
        closeAnim.play();

        isOpen = false;
    }


    public void enableAutoClose() {
        sidebar.setOnMouseExited(e -> {
            if (isOpen) {
                closeMenu();
            }
        });
    }
}
