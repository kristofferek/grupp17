/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * FXML Controller class
 *
 * @author Arvid
 */
public class ProductcellController implements Initializable {

    @FXML private ImageView imageImageView;
    @FXML private Button favouriteButton;
    @FXML private Button buyButton;
    @FXML private Button addOneButton;
    @FXML private Button removeOneButton;
    @FXML private Label priceEachLabel;
    @FXML private Label priceTotalLabel;
    @FXML private Label nameOfProduct;
    @FXML private TextField productAmountTextField;
    
    private String lastValidProductAmountString;
    private Product product;
    private static List<Product> productsToDisplay;
    
    private static int created = 0;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lastValidProductAmountString = "";
        setProduct(productsToDisplay.get(created));
        created++;


        //Removes the .button class and adds favourite/nonfavourite.
        favouriteButton.getStyleClass().remove(0);
        if(IMatDataHandler.getInstance().favorites().contains(product)){
            favouriteButton.getStyleClass().add("favourite");
        }else{
            favouriteButton.getStyleClass().add("nonfavourite");
        }
    }
    
    public void setProduct(Product product){
        this.product = product;
        priceEachLabel.setText(product.getPrice() +  " kr / "  + product.getUnitSuffix());
        nameOfProduct.setText(product.getName());
        imageImageView.setImage(IMatDataHandler.getInstance().getFXImage(product));
        setProductAmount(1);
    }
    
    private void setProductAmount(int amount){
        
        //limit input prompt 1 <= value <= 99
        if(amount < 1){
            amount = 1;
        } else if(amount > 99){
            amount = 99;
        }
        
        productAmountTextField.setText("" + amount);
        updateTotalPrice();
    }
    
    private void updateTotalPrice(){
        if(product != null){
            double price = product.getPrice()*getProductAmount();
            priceTotalLabel.setText("" + String.format("%.2f", price) + " kr");
        }
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
        //conversion of string to int value, corrects problems if any occur
        try{
            if(!productAmountTextField.getText().equals("")){
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
        updateTotalPrice();
    }
    @FXML
    protected void addFavouriteActionPerformed(ActionEvent event) {
        //toggling favorite for product
        favouriteButton.getStyleClass().clear();
        if (IMatDataHandler.getInstance().favorites().contains(product)) {
            IMatDataHandler.getInstance().removeFavorite(product);
            favouriteButton.getStyleClass().add("nonfavourite");
        } else {
            IMatDataHandler.getInstance().addFavorite(product.getProductId());
            favouriteButton.getStyleClass().add("favourite");
        }
    }
    @FXML
    protected void buyButtonActionPerformed(ActionEvent event){
        boolean isAlreadyInCart = false;
        //Kollar om varan redan finns i "kundvagnen" och i så fall bara ökar antalet på varan
        for (ShoppingItem item : IMatDataHandler.getInstance().getShoppingCart().getItems()){
            if(item.getProduct().getName().equals(product.getName())){
                item.setAmount(item.getAmount()+getProductAmount());
                isAlreadyInCart = true;
                break;
            }
        }
        //Om varan inte redan finns i "kundvagnen" så lägg till den
        if (!isAlreadyInCart){
            ShoppingItem shoppingItem = new ShoppingItem(product, getProductAmount());
            IMatDataHandler.getInstance().getShoppingCart().addProduct(product, getProductAmount());
        }
        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(new ShoppingItem(product), true);
    }
    
    public static void setProductsToDisplay(List<Product> products){
        if (products != null){
            productsToDisplay = products;
        }
        created = 0;
    }
    
}
