package com.example.javafx;

import com.example.javafx.Helpers.Patient;
import com.example.javafx.Helpers.SideMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class AddPatientPage extends Stage {

    private TableView<Patient> table;
    private ObservableList<Patient> patientList;
    private final String FILE_PATH = "patients.csv";

    public AddPatientPage() {
        this.setTitle("Add Patient");
        this.setResizable(false);

        Image logo = new Image("mainlogo.png");
        this.getIcons().add(logo);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));


        Pane pane = new Pane();
        pane.setStyle(
                "-fx-background-image: url(startup.jpg);" +
                      "-fx-background-size: 100% 100%;" +
                       "-fx-background-repeat: no-repeat;");
        root.setLayoutY(50);

        pane.getChildren().addAll(root);


        SideMenu menu = new SideMenu();
        menu.enableAutoClose();
        Button sidemenu = new Button();
        ImageView menuimg = new ImageView(new Image("menuIcon.png",25,25,true,false));
        sidemenu.setGraphic(menuimg);
        sidemenu.setLayoutY(20);
        sidemenu.setLayoutX(5);
        pane.getChildren().add(sidemenu);

        sidemenu.setOnAction(event -> {
            menu.openMenu(this.getX(), this.getY());

        });


        TextField nameField = new TextField();
        nameField.setPromptText("Patient Name");

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female", "Other");
        genderBox.setPromptText("Gender");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField wardField = new TextField();
        wardField.setPromptText("Ward");

        DatePicker admitDatePicker = new DatePicker();
        admitDatePicker.setPromptText("Admit Date");

        Button admitButton = new Button("Admit Patient");
        admitButton.setPrefWidth(150);
        Button dischargeButton = new Button("Discharge Patient");
        dischargeButton.setPrefWidth(150);

        HBox form = new HBox(10, nameField, genderBox, ageField, wardField, admitDatePicker, admitButton, dischargeButton);


        table = new TableView<>();
        patientList = FXCollections.observableArrayList();
        table.setItems(patientList);

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Patient, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Patient, String> wardCol = new TableColumn<>("Ward");
        wardCol.setCellValueFactory(new PropertyValueFactory<>("ward"));

        TableColumn<Patient, LocalDate> admitCol = new TableColumn<>("Admit Date");
        admitCol.setCellValueFactory(new PropertyValueFactory<>("admitDate"));

        TableColumn<Patient, LocalDate> dischargeCol = new TableColumn<>("Discharge Date");
        dischargeCol.setCellValueFactory(new PropertyValueFactory<>("dischargeDate"));
        dischargeCol.setPrefWidth(100);

        table.getColumns().addAll(nameCol, genderCol, ageCol, wardCol, admitCol, dischargeCol);
        table.setStyle(
                "-fx-background-color: lightgray;" +
                        "-fx-control-inner-background: gray;" +
                        "-fx-control-inner-background-alt: gray;"
        );

        table.setPrefHeight(800);
        ScrollPane tablepane = new ScrollPane(table);





        loadPatientsFromFile();

        admitButton.setOnAction(e -> {
            String name = nameField.getText();
            String gender = genderBox.getValue();
            String ageText = ageField.getText();
            String ward = wardField.getText();
            LocalDate admitDate = admitDatePicker.getValue();

            if (name.isEmpty() || gender == null || ageText.isEmpty() || ward.isEmpty() || admitDate == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all fields!").showAndWait();
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Age must be a number!").showAndWait();
                return;
            }

            Patient patient = new Patient(name, gender, age, ward, admitDate);
            patientList.add(patient);

            savePatientsToFile();

            nameField.clear();
            genderBox.getSelectionModel().clearSelection();
            ageField.clear();
            wardField.clear();
            admitDatePicker.setValue(null);
        });


        dischargeButton.setOnAction(e -> {
            Patient selected = table.getSelectionModel().getSelectedItem();

            if (selected != null) {
                selected.setDischargeDate(LocalDate.now());
                savePatientsToFile();
                table.refresh();
            } else {
                new Alert(Alert.AlertType.WARNING, "Select a patient to discharge!").showAndWait();
            }
        });
        tablepane.setFitToWidth(true);
        root.getChildren().addAll(form, tablepane);

        Scene scene = new Scene(pane, 1100, 700);
        this.setScene(scene);
        this.show();
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

    private void savePatientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Patient p : patientList) {
                writer.write(
                        p.getName() + "," +
                                p.getGender() + "," +
                                p.getAge() + "," +
                                p.getWard() + "," +
                                p.getAdmitDate() + "," +
                                (p.getDischargeDate() != null ? p.getDischargeDate() : "")
                );
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
