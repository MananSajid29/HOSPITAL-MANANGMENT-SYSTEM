package com.example.javafx;

import com.example.javafx.Helpers.Patient;
import com.example.javafx.Helpers.SideMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BillingPage extends Stage {
     TextField name , gender , age , phone , admitDate , dischargeDate;
     Button search , CalculateBill;
     int SelectedPatient=-1;
     private ObservableList<Patient> patientList=FXCollections.observableArrayList();
     private final String FILE_PATH = "patients.csv";
     Label bill;


    public BillingPage(){
         loadPatientsFromFile();
        Pane billingpage = new Pane();
        billingpage.setStyle("-fx-background-color: #49490f");
        Scene scene = new Scene(billingpage, 1100, 700, Color.GREEN);
        this.setResizable(false);
        this.setScene(scene);
        this.setTitle(" Nexo Hospital-Home Page");

        ImageView main = new ImageView(new Image("startup.jpg"));
        main.setOpacity(0.4);
        main.setFitHeight(700);
        main.setFitWidth(1100);
        billingpage.getChildren().add(main);

        name = createField("Name");
        name.setLayoutY(150);

        gender = createField("Gender");
        gender.setLayoutY(200);

        age = createField("Age");
        age.setLayoutY(250);

        phone = createField("Phone");
        phone.setLayoutY(300);

        admitDate = createField("Admit Date");
        admitDate.setLayoutY(350);

        dischargeDate =createField("Discharge date");
        dischargeDate.setLayoutY(400);

        search = new Button("Search");
        search.setLayoutX(380);
        search.setLayoutY(150);
        search.setOnAction(event -> {
            Search();
        });

        CalculateBill = new Button("Calculate Bill");
        CalculateBill.setLayoutX(380);
        CalculateBill.setLayoutY(200);
        CalculateBill.setOnAction(event -> {
            CalculateBill();
        });

         bill = new Label("Total Bill");
         bill.setPrefSize(200, 40);
         bill.setLayoutY(450);
         bill.setLayoutX(150);
         bill.setFont(Font.font("Arial", FontWeight.BOLD,20));
         bill.setTextFill(Color.WHITE);


        SideMenu menu = new SideMenu();
        menu.enableAutoClose();


        Button sidemenu = new Button();
        ImageView menuimg = new ImageView(new Image("menuIcon.png",25,25,true,false));
        sidemenu.setGraphic(menuimg);
        sidemenu.setLayoutY(20);
        sidemenu.setLayoutX(5);
        billingpage.getChildren().add(sidemenu);

        sidemenu.setOnAction(event -> {
            menu.openMenu(this.getX(), this.getY());
            // this.close();
        });


        billingpage.getChildren().addAll(name,gender,age,phone,admitDate,dischargeDate,search,CalculateBill,bill);


        this.show();


    }

    public TextField createField(String s){
        TextField temp =new TextField();
        temp.setPromptText("Enter "+s);
        temp.setTooltip(new Tooltip("Enter "+s));
        temp.setPrefSize(150,40);
        temp.setLayoutX(150);
        return temp;
    }

    private void loadPatientsFromFile() {
        if (!Files.exists(Paths.get(FILE_PATH))) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    String name = parts[0];
                    String gender = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String ward = parts[3];
                    LocalDate admitDate = LocalDate.parse(parts[4]);

                    Patient p = new Patient(name, gender, age, ward, admitDate);

                    if (parts.length == 6 && !parts[5].isEmpty()) {
                        p.setDischargeDate(LocalDate.parse(parts[5]));
                    }

                    patientList.add(p);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Search(){
          boolean found = false;
         if(!name.getText().isEmpty())
         {
             for(int i =0 ; i<patientList.size();i++)
             {
                 if(patientList.get(i).getName().equals(name.getText()))
                 {
                     Patient p = patientList.get(i);
                     new Alert(Alert.AlertType.INFORMATION,"Patient Found").show();
                     gender.setText(p.getGender());
                     age.setText(""+p.getAge());
                     admitDate.setText(p.getAdmitDate().toString());
                     dischargeDate.setText(p.getDischargeDate().toString());
                     SelectedPatient = i;
                     found = true;
                     break;
                 }
                 if(!found)
                 {
                     new Alert(Alert.AlertType.INFORMATION,"Patient Not Found").show();
                 }
             }


         }

    }


    public void CalculateBill(){
        long days=0;
        if(SelectedPatient!=-1) {
            Patient p = patientList.get(SelectedPatient);
            days = ChronoUnit.DAYS.between(p.getDischargeDate(), p.getAdmitDate());
        }
        bill.setText("Total Bill : "+days * 4000);
    }
}
