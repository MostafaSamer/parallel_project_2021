/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
    
    String[] dataHold = new String[]{};
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
        String[] intArray = new String[]{
            "https://www.googleapis.com/books/v1/volumes?q=search+terms",
            "https://www.googleapis.com/books/v1/volumes?q=search+fights",
            "https://www.googleapis.com/books/v1/volumes?q=search+computer",
            "https://www.googleapis.com/books/v1/volumes?q=search+crime",
            "https://www.googleapis.com/books/v1/volumes?q=search+cars"
        };
        
        Label label = new Label();
        label.setText("Welcome!");
        label.setWrapText(true);
        Button ThreadAPIBtn = new Button();
        ThreadAPIBtn.setText("GET Data by threads From APIs");
        ThreadAPIBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Thread
                dataHold = new ThreadAPI().getData(intArray);
                for (int i = 0; i < dataHold.length; i++) {
                    System.out.println(dataHold[i]);
                }
                label.setText(String.join(",", dataHold));
            }
        }   );
        Button ThreadSotreBtn = new Button();
        ThreadSotreBtn.setText("STORE Data by threads To DB");
        ThreadSotreBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Thread
                conn = new ThreadDB().connectDB();
                if(conn == null) System.exit(1);
                new ThreadDB().storeData(conn, headers, dataHold);
                dataHold = new String[]{};
                label.setText("empty*");
            }
        });
        Button ThreadRetriveBtn = new Button();
        ThreadRetriveBtn.setText("Retrive Data by Threads From DB");
        ThreadRetriveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Thread
                conn = new ThreadDB().connectDB();
                if(conn == null) System.exit(1);
                dataHold = new ThreadDB().retriveData(conn);
                label.setText(String.join(",", dataHold));
            }
        });
        
        //Button Width
        ThreadAPIBtn.setMaxSize(200, 30);
        ThreadSotreBtn.setMaxSize(200, 30);
        ThreadRetriveBtn.setMaxSize(200, 30);
        ThreadAPIBtn.setMinSize(200, 30);
        ThreadSotreBtn.setMinSize(200, 30);
        ThreadRetriveBtn.setMinSize(200, 30);
        
        
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
        gridPane.add(ThreadAPIBtn, 0, 0);
        gridPane.add(ThreadSotreBtn, 0, 1);
        gridPane.add(ThreadRetriveBtn, 0, 2);
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
