package com.example.javafx;

import com.example.javafx.Helpers.Doctor;
import com.example.javafx.Helpers.SideMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class DoctorsDetailPage extends Stage {
    TextField nameField,degreeField,experience;
    Button addButton,removeButton;
    TableView<Doctor>  table;
    ObservableList<Doctor> data= FXCollections.observableArrayList();

    public DoctorsDetailPage(){
        Pane DoctorsDetailpage = new Pane();
        DoctorsDetailpage.setStyle("-fx-background-color: #49490f");
        Scene scene = new Scene(DoctorsDetailpage, 1100, 700, Color.GREEN);
        this.setResizable(false);
        this.setScene(scene);
        this.setTitle(" Nexo Hospital-Doctors Detail Page");

        table=createTabled();
        ScrollPane pane=new ScrollPane(table);
        pane.setPrefSize(440,400);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        pane.setLayoutX(330);
        table.setStyle(
                "-fx-background-color: lightgray;" +
                        "-fx-control-inner-background: gray;" +
                        "-fx-control-inner-background-alt: gray;"
        );

        table.setOnMouseClicked(e->{
            Doctor c=table.getSelectionModel().getSelectedItem();
            if (c != null) {
                nameField.setText(c.getName());
                degreeField.setText(c.getDegree());
                experience.setText(c.getExperience());
            }
        });
        pane.setLayoutY(200);

        nameField=new TextField();
        nameField.setPromptText("Enter Dr. Name");
        nameField.setTooltip(new Tooltip("Enter Dr. name"));
        nameField.setPrefSize(120,30);
        nameField.setLayoutX(330);
        nameField.setLayoutY(70);

        degreeField=new TextField();
        degreeField.setPromptText("Enter Degree");
        degreeField.setTooltip(new Tooltip("Enter Degree"));
        degreeField.setPrefSize(120,30);
        degreeField.setLayoutX(490);
        degreeField.setLayoutY(70);

        experience=new TextField();
        experience.setPromptText("Enter Experience");
        experience.setTooltip(new Tooltip("Enter Experience"));
        experience.setPrefSize(120,30);
        experience.setLayoutX(650);
        experience.setLayoutY(70);

        addButton=new Button("Add");
        addButton.setPrefSize(80,30);
        addButton.setLayoutX(440);
        addButton.setLayoutY(120);
        addButton.setOnAction(e->{
            if(addDoctor(nameField.getText(),degreeField.getText(),experience.getText()))
                new Alert(Alert.AlertType.INFORMATION,"Doctor added Successfully").show();
            else
                new Alert(Alert.AlertType.INFORMATION,"Cannot add Doctor.Enter data correctly").show();

            nameField.setText("");
            degreeField.setText("");
            experience.setText("");
        });

        removeButton=new Button("Remove");
        removeButton.setPrefSize(80,30);
        removeButton.setLayoutX(560);
        removeButton.setLayoutY(120);
        removeButton.setOnAction(e->{
            if(removeDoctor())
                new Alert(Alert.AlertType.INFORMATION,"Doctor removed Successfully").show();
            else
                new Alert(Alert.AlertType.INFORMATION,"Please select a doctor").show();
            nameField.setText("");
            degreeField.setText("");
            experience.setText("");

        });



        ImageView main = new ImageView(new Image("startup.jpg"));
        main.setOpacity(0.4);
        main.setFitHeight(700);
        main.setFitWidth(1100);
        DoctorsDetailpage.getChildren().addAll(main,pane,nameField,degreeField,experience,addButton,removeButton);

        SideMenu menu = new SideMenu();
        menu.enableAutoClose();
        Button sidemenu = new Button();
        ImageView menuimg = new ImageView(new Image("menuIcon.png",25,25,true,false));
        sidemenu.setGraphic(menuimg);
        sidemenu.setLayoutY(20);
        sidemenu.setLayoutX(5);
        DoctorsDetailpage.getChildren().add(sidemenu);

        sidemenu.setOnAction(event -> {
            menu.openMenu(this.getX(), this.getY());
            // this.close();
        });

        this.show();

    }

    public TableView<Doctor> createTabled(){
        TableView<Doctor> temp=new TableView<>();

        TableColumn<Doctor,String> col1=new TableColumn<>("Name");
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col1.setPrefWidth(110);

        TableColumn<Doctor,String> col2=new TableColumn<>("Degree");
        col2.setCellValueFactory(new PropertyValueFactory<>("degree"));
        col2.setPrefWidth(110);

        TableColumn<Doctor,String> col3=new TableColumn<>("Experience");
        col3.setCellValueFactory(new PropertyValueFactory<>("experience"));
        col3.setPrefWidth(220);

        temp.getColumns().addAll(col1,col2,col3);
        temp.setItems(data);
        loadData();
        return temp;
    }

    public void writeData(){
        File file =new File("Doctor.txt");
        Doctor d;
        try(FileWriter writer=new FileWriter(file)) {
            for (int i = 0; i < data.size(); i++) {
                d=data.get(i);
                if (i == 0)
                    writer.write(d.getName() + "," + d.getDegree() + "," + d.getExperience());
                else
                    writer.write("\n" + d.getName() + "," + d.getDegree() + "," + d.getExperience());
            }
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Cannot read file").show();
        }
    }

    public void loadData(){
        File file=new File("Doctor.txt");
        String line;
        try{
            FileReader reader=new FileReader(file);
            BufferedReader input=new BufferedReader(reader);
            while((line=input.readLine())!=null){
                String [] d=line.split(",");
                data.add(new Doctor(d[0],d[1],d[2]));
            }
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,"Cannot read file").show();

        }
    }

    public boolean verify(String name,String degree,String experience){
        if(name.isEmpty() || degree.isEmpty()|| experience.isEmpty())
            return false;
        if(name.contains(",") || degree.contains(",") || experience.contains(","))
            return false;

        return true;
    }

    public boolean addDoctor(String name,String degree,String ex){
        if(verify(name,degree,ex)){
            data.add(new Doctor(name,degree,ex));
            writeData();
            return true;
        }
        return false;
    }

    public boolean removeDoctor(){
        int row=table.getSelectionModel().getSelectedIndex();
        if(row>=0){
            data.remove(row);
            writeData();
            return true;
        }
        return false;
    }
}
