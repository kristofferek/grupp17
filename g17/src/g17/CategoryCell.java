/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

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

                Label name = new Label(item.getCategoryName());

                setGraphic(name);
            
                this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //controller.initGridView(item.getCategories());
                        //controller.gridView.toFront();
                    }
                });
                this.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        name.setStyle("-fx-background-color:#ADD8E6");
                    }
                });
                name.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        name.setStyle("");
                    }
                });
            }
        }
    }
    
}
