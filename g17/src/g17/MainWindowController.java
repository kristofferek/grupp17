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
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * FXML Controller class
 *
 * @author Kristoffer
 */




public class MainWindowController implements Initializable, ShoppingCartListener {

    private List<Category> listViewCategories = new ArrayList<>();
    private List<ShoppingItem> shoppingItems = new ArrayList<>();
    private static MainWindowController instance;
    private int amountToDisplay;
    private boolean isCartShowing = false;
    
    @FXML private MenuBar menuBar;
    @FXML private TextField searchTextField;
    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button rundturButton;
    @FXML private Button historyButton;
    @FXML private Button cartButton;
    @FXML private Button favoriteButton;
    @FXML private Button listButton;
    @FXML private Button finalBuyButton;
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
        instance = this;
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
        
        initCategoryListView();
        setProductsToDisplay(IMatDataHandler.getInstance().getProducts());
        initCartDropDown();
        
    }
    
    protected void setProductsToDisplay(List<Product> products){
        
        amountToDisplay = products.size();
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
                    
                    Parent root = FXMLLoader.load(getClass().getResource("productcell.fxml"),bundle);
                    loader.setRoot(root);
                    
                    root.setScaleX(scaleX);
                    root.setScaleY(scaleY);
                    gridpane.add(root, x, (amountCreated)/5);
                    amountCreated++;
                }
                if(amountCreated == amountToDisplay){ break; }
                gridpane.addRow((int)amountCreated/5);
                gridpane.getRowConstraints().add(con);
            }
        } catch (IOException ex) {
            System.out.println("catched");
            ex.printStackTrace();
        }
        gridpane.autosize();
        gridContainer.setMaxHeight((amountCreated+4)/5*300);
        mainView.autosize();
    }

    protected void initCategoryListView(){
        
        // Allt category
        Category showAll = new Category("Visa allt");

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
        listViewCategories.add(showAll);
        listViewCategories.add(diaries);
        listViewCategories.add(meat);
        listViewCategories.add(greenFood);
        
        categoryListView.setItems(FXCollections.observableList(listViewCategories));
        categoryListView.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override public ListCell<Category> call(ListView<Category> list) {
                return new CategoryCell();
            }
        });       
    }
    
    protected void initCartDropDown(){
        shoppingItems.clear();
        shoppingItems.addAll(IMatDataHandler.getInstance().getShoppingCart().getItems());
        cartListView.setItems(FXCollections.observableList(shoppingItems));
        cartListView.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
            @Override public ListCell<ShoppingItem> call(ListView<ShoppingItem> list) {
                return new ShoppingItemCell();
            }
        });
        
    }
    
    public static MainWindowController getInstance(){
        return instance;
    }

    @FXML
    protected void searchButtonActionPerformed(ActionEvent event){
        String searchWord = searchTextField.getText();
        setProductsToDisplay(IMatDataHandler.getInstance().findProducts(searchWord));
    }
    
    @FXML
    protected void historyButtonActionPerformed(ActionEvent event){
        // TODO
    }
    
    @FXML
    protected void favoriteButtonActionPerformed(ActionEvent event){
        setProductsToDisplay(IMatDataHandler.getInstance().favorites());
    }
    
    @FXML
    protected void cartButtonActionPerformed(ActionEvent event){
        if(isCartShowing){
            cartAnchorPane.toBack();
            isCartShowing = false;
        }else {
            cartAnchorPane.toFront();
            cartAnchorPane.focusedProperty();
            isCartShowing = true;
        }
    }
    
    @FXML
    protected void listButtonActionPerformed(ActionEvent event){
        // TODO
    }
    
    @FXML
    protected void homeButtonActionPerformed(ActionEvent event){
        // TODO
    }

    @Override
    public void shoppingCartChanged(CartEvent ce) {
        priceLabel.setText(IMatDataHandler.getInstance().getShoppingCart().getTotal() + " kr");
        initCartDropDown();
    }
    
    @FXML
    protected void finalBuyButtonActionPerformed(ActionEvent event){
        System.out.println("clicked final buy");
    }
    
}
