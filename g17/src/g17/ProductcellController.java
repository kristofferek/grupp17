/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import se.chalmers.ait.dat215.project.Product;

/**
 * FXML Controller class
 *
 * @author Arvid
 */
public class ProductcellController implements Initializable {

    @FXML private Image imageImage;
    @FXML private Button infoButton;
    @FXML private Button favoriteButton;
    @FXML private Button buyButton;
    @FXML private Button addOneButton;
    @FXML private Button removeOneButton;
    @FXML private Label priceEachLabel;
    @FXML private Label priceTotalLabel;
    @FXML private Label nameOfProduct;
    @FXML private TextField productAmountTextField;
    
    private String lastValidProductAmountString = "";
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    public void setProduct(Product product){
        
    }
    
    private void setProductAmount(int amount){
        if(amount < 1){
            amount = 1;
        } else if(amount > 99){
            amount = 99;
        }
        
        productAmountTextField.setText("" + amount);
    }
    
    private int getProductAmount(){
        try{
            int productAmount = Integer.parseInt(productAmountTextField.getText());
            return productAmount;
        } catch (NumberFormatException e){
            return 0;
        }
    }
    
    private void correctProductAmountTextField(){
        try{
            if(!productAmountTextField.getText().equals("")){
                System.out.println("in if");
                int amount = Integer.parseInt(productAmountTextField.getText());
                if(amount < 1){
                    setProductAmount(1);
                } else if(amount > 99){
                    setProductAmount(99);
                }
                lastValidProductAmountString = productAmountTextField.getText();
            }
        } catch (NumberFormatException e){
            productAmountTextField.setText(lastValidProductAmountString);
        }
        
        
    }
    
    
    @FXML
    protected void addOneButtonActionPerformed(ActionEvent event){
        setProductAmount(getProductAmount() + 1);
    }
    @FXML
    protected void removeOneButtonActionPerformed(ActionEvent event){
        setProductAmount(getProductAmount() - 1);
    }
    @FXML
    protected void productAmountTextFieldOnKeyPress(javafx.scene.input.KeyEvent event){
        correctProductAmountTextField();
    }
    
}
