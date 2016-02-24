/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g17;

import java.util.List;
import se.chalmers.ait.dat215.project.ProductCategory;

/**
 *
 * @author Kristoffer
 */
public class Category {
    
    private List<ProductCategory> categories;
    private final String categoryName;
    
    public Category(String categoryName){
        this.categoryName = categoryName;
    }
    
    public String getCategoryName(){
        return categoryName;
    }
    
    public List<ProductCategory> getCategories(){
        return categories;
    }
    
    public void addProductCategory(ProductCategory pc){
        categories.add(pc);
    }
    
}
