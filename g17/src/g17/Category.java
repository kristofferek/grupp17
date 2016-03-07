/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.util.ArrayList;
import java.util.List;
import se.chalmers.ait.dat215.project.ProductCategory;

/**
 *
 * @author Kristoffer
 */
public class Category {
    
    private List<ProductCategory> categories = new ArrayList<>();
    private final String categoryName;
    private String cssStyle;
    
    public Category(String categoryName, String cssStyle){
        this.categoryName = categoryName;
        this.cssStyle = cssStyle;
    }
    
    public String getCategoryName(){
        return categoryName;
    }
    
    public String getCssStyle(){
        return cssStyle;
    }
    
    public List<ProductCategory> getCategories(){
        return categories;
    }
    
    public void addProductCategory(ProductCategory pc){
        if(pc != null){
            categories.add(pc);
        }
    }
    
}
