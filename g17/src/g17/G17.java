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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Kristoffer
 */
public class G17 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/g17");
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"),bundle);
        Parent root2 = FXMLLoader.load(getClass().getResource("productcell.fxml"),bundle);
        
        Scene scene = new Scene(root2, 1280, 720);
        
        primaryStage.setTitle("iMat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
