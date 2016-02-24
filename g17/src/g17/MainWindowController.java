/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.ProductCategory;

/**
 * FXML Controller class
 *
 * @author Kristoffer
 */




public class MainWindowController implements Initializable {

    private List<Category> listViewCategories;
    MainWindowController instace;
    
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instace = this;
        
        initCategoryListView();
    }

    protected void initCategoryListView(){
        // Mejeri category
        Category diaries = new Category("Mejeri");
        diaries.addProductCategory(ProductCategory.DAIRIES);
        
        // Frukt och grönt category
        Category greenFood = new Category("Frukt och grönt");
        greenFood.addProductCategory(ProductCategory.CITRUS_FRUIT);
        greenFood.addProductCategory(ProductCategory.BERRY);
        greenFood.addProductCategory(ProductCategory.EXOTIC_FRUIT);
        greenFood.addProductCategory(ProductCategory.FRUIT);
        greenFood.addProductCategory(ProductCategory.MELONS);
        
        // Chark och kött category
        Category meat = new Category("Chark och kött");
        meat.addProductCategory(ProductCategory.MEAT);
        meat.addProductCategory(ProductCategory.FISH);
        
        //Lägger till alla kategorier i en lista
        listViewCategories.add(diaries);
        listViewCategories.add(meat);
        listViewCategories.add(greenFood);
        
        categoryListView.setItems(FXCollections.observableList(listViewCategories));
        categoryListView.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override public ListCell<Category> call(ListView<Category> list) {
                return new CategoryCell(instace);
            }
        });
    }
    
}
