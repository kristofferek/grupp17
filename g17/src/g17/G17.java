/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;

/**
 *
 * @author Kristoffer
 */
public class G17 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/g17");
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"),bundle);
           
        Scene scene = new Scene(root, 1280, 720);   
        primaryStage.getIcons().add(new Image("g17/pictures/imat.png"));
        primaryStage.setTitle("Imat - Mathandel");

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1280.0);
        primaryStage.setMinHeight(720.0); 
        primaryStage.setMaximized(true);
        primaryStage.show();
        
    }
    
    @Override
    public void stop(){
        IMatDataHandler.getInstance().shutDown();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
