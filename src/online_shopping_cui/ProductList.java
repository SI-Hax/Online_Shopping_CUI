/*
* This class contains the ProductList Class which encapsulates the following methods:
* Methods:
*- Empty Constructor
*- Getters and Setters
*
* @author  Miguel Emmara - 18022146
* @author  Amos Foong - 18044418
* @author  Roxy Dao - 1073633
* @version 1.0 
* @since   15/03/2021
 */
package online_shopping_cui;

import java.util.*;

public class ProductList {
    //Variables-----------------------------------------------------------------
    private HashMap<Category, ArrayList<Product>> singleProductList;
    
    //Constructors--------------------------------------------------------------
    public ProductList(){
        singleProductList = new HashMap<Category, ArrayList<Product>>();
    }
    
    public ProductList(Product newProduct){
        singleProductList = new HashMap<Category, ArrayList<Product>>();
        
        addSingleProduct(newProduct);
    }
    
    public void addSingleProduct(Product newProductEntry){
        ArrayList<Product> tempList = new ArrayList<Product>();
    }
}
