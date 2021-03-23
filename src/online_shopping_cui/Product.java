/*
* This class contains the Product Class which encapsulates the following methods:
* Methods:
*- Empty Constructor
*- Five parameter Constructor
*- Getters and Setters
*
* @author  Miguel Emmara - 18022146
* @author  Amos Foong - 18044418
* @author  Roxy Dao - 1073633
* @version 1.0 
* @since   15/03/2021
 */
package online_shopping_cui;

public class Product {

    private String productName;
    private String description;
    private int productID;
    private double price;
    private Integer stock;

    public Product() {

    }

    public Product(String productName, int productID, double price, String description, Integer stock) {
        this.productName = productName;
        this.productID = productID;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
