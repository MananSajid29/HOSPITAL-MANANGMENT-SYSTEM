package com.example.javafx;

import com.example.javafx.Helpers.SideMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;

public class HomePage extends Stage{
    Button AddPatientbutton , PatientRecordbutton , BookAppointmentButton , DoctorsDetailButton , BillingButton , PharmacyButton ,AboutButton;

      public HomePage(){
          Pane homepage = new Pane();
          homepage.setStyle("-fx-background-color: #49490f");
          Scene scene = new Scene(homepage,1100,700,Color.GREEN);
          this.setScene(scene);
          this.setResizable(false);
          this.setTitle(" Nexo Hospital-Home Page");

          ImageView main = new ImageView(new Image("startup.jpg"));
          main.setOpacity(0.4);
          main.setFitHeight(700);
          main.setFitWidth(1100);
          homepage.getChildren().add(main);



        ImageView mainlogo = new ImageView(new Image("mainlogo.png"));
          this.getIcons().add(new Image("mainlogo.png"));
         mainlogo.setFitHeight(400);
         mainlogo.setFitWidth(200);
         mainlogo.setPreserveRatio(true);
         mainlogo.setX(440);
         mainlogo.setY(10);
         homepage.getChildren().add(mainlogo);


          AddPatientbutton = createButton("Patient Record");
          AddPatientbutton.setLayoutX(140);
          AddPatientbutton.setLayoutY(300);
          homepage.getChildren().add(AddPatientbutton);
          Image addpatientlogo = new Image("admitpatient.png");
          AddPatientbutton.setGraphic(setImageSize(addpatientlogo));
          AddPatientbutton.setOnAction(event -> {
              this.close();
              new AddPatientPage().show();
          });



          BookAppointmentButton = createButton("AppointMent");
          BookAppointmentButton.setLayoutX(440);
          BookAppointmentButton.setLayoutY(300);
          homepage.getChildren().add(BookAppointmentButton);
          Image Bookappointmentlogo = new Image("bookappointment.png");
          BookAppointmentButton.setGraphic(setImageSize(Bookappointmentlogo));
          BookAppointmentButton.setOnAction(event -> {
              this.close();
              new BookAppintmentPage().show();
          });


          DoctorsDetailButton = createButton("Doctors Record");
          DoctorsDetailButton.setLayoutX(140);
          DoctorsDetailButton.setLayoutY(400);
          homepage.getChildren().add(DoctorsDetailButton);
          Image doctorsrecordlogo = new Image("doctorrecord.png");
          DoctorsDetailButton.setGraphic(setImageSize(doctorsrecordlogo));
          DoctorsDetailButton.setOnAction(event -> {
              this.close();
              new DoctorsDetailPage().show();
          });

          BillingButton = createButton("Billing");
          BillingButton.setLayoutX(740);
          BillingButton.setLayoutY(300);
          homepage.getChildren().add(BillingButton);
          Image billinglogo = new Image("billing.png");
          BillingButton.setGraphic(setImageSize(billinglogo));
          BillingButton.setOnAction(event -> {
              this.close();
              new BillingPage().show();
          });

          PharmacyButton = createButton("Pharmacy");
          PharmacyButton.setLayoutX(440);
          PharmacyButton.setLayoutY(400);
          homepage.getChildren().add(PharmacyButton);
          Image pharmacylogo = new Image("pharmacylogo.png");
          PharmacyButton.setGraphic(setImageSize(pharmacylogo));
          PharmacyButton.setOnAction(event -> {
              this.close();
              new PharmacyPage().show();
          });

          AboutButton = createButton("About");
          AboutButton.setLayoutX(740);
          AboutButton.setLayoutY(400);
          homepage.getChildren().add(AboutButton);
          AboutButton.setOnAction(event -> {
              this.close();
              new AboutPage().show();
          });

          SideMenu menu = new SideMenu();
          menu.enableAutoClose();


          Button sidemenu = new Button();
          sidemenu.setBackground(null);
          ImageView menuimg = new ImageView(new Image("menuIcon.png",25,25,true,false));
          sidemenu.setGraphic(menuimg);
          sidemenu.setLayoutY(20);
          sidemenu.setLayoutX(5);
          homepage.getChildren().add(sidemenu);

          sidemenu.setOnAction(event -> {
              menu.openMenu(this.getX(), this.getY());

          });


          VBox glassbox = new VBox();
          glassbox.setLayoutX(100);
          glassbox.setLayoutY(200);
          glassbox.setPrefHeight(350);
          glassbox.setPrefWidth(900);
          glassbox.setPadding(new Insets(20));
          glassbox.setStyle("-fx-background-color: rgba(91,179,135,0.84);" +
                  "-fx-background-radius: 20;" +
                  "-fx-border-radius: 20;" +
                  "-fx-border-color: rgba(253,253,250,0.39);" +
                  "-fx-border-width: 1.5;");
          glassbox.setOpacity(0.5);
          homepage.getChildren().add(glassbox);
          glassbox.toBack();






          this.show();


      }

    public Button createButton(String s){
        Button temp=new Button(s);
        temp.setPrefSize(230,45);
        temp.setPadding(new Insets(4));
        temp.setTextFill(Color.WHITE);
        temp.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC,22));
        temp.setBackground(new Background(new BackgroundFill(Color.rgb(99,107,47),null,null)));
        return temp;
    }
    public ImageView setImageSize(Image image){
        ImageView temp=new ImageView(image);
        temp.setPreserveRatio(true);
        temp.setFitWidth(40);
        temp.setFitHeight(40);
        return temp;
    }


}
