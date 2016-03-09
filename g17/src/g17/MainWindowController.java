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

import com.sun.javafx.css.StyleClassSet;
import java.time.chrono.Chronology;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
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
    private List<Order> orderHistory = new ArrayList<>();
    
    private static MainWindowController instance;
    private int amountToDisplay;
    private boolean isCartShowing = false;
    private boolean isListsShowing = false;
    private boolean isHistoryShowing = false;
    private int lastHorizontalCells = 5;
    
    @FXML private MenuBar menuBar;
    @FXML private TextField searchTextField;
    @FXML private Button homeButton;
    @FXML private Button historyButton;
    @FXML private Button favoriteButton;
    @FXML private Button cartButton;
    @FXML private Button searchButton;
    @FXML private Button finalBuyButton;
    @FXML private Label priceLabel;
    @FXML private ScrollPane mainView;
    @FXML private AnchorPane checkoutView;
    @FXML private AnchorPane cartAnchorPane;
    @FXML private AnchorPane historyAnchorPane;
    @FXML private ListView categoryListView;
    @FXML private ListView historyListView;
    @FXML private ListView cartListView;
    @FXML private AnchorPane gridContainer;
    @FXML private GridPane gridpane;
    @FXML private AnchorPane cartListAnchorPane;
    @FXML private Label cartAmountLabel;
    @FXML private Label cartPriceLabel;

    //Checkout object
    @FXML private TextField nameLabel;
    @FXML private TextField lastLabel;
    @FXML private TextField adrLabel;
    @FXML private TextField postLabel;
    @FXML private TextField cityLabel;
    @FXML private TextField phoneLabel;
    @FXML private TextField mailLabel;
    
    @FXML private RadioButton masterRadio;
    @FXML private RadioButton visaRadio;
    @FXML private TextField cardNameLabel;
    @FXML private TextField cardLabel;
    @FXML private TextField cvvLabel;
    @FXML private ChoiceBox monthBox;
    @FXML private ChoiceBox yearBox;
    
    @FXML private DatePicker dateLabel;
    
    @FXML private CheckBox box8;
    @FXML private CheckBox box11;
    @FXML private CheckBox box14;
    @FXML private CheckBox box17;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
        
        initCategoryListView();
        setProductsToDisplay(IMatDataHandler.getInstance().getProducts(), 5);
        initCartDropDown();
        System.out.println(historyButton.getStyleClass().toString());
    }
    
    protected void setProductsToDisplay(List<Product> products, int horizontalCells){
        ProductcellController.setProductsToDisplay(products);

        //products == null betyder att vi vill behålla de produkter vi visade innan och endast byta gridsize
        if (products != null){
            amountToDisplay = products.size();
        }
        // om horizontalCells == 0 så vill vi behålla den gridsize vi har för tillfället
        if (horizontalCells == 0){
            addDisplayProducts(lastHorizontalCells);
        }else {
            addDisplayProducts(horizontalCells);
            lastHorizontalCells = horizontalCells;
        }
    }
    
    protected void addDisplayProducts(int horizontalCells){
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
                for(int x = 0; x < horizontalCells; x++){
                    if(amountCreated == amountToDisplay){ break; }
                    
                    ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/g17");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("productcell.fxml"));

                    new ProductcellController();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("productcell.fxml"),bundle);
                    loader.setRoot(root);
                    
                    root.setScaleX(scaleX);
                    root.setScaleY(scaleY);
                    gridpane.add(root, x, (amountCreated)/horizontalCells);
                    amountCreated++;
                }
                if(amountCreated == amountToDisplay){ break; }
                gridpane.addRow((int)amountCreated/horizontalCells);
                gridpane.getRowConstraints().add(con);
            }
        } catch (IOException ex) {
            System.out.println("catched");
            ex.printStackTrace();
        }
        gridpane.autosize();
        gridContainer.setMaxHeight((amountCreated+4)/horizontalCells*300);
        mainView.autosize();
    }

    protected void initCategoryListView(){

        // Allt category
        Category showAll = new Category("Visa allt", "showallimage");

        // Bageri category
        Category bread = new Category("Bageri", "breadimage");
        bread.addProductCategory(ProductCategory.BREAD);

        // Chark och kött category
        Category meat = new Category("Chark & kött", "meatimage");
        meat.addProductCategory(ProductCategory.MEAT);

        // Bageri category
        Category drinks = new Category("Drycker", "drinkimage");
        drinks.addProductCategory(ProductCategory.COLD_DRINKS);
        drinks.addProductCategory(ProductCategory.HOT_DRINKS);

        //Fisk category
        Category fish = new Category("Fisk & skaldjur", "fishimage");
        fish.addProductCategory(ProductCategory.FISH);

        //Frukt category
        Category fruit= new Category("Frukt", "fruitimage");
        fruit.addProductCategory(ProductCategory.FRUIT);
        fruit.addProductCategory(ProductCategory.VEGETABLE_FRUIT);
        fruit.addProductCategory(ProductCategory.EXOTIC_FRUIT);
        fruit.addProductCategory(ProductCategory.CITRUS_FRUIT);
        fruit.addProductCategory(ProductCategory.MELONS);
        fruit.addProductCategory(ProductCategory.BERRY);

        //Vegtable category
        Category greenFood = new Category("Grönsaker", "greenimage");
        greenFood.addProductCategory(ProductCategory.CABBAGE);
        greenFood.addProductCategory(ProductCategory.MELONS);
        greenFood.addProductCategory(ProductCategory.BERRY);
        greenFood.addProductCategory(ProductCategory.HERB);


        // Mejeri category
        Category diaries = new Category("Mejeri", "diaryimage");
        diaries.addProductCategory(ProductCategory.DAIRIES);

        // Skafferi category
        Category pantry = new Category("Skafferi", "dryfoodimage");
        pantry.addProductCategory(ProductCategory.FLOUR_SUGAR_SALT);
        pantry.addProductCategory(ProductCategory.PASTA);
        pantry.addProductCategory(ProductCategory.POTATO_RICE);
        pantry.addProductCategory(ProductCategory.NUTS_AND_SEEDS);
        pantry.addProductCategory(ProductCategory.POD);
        pantry.addProductCategory(ProductCategory.HERB);

        // Snask category
        Category sweets = new Category("Snask", "sugarimage");
        sweets.addProductCategory(ProductCategory.SWEET);
        sweets.addProductCategory(ProductCategory.NUTS_AND_SEEDS);

        //Lägger till alla kategorier i en lista
        listViewCategories.add(showAll);
        listViewCategories.add(fruit);
        listViewCategories.add(greenFood);
        listViewCategories.add(bread);
        listViewCategories.add(diaries);
        listViewCategories.add(meat);
        listViewCategories.add(fish);
        listViewCategories.add(pantry);
        listViewCategories.add(drinks);
        listViewCategories.add(sweets);

        categoryListView.setItems(FXCollections.observableList(listViewCategories));
        //TODO
        categoryListView.getStyleClass().clear();
        categoryListView.getStyleClass().add("currentTab");
        categoryListView.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override public ListCell<Category> call(ListView<Category> list) {
                return new CategoryCell();
            }
        });       
    }
    
    public static void setToMainWindow(){
        getInstance().mainView.toFront();
    }
    
    public static void setIsHistoryShowing(boolean set){    
            getInstance().isHistoryShowing = set;
    }
    
    protected void initHistoryDropDown(){
        orderHistory.clear();
        historyListView.getItems().clear();
        
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
        
        if (shoppingItems.size()==0){
            shoppingItems.add(null);
        }
                
        cartListView.setItems(FXCollections.observableList(shoppingItems));
        cartListView.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
            @Override
            public ListCell<ShoppingItem> call(ListView<ShoppingItem> list) {
                return new ShoppingItemCell();
            }
        });
        updateCartLabels();
        
    }
    
    protected void initCheckoutList(){
        
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        nameLabel.setText(customer.getFirstName());
        lastLabel.setText(customer.getLastName());
        adrLabel.setText(customer.getAddress());
        postLabel.setText(customer.getPostCode());
        cityLabel.setText(customer.getPostAddress());
        phoneLabel.setText(customer.getMobilePhoneNumber());
        mailLabel.setText(customer.getEmail());
        
        CreditCard card = IMatDataHandler.getInstance().getCreditCard();
        ToggleGroup cardGroup = new ToggleGroup();
        masterRadio.setUserData("MasterCard");
        masterRadio.setToggleGroup(cardGroup);
        visaRadio.setUserData("Visa");
        visaRadio.setToggleGroup(cardGroup);
        masterRadio.setSelected(true);
        
        cardNameLabel.setText(card.getHoldersName());
        cardLabel.setText(card.getCardNumber());
        cvvLabel.setText(card.getVerificationCode()+"");
        initCardComboBox();
    }
    
    protected void initCardComboBox(){
        ObservableList<String> months = 
        FXCollections.observableArrayList(
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        monthBox.setItems(months); 
        monthBox.setValue(months.get(0));
        
        ObservableList<String> years = 
        FXCollections.observableArrayList(
                "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023");
        yearBox.setItems(years); 
        yearBox.setValue(years.get(0));
    }
    
    
    
    public static MainWindowController getInstance(){
        return instance;
    }

    @FXML
    protected void searchButtonActionPerformed(ActionEvent event){
        String searchWord = searchTextField.getText();
        setProductsToDisplay(IMatDataHandler.getInstance().findProducts(searchWord), 0);
    }
    @FXML
    protected void searchTextFieldActionPreformed(KeyEvent event){
        if(event.getCode()== KeyCode.ENTER){
            searchButtonActionPerformed(null);
        }
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
        setProductsToDisplay(IMatDataHandler.getInstance().favorites(), 0);
        bringCartToFront();
    }
    
    @FXML
    protected void cartButtonActionPerformed(ActionEvent event){
        if(isCartShowing){
            cartAnchorPane.toBack();
            setProductsToDisplay(null, 5);
            isCartShowing = false;
        }else {
            cartAnchorPane.toFront();
            setProductsToDisplay(null, 4);
            cartAnchorPane.setMouseTransparent(false);
            isCartShowing = true;
        }
    }

    @FXML
    protected void homeButtonActionPerformed(ActionEvent event){
        // TODO
        mainView.toFront();
    }

    @Override
    public void shoppingCartChanged(CartEvent ce) {
        updateCartLabels();
        initCartDropDown();
    }
   
    private void updateCartLabels(){
        double price = IMatDataHandler.getInstance().getShoppingCart().getTotal();
        priceLabel.setText(String.format("%.2f", price) + " kr");
        int amount=0;
        for (ShoppingItem item : IMatDataHandler.getInstance().getShoppingCart().getItems()){
            amount += item.getAmount();
            }
        cartAmountLabel.setText("Antal varor: " + amount + " st");
        cartPriceLabel.setText("Totalt: " + String.format("%.2f", price) + " kr");
    }
    
    @FXML
    protected void finalBuyButtonActionPerformed(ActionEvent event){
        initCheckoutList();
        checkoutView.toFront();
        bringCartToFront();
        finalBuyButton.setVisible(false);
        
        
        System.out.println("clicked final buy");
    }
    
    protected void bringCartToFront(){
        if (isCartShowing){
            cartAnchorPane.toFront();
            cartAnchorPane.setMouseTransparent(false);
            finalBuyButton.setVisible(true);
        }
    }
    @FXML
    protected void placeOrderButtonActionPerformed(ActionEvent event){
        
        if(check(nameLabel) && check(lastLabel) && check(adrLabel) && check(postLabel) &&
            check(cityLabel) && check(phoneLabel) && check(mailLabel) && check(cardNameLabel) &&
            check(cvvLabel) && check(dateLabel.getEditor())){

            System.out.println("Din order är nu bekräftad.");
            
            Customer cust= IMatDataHandler.getInstance().getCustomer();
            cust.setFirstName(nameLabel.getText());
            cust.setLastName(lastLabel.getText());
            cust.setAddress(adrLabel.getText());
            cust.setPostAddress(cityLabel.getText());
            cust.setPostCode(postLabel.getText());
            cust.setMobilePhoneNumber(phoneLabel.getText());
            cust.setEmail(mailLabel.getText());
            
            CreditCard card = IMatDataHandler.getInstance().getCreditCard();
            card.setCardNumber(cardLabel.getText());
            card.setCardType((masterRadio.selectedProperty().getValue()==true?masterRadio.getText():visaRadio.getText()));
            card.setHoldersName(cardNameLabel.getText());
            card.setValidMonth(Integer.parseInt(monthBox.getValue().toString()));
            card.setValidYear(Integer.parseInt(yearBox.getValue().toString()));
            card.setVerificationCode(Integer.parseInt(cvvLabel.getText()));
            
            IMatDataHandler.getInstance().placeOrder();
            IMatDataHandler.getInstance().getShoppingCart().clear();
        }
        else {
            System.out.println("Var god och fyll i alla fält");
        }
    }
    
    //Returnerar false om textfältet är tomt
    private boolean check(TextField t){
        return !(t.getText().equals(""));
    }
}
