package com.example.javafx;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AboutPage extends Stage {

    public AboutPage(){
        Pane Aboutpage = new Pane();
        Aboutpage.setStyle("-fx-background-color: #49490f");
        Scene scene = new Scene(Aboutpage, 1100, 700, Color.GREEN);
        this.setScene(scene);
        this.setTitle(" Nexo Hospital-About Page");

        ImageView main = new ImageView(new Image("startup.jpg"));
        main.setOpacity(0.4);
        main.setFitHeight(700);
        main.setFitWidth(1100);
        Aboutpage.getChildren().add(main);

        this.show();


    }
}
