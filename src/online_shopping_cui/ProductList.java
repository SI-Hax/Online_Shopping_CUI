package online_shopping_cui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class contains the ProductList Class which encapsulates the following methods:
 * <p>Methods:</p>
 * <ul>
 *  <li>Empty Constructor</li>
 *  <li>Getters and Setters</li>
 * </ul>
 *
 * @author  Miguel Emmara - 18022146
 * @author  Amos Foong - 18044418
 * @author  Roxy Dao - 1073633
 * @version 1.0
 * @since   15/03/2021
 **/
public class ProductList {
    //Variables-----------------------------------------------------------------
    private LinkedHashMap<Category, ArrayList<Product>> singleProductList;

    //Constructors--------------------------------------------------------------
    public ProductList() {
        singleProductList = new LinkedHashMap<Category, ArrayList<Product>>();
    }

    public ProductList(Product newProduct) {
        singleProductList = new LinkedHashMap<Category, ArrayList<Product>>();

        addSingleProduct(newProduct);
    }

   
    /**
     * Core function: Adds a Product object into the entry list by Category
     *
     * @param newProductEntry
     */
    public void addSingleProduct(Product newProductEntry) {
        ArrayList<Product> tempList = new ArrayList<Product>();
        if (singleProductList.containsKey(newProductEntry.getCategory())) {
            tempList = singleProductList.get(newProductEntry.getCategory());
        }

        tempList.add(newProductEntry);
        singleProductList.put(newProductEntry.getCategory(), tempList);
    }
    
    /**
     * Core function: Get all product entries
     *
     * @return allProducts
     */
    public ArrayList<Product> getProductList() {
        ArrayList<Product> allProducts = new ArrayList<Product>();

        for (Category category : singleProductList.keySet()) {
            ArrayList<Product> tempList = singleProductList.get(category);
            allProducts.addAll(tempList);
        }

        return allProducts;
    }
    
    /**
     * Search for a product by key.
     * 
     * @return string of products
     */
    public String searchProduct(Category category){
        if(singleProductList.containsKey(category)){
            return "The Products in " + category + "is " + singleProductList.get(category);
        }
        return "No products under this category";
    }
    
    /**
     * Remove product from singleProductList
     * 
     * @param category : Product category
     * @param index : Index of product within that category
     */
    public void removeProduct(Category category, int index){       
        this.singleProductList.get(category).remove(index); 
    }
    
    /**
     * Checks if Category has the product
     * 
     * @return true if it does
     * @return false otherwise
     */
    public boolean categoryHasProduct(Category category){
        if(singleProductList.containsKey(category)){
            return true;
        }
        return false;
    }

    //Getters and Setters-------------------------------------------------------
    public LinkedHashMap<Category, ArrayList<Product>> getSingleProductList() {
        return singleProductList;
    }

    public void setSingleProductList(LinkedHashMap<Category, ArrayList<Product>> singleProductList) {
        this.singleProductList = singleProductList;
    }
}
