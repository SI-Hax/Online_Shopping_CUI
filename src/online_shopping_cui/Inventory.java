/**
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 02/04/2021
 **/
package online_shopping_cui;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> quantity;

    public Inventory(HashMap<String, Integer> quantity) {
        this.quantity = quantity;
    }

    public HashMap<String, Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(HashMap<String, Integer> quantity) {
        this.quantity = quantity;
    }
}
