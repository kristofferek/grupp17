/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Kristoffer
 */




public class MainWindowController implements Initializable {

    private ResourceBundle rb;
    @FXML private MenuBar menuBar;
    @FXML private TextField searchTextField;
    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button rundturButton;
    @FXML private Button historyButton;
    @FXML private Button cartButton;
    @FXML private Button favoritehButton;
    @FXML private Label priceLabel;
    @FXML private ScrollPane mainView;
    @FXML private AnchorPane checkoutView;
    @FXML private ListView categoryListView;

<<<<<<< HEAD
    private Prod


=======
    
    
>>>>>>> origin/master
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
    }

    @FXML
    protected void searchButtonActionPerformed(ActionEvent event){

        String searchWord = searchTextField.getText();

    }
    
}
