/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Kristoffer
 */
public class CategoryCell extends ListCell<Category>{
    
    MainWindowController controller;
    
    public CategoryCell(MainWindowController c){
        controller = c;
    }
    
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
                        //controller.initGridView(item.getCategories());
                        //controller.gridView.toFront();
                    }
                });
            }
        }
    }
    
}