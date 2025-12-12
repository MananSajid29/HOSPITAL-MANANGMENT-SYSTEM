package com.example.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class StartUp extends Stage {

    public StartUp() {
        BorderPane startup = new BorderPane();
        startup.setStyle("-fx-background-color: rgba(36,156,20,0.31);"+
               "-fx-background-image: url(startup.jpg);"+
                "-fx-background-size: 100% 100%;");

        Image logo = new Image("Hlogo.png");
        this.getIcons().add(logo);
        Scene sc = new Scene(startup,700,500, Color.OLIVE);
        this.setTitle("HOSPITAL MANAGMENT SYSTEM");
        this.setResizable(false);
        this.setScene(sc);

          ImageView Startlogo = new ImageView(new Image("mainlogo.png"));
         Startlogo.setPreserveRatio(true);
         Startlogo.setFitWidth(450);
         Startlogo.setFitHeight(350);
        startup.setTop(Startlogo);
        BorderPane.setAlignment(Startlogo,Pos.TOP_CENTER);


        ProgressBar load = new ProgressBar(0);
        load.setPrefHeight(50);
        load.setPrefWidth(250);
        load.setPadding(new Insets(0,0,30,0));
        load.setStyle(
                "-fx-accent: #184b60; " +       // color of the filled part
                        "-fx-control-inner-background: rgba(63,133,14,0.66);" // background track
        );

        startup.setBottom(load);
        BorderPane.setAlignment(load, Pos.BOTTOM_CENTER);
        this.show();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(load.progressProperty(), 0.0)),
                new KeyFrame(Duration.seconds(3), new KeyValue(load.progressProperty(), 1.0))
        );

        timeline.setOnFinished(e -> {
            // Close splash and open login page
            this.close();
            new LoginPage().show();
        });

        timeline.play();

    }
}

