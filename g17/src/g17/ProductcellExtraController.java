/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Arvid
 */
public class ProductcellExtraController implements Initializable {

    private List<Category> lists = new ArrayList<>();
    
    private ProductcellExtraController instance; 

    
    @FXML private ListView listView;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        // TODO
        

        
        
        //lists.add(/*listor*/);
        
        /*lists.setItems(FXCollections.observableList(lists));
        lists.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override public ListCell<Category> call(ListView<Category> list) {
                return new CategoryCell(instance);
            }
        }); */
    }    
    
}
