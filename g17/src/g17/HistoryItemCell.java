/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;


/**
 *
 * @author David
 */
public class HistoryItemCell extends ListCell<Order> {
    
    @Override 
    protected void updateItem(Order item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        
        if (item != null){
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);          

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(6);
                grid.setPadding(new Insets(2, 5, 2, 5));
                
                Label name = new Label(item.getDate().toString());
                name.getStyleClass().add("categorylist");
                grid.add(name, 1, 0);             
                
                setGraphic(grid);
            
                this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    private List<Product> products = new ArrayList<>();
                    @Override
                    public void handle(MouseEvent event) {                   
                        for(int i=0; i<item.getItems().size(); i++){
                            products.add(item.getItems().get(i).getProduct());
                        }                       
                        MainWindowController.getInstance().setProductsToDisplay(products);  
                       }
                });
            }
        }
    }
}
