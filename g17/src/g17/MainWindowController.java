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
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;
import sun.swing.FilePane;

/**
 * FXML Controller class
 *
 * @author Kristoffer
 */




public class MainWindowController implements Initializable, ShoppingCartListener {

    private List<Category> listViewCategories = new ArrayList<>();
    private List<ShoppingItem> shoppingItems = new ArrayList<>();  
    private List<Order> orderHistory = new ArrayList<>();
    
    private static MainWindowController instance;
    private int amountToDisplay;
    private boolean isCartShowing = false;
    private boolean isListsShowing = false;
    private boolean isHistoryShowing = false;
    
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
    @FXML private ListView cartListViewCheckout;
    @FXML private ListView listListView;
    @FXML private AnchorPane gridContainer;
    @FXML private GridPane gridpane;
    @FXML private AnchorPane cartListAnchorPane;
    
    
    
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
        greenFood.addProductCategory(ProductCategory.HERB);
        greenFood.addProductCategory(ProductCategory.VEGETABLE_FRUIT);
        greenFood.addProductCategory(ProductCategory.CABBAGE);
        
        // Chark och kött category
        Category meat = new Category("Chark och kött");
        meat.addProductCategory(ProductCategory.MEAT);
        meat.addProductCategory(ProductCategory.FISH);
        
        // Skafferi category
        Category pantry = new Category("Skafferi");
        pantry.addProductCategory(ProductCategory.BREAD);
        pantry.addProductCategory(ProductCategory.FLOUR_SUGAR_SALT);
        pantry.addProductCategory(ProductCategory.NUTS_AND_SEEDS);
        pantry.addProductCategory(ProductCategory.PASTA);
        pantry.addProductCategory(ProductCategory.POTATO_RICE);
        pantry.addProductCategory(ProductCategory.POD);
        pantry.addProductCategory(ProductCategory.COLD_DRINKS);
        pantry.addProductCategory(ProductCategory.HOT_DRINKS);
        
        // Snask category
        Category sweets = new Category("Snask");
        sweets.addProductCategory(ProductCategory.SWEET);
        
        //Lägger till alla kategorier i en lista
        listViewCategories.add(showAll);
        listViewCategories.add(diaries);
        listViewCategories.add(meat);
        listViewCategories.add(greenFood);
        listViewCategories.add(pantry);
        listViewCategories.add(sweets);
        
        categoryListView.setItems(FXCollections.observableList(listViewCategories));
        categoryListView.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override public ListCell<Category> call(ListView<Category> list) {
                return new CategoryCell();
            }
        });       
    }
    
    public static void setToMainWindow(){
        getInstance().mainView.toFront();
    }
    
    protected void initHistoryDropDown(){
        orderHistory.clear();
        historyListView.getItems().clear();
       
        //for test purposes
        for(int i=0; i<10; i++){
            IMatDataHandler.getInstance().placeOrder(false);
        }
        
       
        orderHistory.addAll(IMatDataHandler.getInstance().getOrders());
        historyListView.setItems(FXCollections.observableList(orderHistory));
        
        for(int i=0; i<orderHistory.size(); i++){
            historyListView.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
                @Override public ListCell<Order> call(ListView<Order> list) {
                    return new HistoryItemCell();
                }
            });
        }
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
    
    protected void initCheckoutList(){
        shoppingItems.clear();
        shoppingItems.addAll(IMatDataHandler.getInstance().getShoppingCart().getItems());
        cartListViewCheckout.setItems(FXCollections.observableList(shoppingItems));
        cartListViewCheckout.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
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
        if(isHistoryShowing){
            historyAnchorPane.toBack();
            isHistoryShowing = false;
        }else {
            historyAnchorPane.toFront();
            historyAnchorPane.setMouseTransparent(false);
            isHistoryShowing = true;           
            initHistoryDropDown();
        }
    }
    
    @FXML
    protected void favoriteButtonActionPerformed(ActionEvent event){
        mainView.toFront();
        setProductsToDisplay(IMatDataHandler.getInstance().favorites());
    }
    
    @FXML
    protected void cartButtonActionPerformed(ActionEvent event){
        if(isCartShowing){
            cartAnchorPane.toBack();
            isCartShowing = false;
        }else {
            cartAnchorPane.toFront();
            cartAnchorPane.setMouseTransparent(false);
            isCartShowing = true;
        }
    }
    
    
    @FXML
    protected void listButtonActionPerformed(ActionEvent event){
        if(isListsShowing){
            listAnchorPane.toBack();
            isListsShowing = false;
        }else {
            listAnchorPane.toFront();
            listAnchorPane.setMouseTransparent(false);
            isListsShowing = true;
        }
    }
    
    @FXML
    protected void homeButtonActionPerformed(ActionEvent event){
        mainView.toFront();
    }

    @Override
    public void shoppingCartChanged(CartEvent ce) {
        priceLabel.setText(IMatDataHandler.getInstance().getShoppingCart().getTotal() + " kr");
        initCartDropDown();
        initCheckoutList();
    }
    
    @FXML
    protected void finalBuyButtonActionPerformed(ActionEvent event){
                    checkoutView.toFront();

        /*if(isCartShowing){
            isCartShowing = false;
        }else {
            cartAnchorPane.toFront();
            cartAnchorPane.setMouseTransparent(false);
            isCartShowing = true;
        }*/
        
        
        
        System.out.println("clicked final buy");
    }
    
}
