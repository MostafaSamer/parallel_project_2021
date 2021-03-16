/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets; 
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 *
 * @author mosta
 */
public class Main extends Application {
    
    ArrayList<String> dataHold = new ArrayList<>();
    Connection conn;
    
    @Override
    public void start(Stage stage) {
        
        String[] headers = new String[]{
            "terms",
            "fights",
            "computer",
            "crime",
            "cars"
        };
        ArrayList<String> intArray = new ArrayList<>();
        intArray.add("https://www.googleapis.com/books/v1/volumes?q=search+terms");
        intArray.add("https://www.googleapis.com/books/v1/volumes?q=search+fights");
        intArray.add("https://www.googleapis.com/books/v1/volumes?q=search+computer");
        intArray.add("https://www.googleapis.com/books/v1/volumes?q=search+crime");
        intArray.add("https://www.googleapis.com/books/v1/volumes?q=search+cars");
        
        Label label = new Label();
        label.setText("Welcome!");
        label.setWrapText(true);
        Button seqAPIBtn = new Button();
        seqAPIBtn.setText("GET Data With RX From APIs");
        seqAPIBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // SEQ
                dataHold = new RXAPI().getData(intArray);
                for (int i = 0; i < dataHold.size(); i++) {
                    System.out.println(dataHold.get(i));
                }
                label.setText(String.join(",", dataHold));
            }
        });
        Button seqSotreBtn = new Button();
        seqSotreBtn.setText("STORE Data With RX To DB");
        seqSotreBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // SEQ
                conn = new RXDB().connectDB();
                if(conn == null) System.exit(1);
                new RXDB().storeData(conn, headers, dataHold);
                dataHold = new ArrayList<>();
                label.setText("empty*");
            }
        });
        Button seqRetriveBtn = new Button();
        seqRetriveBtn.setText("Retrive Data With RX From DB");
        seqRetriveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // SEQ
                conn = new RXDB().connectDB();
                if(conn == null) System.exit(1);
               // dataHold = new SeqDB().retriveData(conn);
                label.setText(String.join(",", dataHold));
            }
        });
        
        //Button Width
        seqAPIBtn.setMaxSize(170, 30);
        seqSotreBtn.setMaxSize(170, 30);
        seqRetriveBtn.setMaxSize(170, 30);
        seqAPIBtn.setMinSize(170, 30);
        seqSotreBtn.setMinSize(170, 30);
        seqRetriveBtn.setMinSize(170, 30);
        
        
        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();    

        //Setting size for the pane  
        gridPane.setMinSize(1000, 1000); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);
        
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.TOP_LEFT);
        
        //Arranging all the nodes in the grid 
        gridPane.add(seqAPIBtn, 0, 0);
        gridPane.add(seqSotreBtn, 0, 1);
        gridPane.add(seqRetriveBtn, 0, 2);
        gridPane.add(label, 1, 0);
        
        
        //Creating a scene object 
        Scene scene = new Scene(gridPane);
        
        //Setting title to the Stage 
        stage.setTitle("Grid Pane Example"); 

        //Adding scene to the stage 
        stage.setScene(scene); 

        //Displaying the contents of the stage 
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
