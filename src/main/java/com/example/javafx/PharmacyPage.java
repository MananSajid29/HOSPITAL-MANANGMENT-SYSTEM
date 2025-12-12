package com.example.javafx;

import com.example.javafx.Helpers.Doctor;
import com.example.javafx.Helpers.Medicine;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;

public class PharmacyPage extends Stage {
    TextField medname , quantity, price;
    TableView<Medicine> table;
    ObservableList<Medicine> data= FXCollections.observableArrayList();
    Button add;
    Label bill;


    public PharmacyPage(){
        Pane Pharmacypage = new Pane();
        Pharmacypage.setStyle("-fx-background-color: #49490f");
        Scene scene = new Scene(Pharmacypage, 1100, 700, Color.GREEN);
        this.setScene(scene);
        this.setResizable(false);
        this.setTitle(" Nexo Hospital-Pharmacy Page");

        medname=new TextField();
        medname.setPromptText("Enter med Name");
        medname.setTooltip(new Tooltip("Enter med name"));
        medname.setPrefSize(120,30);
        medname.setLayoutX(330);
        medname.setLayoutY(70);

        quantity=new TextField();
        quantity.setPromptText("Enter Quantity");
        quantity.setTooltip(new Tooltip("Enter med name"));
        quantity.setPrefSize(120,30);
        quantity.setLayoutX(490);
        quantity.setLayoutY(70);

        price=new TextField();
        price.setPromptText("Enter price");
        price.setTooltip(new Tooltip("Enter med name"));
        price.setPrefSize(120,30);
        price.setLayoutX(650);
        price.setLayoutY(70);

        bill = new Label();
        bill.setLayoutX(420);
        bill.setLayoutY(600);
        bill.setFont(Font.font("Arial", FontWeight.BOLD,20));
        bill.setTextFill(Color.WHITE);


        add=new Button("");
        ImageView addbutton = new ImageView(new Image("addbutton.png"));
        addbutton.setPreserveRatio(true);
        addbutton.setFitWidth(150);
        addbutton.setFitHeight(50);
        add.setGraphic(addbutton);
        add.setPrefSize(150,40);
        add.setLayoutX(370);
        add.setLayoutY(120);
        add.setBackground(null);
        add.setOnAction(e->{
            if(addMedicine(medname.getText(),quantity.getText(),price.getText())){
                new Alert(Alert.AlertType.INFORMATION,"Medicine added successfully").show();
            }else
                new Alert(Alert.AlertType.INFORMATION,"Enter data correctly").show();

            medname.setText("");
            quantity.setText("");
            price.setText("");
        });

        Button billing = new Button();
        ImageView billimg = new ImageView(new Image("BillButton.png"));
        billimg.setFitHeight(50);
        billimg.setFitWidth(150);
        billimg.setPreserveRatio(true);
        billing.setGraphic(billimg);
        billing.setLayoutX(570);
        billing.setLayoutY(120);
        billing.setBackground(null);
        billing.setOnAction(event -> calculateBill());


        table=createTable();
        ScrollPane pane=new ScrollPane(table);
        pane.setPrefSize(440,400);
        pane.setFitToWidth(true);
        pane.setLayoutX(330);
        pane.setLayoutY(180);

        ImageView main = new ImageView(new Image("startup.jpg"));
        main.setOpacity(0.4);
        main.setFitHeight(700);
        main.setFitWidth(1100);
        Pharmacypage.getChildren().addAll(main,medname,quantity,price,add,pane,billing,bill);

        SideMenu menu = new SideMenu();
        menu.enableAutoClose();
        Button sidemenu = new Button();
        ImageView menuimg = new ImageView(new Image("menuIcon.png",25,25,true,false));
        sidemenu.setGraphic(menuimg);
        sidemenu.setLayoutY(20);
        sidemenu.setLayoutX(5);
        Pharmacypage.getChildren().add(sidemenu);

        sidemenu.setOnAction(event -> {
            menu.openMenu(this.getX(), this.getY());
            // this.close();
        });

        this.show();

    }

    public TableView<Medicine> createTable(){
        TableView<Medicine> temp=new TableView<>();
        TableColumn<Medicine,String > nameCol=new TableColumn<>("Medicine Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine,String > quanCol=new TableColumn<>("Medicine Quantity");
        quanCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Medicine,String > priceCol=new TableColumn<>("Medicine price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);
        loadData();
        temp.setItems(data);
        temp.getColumns().addAll(nameCol,quanCol,priceCol);

        return temp;
    }

    public void writeData(){
        File file =new File("medicine.txt");
        Medicine d;
        try(FileWriter writer=new FileWriter(file)) {
            for (int i = 0; i < data.size(); i++) {
                d=data.get(i);
                if (i == 0)
                    writer.write(d.getName() + "," + d.getQuantity() + "," + d.getPrice());
                else
                    writer.write("\n" + d.getName() + "," + d.getQuantity() + "," + d.getPrice());
            }
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Cannot read file").show();
        }
    }

    public void loadData(){
        File file=new File("medicine.txt");
        String line;
        try{
            FileReader reader=new FileReader(file);
            BufferedReader input=new BufferedReader(reader);
            while((line=input.readLine())!=null){
                String [] d=line.split(",");
                data.add(new Medicine(d[0],Integer.parseInt(d[1]),Double.parseDouble(d[2])));
            }
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,"Cannot read file").show();

        }

    }
    public boolean addMedicine(String name,String quan,String price){
        if(name.isEmpty() || quan.isEmpty() || price.isEmpty() || name.contains(",")|| quan.contains(",")|| price.contains(","))
            return false;
        else{
            data.add(new Medicine(name,Integer.parseInt(quan),Double.parseDouble(price)));
            writeData();
            return true;
        }
    }
    public void calculateBill() {

            double result = 0;
            for(Medicine med : data){
               int quantityof = med.getQuantity();
               double priceof = med.getPrice();
               result +=  quantityof * priceof;
           }

               bill.setText("Total Expenses:  "+result);

    }

}
