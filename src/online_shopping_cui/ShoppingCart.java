package online_shopping_cui;

import java.util.*;

/**
 *
 * @author Amos Foong <18044418>
 */
public class ShoppingCart 
{
    protected static int CART_ID = 0;
    private ArrayList<Product> products;
    private ArrayList<Integer> quantity;
    private double grandTotal;
    
    public ShoppingCart() 
    {
        ShoppingCart.CART_ID++;
        this.products = new ArrayList<Product>();
        this.quantity = new ArrayList<Integer>();
        this.grandTotal = 0.0;
    }
    
    public void addItemToCart(Product product, int amount) 
    {
        this.products.add(product);
        this.quantity.add(amount);
        this.grandTotal += (product.getPrice() * amount); 
    }
    
    public void removeItemFromCart(int index) 
    {
        this.grandTotal -= (this.products.get(index).getPrice() * this.quantity.get(index));
        this.products.remove(index);
        this.quantity.remove(index);
    }
}
