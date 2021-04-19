package online_shopping_cui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
     * Search for a product by name.
     * 
     * @return Product that matched keyword
     */
    public Product searchProduct(String keyword){
        ArrayList<Product> pList = this.getProductList();
        
        for(Product product: pList){
            if(product.getProductName().contains(keyword)){ // If keyword partially matches an entry in pList...
                return product; // Return found product.
            }
        }
        
        return null; // Return null if not found.
    }
    
    /**
     * Remove product from singleProductList
     * 
     * @param category : Product category
     * @param index : Index of product within that category
     */
    public void removeProduct(Category category, Product product){       
        this.singleProductList.get(category).remove(product); 
    }
    
    /**
     * Checks if Category has the product
     * 
     * @return true if it does
     * @return false otherwise
     */
    public boolean categoryHasProduct(Category category){
        return singleProductList.containsKey(category);
    }
    
    /**
     * Print all entries in the Product ArrayList.
     * 
     * @return String containing entries in the Product ArrayList.
     */
    @Override
    public String toString(){
        ArrayList<Product> pList = this.getProductList();
        String pListStr = "\n";
        
        // For loop to traverse through the ArrayList-ed products.
        for(int i = 0; i < pList.size(); i++){
            pListStr += String.format("\t%d. Product Name: %s Stock: %d Price: $%.2f\n", (i + 1), pList.get(i).getProductName(), pList.get(i).getStock(), pList.get(i).getPrice());
        }
        
        return pListStr;
    }

    //Getters and Setters-------------------------------------------------------
    public LinkedHashMap<Category, ArrayList<Product>> getSingleProductList() {
        return singleProductList;
    }

    public void setSingleProductList(LinkedHashMap<Category, ArrayList<Product>> singleProductList) {
        this.singleProductList = singleProductList;
    }
}
