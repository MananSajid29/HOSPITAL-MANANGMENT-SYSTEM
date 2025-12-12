package com.example.javafx;

import com.example.javafx.Helpers.AppointMent;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookAppintmentPage extends Stage {
    Button book, cancel;
    TextField nameField,  ageField, phoneField;
    ComboBox<String> genderbox;
    DatePicker date;
    ChoiceBox<String> doctorList=new ChoiceBox<>();
    ObservableList<AppointMent> data= FXCollections.observableArrayList();
    TableView<AppointMent> table;

    public BookAppintmentPage() {
        Pane BookAppointmentpage = new Pane();
        BookAppointmentpage.setStyle("-fx-background-color: #49490f");
        Scene scene = new Scene(BookAppointmentpage, 1100, 700, Color.GREEN);
        this.setScene(scene);
        this.setResizable(false);
        this.setTitle(" Nexo Hospital-Book Appointment Page");

        date=new DatePicker();
        date.setLayoutX(900);
        date.setPromptText("Booking date");
        date.setLayoutY(70);
        date.setPrefSize(120,30);



        table=createTable();
        ScrollPane tablePane=new ScrollPane(table);
        tablePane.setPrefSize(800,400);
        tablePane.setLayoutX(150);
        tablePane.setLayoutY(200);
        tablePane.setFitToWidth(true);
        table.setStyle(
                "-fx-background-color: lightgray;" +
                        "-fx-control-inner-background: gray;" +
                        "-fx-control-inner-background-alt: gray;"
        );

        table.setOnMouseClicked(e->{
            AppointMent p=table.getSelectionModel().getSelectedItem();
            if(p!=null){
                nameField.setText(p.getName());
                genderbox.setValue(p.getGender());
                ageField.setText(p.getAge());
                phoneField.setText(p.getPhone());
                doctorList.setValue(p.getDocName());
                String []d=p.getDate().split("-");
                date.setValue(LocalDate.of(Integer.parseInt(d[0]),Integer.parseInt(d[1]),Integer.parseInt(d[2])));

            }
        });

        nameField=createField("Name");
        nameField.setLayoutX(100);

        genderbox = new ComboBox<>();
        genderbox.getItems().addAll("Male", "Female", "Other");
        genderbox.setPromptText("Gender");
        genderbox.setLayoutX(260);
        genderbox.setLayoutY(70);
        genderbox.setPrefSize(120,30);

        ageField=createField("Age");
        ageField.setLayoutX(420);

        phoneField=createField("Phone");
        phoneField.setLayoutX(580);

        createDoctorList();
        doctorList.setLayoutY(70);
        doctorList.setLayoutX(740);
        doctorList.setValue("Dr Name");
        doctorList.setPrefSize(120,30);


        book = new Button("Book");
        book.setLayoutY(130);
        book.setLayoutX(450);
        book.setPrefSize(80,30);
        book.setOnAction(e -> {
            LocalDate selected = date.getValue();
            if (selected == null) {
                new Alert(Alert.AlertType.INFORMATION, "Date must be selected").show();
                return;
            }
                   // here we check for same date error
            boolean found = false;
            try (BufferedReader br = new BufferedReader(new FileReader("AppointMent.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    String dateString = parts[parts.length - 1].trim();
                    LocalDate booked = LocalDate.parse(dateString);
                    if (booked.equals(selected)) {
                        found = true;
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            if (found) {
                new Alert(Alert.AlertType.ERROR, "Date Already Booked. Select a different date.").show();
                return;
            }

            
            boolean success = book(
                    nameField.getText(),
                    genderbox.getValue(),
                    ageField.getText(),
                    phoneField.getText(),
                    doctorList.getSelectionModel().getSelectedItem(),
                    selected.toString()
            );

            if (success)
                new Alert(Alert.AlertType.INFORMATION, "Appointment Booked Successfully").show();
            else
                new Alert(Alert.AlertType.INFORMATION, "Enter data correctly").show();
        });


        cancel = new Button("Cancel");
        cancel.setLayoutY(130);
        cancel.setLayoutX(570);
        cancel.setPrefSize(80,30);
        cancel.setOnAction(e->{
            if(cancel())
                new Alert(Alert.AlertType.INFORMATION,"Appointment Canceled successfully").show();
            else
                new Alert(Alert.AlertType.INFORMATION,"Please select an appointment").show();
            setFieldEmpty();
        });


        ImageView main = new ImageView(new Image("startup.jpg"));
        writeData();
        main.setOpacity(0.4);
        main.setFitHeight(700);
        main.setFitWidth(1100);
        BookAppointmentpage.getChildren().addAll(main,tablePane,nameField,genderbox,ageField,phoneField,doctorList,date);
        BookAppointmentpage.getChildren().addAll(book,cancel);
        SideMenu menu = new SideMenu();
        menu.enableAutoClose();


        Button sidemenu = new Button();
        ImageView menuimg = new ImageView(new Image("menuIcon.png",25,25,true,false));
        sidemenu.setGraphic(menuimg);
        sidemenu.setLayoutY(20);
        sidemenu.setLayoutX(5);
        BookAppointmentpage.getChildren().add(sidemenu);

        sidemenu.setOnAction(event -> {
            menu.openMenu(this.getX(), this.getY());
            // this.close();
        });
        this.show();
    }

         public TableView<AppointMent> createTable () {
            TableView<AppointMent> temp = new TableView<>();

            TableColumn<AppointMent, String> col1 = new TableColumn<>("Patient Name");
            col1.setCellValueFactory(new PropertyValueFactory<>("name"));
            col1.setPrefWidth(100);

            TableColumn<AppointMent, String> col2 = new TableColumn<>("Gender");
            col2.setCellValueFactory(new PropertyValueFactory<>("gender"));

            TableColumn<AppointMent, String> col3 = new TableColumn<>("Age");
            col3.setCellValueFactory(new PropertyValueFactory<>("age"));

            TableColumn<AppointMent, String> col4 = new TableColumn<>("Contact No");
            col4.setCellValueFactory(new PropertyValueFactory<>("phone"));
            col4.setPrefWidth(100);


            TableColumn<AppointMent, String> col5 = new TableColumn<>("Dr Name");
            col5.setCellValueFactory(new PropertyValueFactory<>("docName"));
            col5.setPrefWidth(100);


            TableColumn<AppointMent, String> col6 = new TableColumn<>("Date");
            col6.setCellValueFactory(new PropertyValueFactory<>("date"));
            col6.setPrefWidth(100);

            temp.getColumns().addAll(col1,col2,col3,col4,col5,col6);

            loadData();
            temp.setItems(data);
            return temp;
        }

        public TextField createField(String s){
        TextField temp =new TextField();
        temp.setPromptText("Enter "+s);
        temp.setTooltip(new Tooltip("Enter "+s));
        temp.setPrefSize(120,30);
        temp.setLayoutY(70);
        return temp;
        }

        public void createDoctorList(){
        ObservableList<String> list=doctorList.getItems();
            File file=new File("Doctor.txt");
            String line;
            try{
                FileReader reader=new FileReader(file);
                BufferedReader input=new BufferedReader(reader);
                while((line=input.readLine())!=null){
                    String [] d=line.split(",");
                    list.add(d[0]);
                }
            }catch(IOException e){
                new Alert(Alert.AlertType.ERROR,"Cannot read file").show();
            }
        }


        public void writeData () {
            File file = new File("AppointMent.txt");
            AppointMent p;
            try (FileWriter writer = new FileWriter(file)) {
                for (int i = 0; i < data.size(); i++) {
                    p = data.get(i);
                    if (i == 0)
                        writer.write(p.getName() + "," + p.getGender() + "," + p.getAge() + "," + p.getPhone() + "," + p.getDocName() + "," + p.getDate());
                    else
                        writer.write("\n" + p.getName() + "," + p.getGender() + "," + p.getAge() + "," + p.getPhone() + "," + p.getDocName() + "," + p.getDate());
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Cannot read file").show();
            }
        }

        public void loadData () {
            File file = new File("AppointMent.txt");
            String line;
            try {
                FileReader reader = new FileReader(file);
                BufferedReader input = new BufferedReader(reader);
                while ((line = input.readLine()) != null) {
                    String[] d = line.split(",");
                    data.add(new AppointMent(d[0], d[1], d[2], d[3], d[4], d[5]));
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Cannot read file").show();

            }
       }
       public boolean verify(String name , String gen , String age, String ph , String DrNa,String Dat){
                if(name.isEmpty() || gen.isEmpty() || age.isEmpty() || DrNa.isEmpty() || Dat.isEmpty()){
                    return false;
                }

                if(name.contains(",") || gen.contains(",") || age.contains(",") || DrNa.contains(",") || Dat.contains(",")){
                       return false;
                }

                if(!age.matches("\\d+"))
                    return false;

                if(!ph.matches("\\d{11}"))
                    return false;


                return true;
       }
    public boolean book(String name , String gen , String age, String ph , String DrNa,String Dat){
        if(verify(name,gen,age,ph,DrNa,Dat)){
            data.add(new AppointMent(name,gen,age,ph,DrNa,Dat));
            writeData();
            return true;
        }

        return false;
    }

    public boolean cancel(){
        int row=table.getSelectionModel().getSelectedIndex();
        if(row>=0){
            data.remove(row);
            writeData();
            return true;
        }
        return  false;
    }

    public void setFieldEmpty(){
        nameField.setText("");
        genderbox.setValue("");
        ageField.setText("");
        phoneField.setText("");
        doctorList.setValue(null);
        date.setValue(null);
    }

    }

