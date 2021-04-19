package com.online.shopping_cui.model;

import com.online.shopping_cui.enumerations.Category;

/**
 * This class contains the Product Class which encapsulates the following methods:
 *
 * <p>Methods:</p>
 * <ul>
 *  <li>Empty Constructor</li>
 *  <li>Five-parameter Constructor</li>
 *  <li>Getters and Setters</li>
 *  <li>To String Method</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.02
 * @since 15/03/2021
 **/
public class Product {
    private String productName;
    private int productID;
    private double price;
    private Category category;
    private Integer stock;

    public Product() {
        this.productName = "";
        this.productID = 0;
        this.price = 0D;
        this.category = Category.MISC;
        this.stock = 0;
    }

    public Product(String productName, int productID, double price, Category category, Integer stock) {
        this.productName = productName;
        this.productID = productID;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    // Getters and setter methods for Object's instance data.
    //-------------------------------------------------------
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //-------------------------------------------------------
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    //-------------------------------------------------------
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //-------------------------------------------------------
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //-------------------------------------------------------
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    //-------------------------------------------------------

    /**
     * To String method to serialise object. Stringifies Object's
     * attributes, which are separated by commas.
     *
     * @return A String containing Product's name, ID, price, category,
     *              and stock number, all separated by commas.
     **/
    @Override
    public String toString() {
        String comma = ",";
        String pData = "";

        // Product details separated by commas.
        pData += this.getProductName() + comma;
        pData += this.getProductID() + comma;
        pData += this.getPrice() + comma;
        pData += this.getCategory().toString() + comma;
        pData += this.getStock();
        pData += "\n";

        return pData;
    }
}
