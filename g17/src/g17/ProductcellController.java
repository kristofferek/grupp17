/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.ait.dat215.project.Product;

/**
 * FXML Controller class
 *
 * @author Arvid
 */
public class ProductcellController implements Initializable {

    @FXML private Button infoButton;
    @FXML private Button favoriteButton;
    @FXML private Button buyButton;
    @FXML private Button addOneButton;
    @FXML private Button removeOneButton;
    @FXML private Label priceEachLabel;
    @FXML private Label priceTotalLabel;
    @FXML private Label nameOfProduct;
    @FXML private TextField productAmountTextField;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setProduct(Product product){
        
    }
    
    public void setProductAmount(int amount){
        
    }
    
}
