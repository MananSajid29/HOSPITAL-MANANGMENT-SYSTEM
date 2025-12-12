package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPage extends Stage {



        public LoginPage() {

            Pane fp = new Pane();
            fp.setStyle("-fx-background-image: url('mainbg.jpg'); " +
                    "-fx-background-size: 100% 100%; " +
                    "-fx-background-repeat: no-repeat;");
            fp.setOpacity(0.7);
            Scene sc = new Scene(fp, 450, 500, Color.GREEN);
            this.setScene(sc);


            this.setTitle("Login Page");
            Image im = new Image("login.png");
            this.getIcons().add(im);
            this.setResizable(false);


            Text l1 = new Text("Username");
            l1.setFill(Color.BEIGE);
            l1.setX(50);
            l1.setY(160);
            l1.setFont(Font.font("Arial Black", FontWeight.BOLD, 20));
            fp.getChildren().add(l1);
            TextField tf = new TextField();
            tf.setLayoutX(190);
            tf.setLayoutY(137);
            tf.setFont(Font.font(14));
            fp.getChildren().add(tf);


            Text t2 = new Text("Password");
            t2.setX(50);
            t2.setY(230);
            t2.setFill(Color.BEIGE);
            t2.setFont(Font.font("Arial Black", FontWeight.BOLD, 20));
            fp.getChildren().add(t2);

            PasswordField p1 = new PasswordField();
            p1.setLayoutX(190);
            p1.setLayoutY(208);
            p1.setFont(Font.font(14));
            fp.getChildren().add(p1);

            Button b1 = new Button();
            ImageView imv = new ImageView(new Image("logo.png"));
            imv.setFitWidth(200);
            imv.setFitHeight(100);
            imv.setPreserveRatio(true);
            b1.setGraphic(imv);
            b1.setPrefSize(80, 15);
            b1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            b1.setTextFill(Color.GREEN);
            b1.setBackground(null);
            b1.setLayoutX(210);
            b1.setLayoutY(240);
            fp.getChildren().add(b1);
            b1.setOnAction(event -> {
                String username = tf.getText();
                String password = p1.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    Alert a1 = new Alert(Alert.AlertType.ERROR, "Please input values");
                    a1.setHeaderText(null);
                    a1.showAndWait();
                    return;
                }

                boolean loginSuccess = false;

                try {
                    BufferedReader bfReader = new BufferedReader(new FileReader("data.txt"));
                    String line;
                    while ((line = bfReader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 2) {
                            String userid = parts[0].trim();
                            String userpas = parts[1].trim();

                            if (username.equals(userid) && password.equals(userpas)) {
                                loginSuccess = true;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (loginSuccess) {
                    Alert login = new Alert(Alert.AlertType.INFORMATION, "Welcome " + username);
                    login.setHeaderText(null);
                    login.showAndWait();
                    this.close();
                    new HomePage();
                } else {
                    Alert loginerror = new Alert(Alert.AlertType.ERROR, "Wrong Credentials");
                    loginerror.setHeaderText(null);
                    loginerror.showAndWait();
                }
            });

            Text companyname = new Text(" Made by\nNexo-Tech");
            companyname.setFill(Color.WHITE);
            companyname.setLayoutX(180);
            companyname.setLayoutY(400);
            companyname.setFont(Font.font("Times new Roman", FontWeight.NORMAL, FontPosture.ITALIC, 16));
            fp.getChildren().add(companyname);

            VBox glassbox = new VBox();
            glassbox.setLayoutX(40);
            glassbox.setLayoutY(90);
            glassbox.setPrefHeight(340);
            glassbox.setPrefWidth(350);
            glassbox.setPadding(new Insets(20));
            glassbox.setStyle("-fx-background-color: rgba(136,216,107,0.35);" +
                    "-fx-background-radius: 20;" +
                    "-fx-border-radius: 20;" +
                    "-fx-border-color: rgba(253,253,250,0.39);" +
                    "-fx-border-width: 1.5;");
            glassbox.setOpacity(0.7);
            fp.getChildren().add(glassbox);
            glassbox.toBack();


            this.show();
        }
    }


