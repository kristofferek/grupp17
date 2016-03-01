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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

/**
 *
 * @author Kristoffer
 */
public class CategoryCell extends ListCell<Category>{
    
    @Override 
    protected void updateItem(Category item, boolean empty) {
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
                grid.setPadding(new Insets(5, 10, 5, 10));
                Label name = new Label(item.getCategoryName());
                name.getStyleClass().add("categorylist");
                grid.add(name, 0, 0);
                setGraphic(grid);
            
                this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("In mouse event");
                        MainWindowController.setToMainWindow();
                        if(item.getCategoryName().equalsIgnoreCase("Visa allt")){
                            MainWindowController.getInstance().setProductsToDisplay(IMatDataHandler.getInstance().getProducts());
                        }else{
                            List<Product> productsToDisplay = new ArrayList<>();
                            for(ProductCategory cat : item.getCategories()){
                                productsToDisplay.addAll(IMatDataHandler.getInstance().getProducts(cat));
                            }
                            MainWindowController.getInstance().setProductsToDisplay(productsToDisplay);
                        }
                    }
                });
            }
        }
    }
    
}
