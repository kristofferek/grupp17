/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

/**
 * FXML Controller class
 *
 * @author Kristoffer
 */




public class MainWindowController implements Initializable {

    private List<Category> listViewCategories = new ArrayList<>();
    private MainWindowController instace;
    private int amountToDisplay;
    
    @FXML private MenuBar menuBar;
    @FXML private TextField searchTextField;
    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button rundturButton;
    @FXML private Button historyButton;
    @FXML private Button cartButton;
    @FXML private Button favoriteButton;
    @FXML private Button listButton;
    @FXML private Label priceLabel;
    @FXML private ScrollPane mainView;
    @FXML private AnchorPane checkoutView;
    @FXML private AnchorPane cartAnchorPane;
    @FXML private AnchorPane historyAnchorPane;
    @FXML private AnchorPane listAnchorPane;
    @FXML private ListView categoryListView;
    @FXML private ListView historyListView;
    @FXML private ListView cartListView;
    @FXML private ListView listListView;
    @FXML private AnchorPane gridContainer;
    @FXML private GridPane gridpane;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instace = this;
        
        initCategoryListView();
        setProductsToDisplay(IMatDataHandler.getInstance().getProducts());
        
    }
    
    protected void setProductsToDisplay(List<Product> products){
        
        amountToDisplay = products.size();
        ProductcellController.clearCreated();
        ProductcellController.setProductsToDisplay(products);
        addDisplayProducts();
    }
    
    protected void addDisplayProducts(){
        //gridContainer.autosize();
        gridpane.getChildren().clear();
        gridContainer.autosize();
        gridpane.setMaxWidth(1626);
        
        System.out.println("gridContainer width: " + gridContainer.getWidth());
        double scaleX = (((gridContainer.getWidth()) / 5.0) * (double)1/325) * 0.97;        //scale to fit in gridpane
        double scaleY = ((gridContainer.getHeight() / 3.0) * (double)1/409) * 0.90;
        
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(300);
        gridpane.getRowConstraints().add(con);
        gridpane.autosize();
        
        int amountCreated = 0;
        try {
            while(amountCreated<amountToDisplay){
                for(int x = 0; x < 5; x++){
                    if(amountCreated == amountToDisplay){ break; }
                    
                    ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/g17");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("productcell.fxml"));

                    new ProductcellController();
                    
                    //System.out.println("controller: " + controller);
                    
                    Parent root = FXMLLoader.load(getClass().getResource("productcell.fxml"),bundle);
                    loader.setRoot(root);
                    
                    //System.out.println("root: " + root);

                    root.setScaleX(scaleX);
                    root.setScaleY(scaleY);
                    System.out.println("x: " + x + ", y: " + (int)amountCreated/5 + " Tillagd vara");
                    gridpane.add(root, x, (amountCreated)/5);
                    amountCreated++;
                }
                if(amountCreated == amountToDisplay){ break; }
                System.out.println((int)amountCreated/5 + " Tillagd rad");
                gridpane.addRow((int)amountCreated/5);
                gridpane.getRowConstraints().add(con);
            }
        } catch (IOException ex) {
            System.out.println("catched");
            ex.printStackTrace();
            //Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        gridpane.autosize();
        gridContainer.setMaxHeight((amountCreated+4)/5*300);
        mainView.autosize();
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

    @FXML
    protected void searchButtonActionPerformed(ActionEvent event){
        String searchWord = searchTextField.getText();
    }
    
}
