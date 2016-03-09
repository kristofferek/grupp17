/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 *
 * @author Kristoffer
 */
public class ShoppingItemCell extends ListCell<ShoppingItem>{
    
    String lastValidProductAmountString = "1";
    static int count = 0;
    
    @Override 
    protected void updateItem(ShoppingItem item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        
        if (item != null){
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);          

                BorderPane pane = new BorderPane();
                pane.setPadding(new Insets(2, 5, 2, 5));
                TextField txtField = new TextField((int)item.getAmount()+"");
                txtField.setMaxWidth(40);
                txtField.setDisable(false);
                txtField.setEditable(true);
                pane.setLeft(txtField);
                
                Label name = new Label(item.getProduct().getName());
                BorderPane.setAlignment(name, Pos.CENTER_LEFT);
                BorderPane.setMargin(name, new Insets(0,0,0,10));
                name.getStyleClass().add("produktnamn");
                pane.setCenter(name);
                
                GridPane priceAndDelete = new GridPane();
                double cost = item.getTotal();
                Label price = new Label(String.format("%.2f", cost)+" kr");
                price.setTextAlignment(TextAlignment.CENTER);
                GridPane.setMargin(price, new Insets(0,10,0,0));
                priceAndDelete.add(price, 0, 0);
                
                Button delete = new Button("X");
                priceAndDelete.add(delete, 1, 0);
                
                pane.setRight(priceAndDelete);
                setGraphic(pane);
            
                delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        IMatDataHandler.getInstance().getShoppingCart().removeItem(item);
                        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(item, false);
                    }
                });
                
                // Listen for TextField text changes
                txtField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        try{
                            if(!txtField.getText().equals("")){
                                int amount = Integer.parseInt(txtField.getText());
                                if(amount < 1){
                                    txtField.setText(1+"");
                                } else if(amount > 99){
                                    txtField.setText(99+"");
                                }
                                item.setAmount(Integer.parseInt(txtField.getText()));
                                double cost2 = item.getTotal();
                                price.setText(String.format("%.2f", cost2)+ " kr");
                                //IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(item, true);
                                lastValidProductAmountString = txtField.getText();
                            }
                        } catch (NumberFormatException e){
                            txtField.setText(lastValidProductAmountString);
                        }
                    }
                });
            }
        } else {
            if (count <1){    
                Label noProducts = new Label("Din kundvagn är tom");
                noProducts.getStyleClass().add("produktnamn");
                setGraphic(noProducts);
                count++;
            } else {
                setText(null);
                setGraphic(null);
            }
        }
    }
    
}

