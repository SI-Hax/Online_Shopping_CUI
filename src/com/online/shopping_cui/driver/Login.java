/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.online.shopping_cui.driver;

import com.online.shopping_cui.model.Customer;
import com.online.shopping_cui.model.User;
import com.online.shopping_cui.model.Administrator;

import java.util.*;

/**
 * User Interface Class - This class contains methods and attributes that allows
 * user-computer interaction.
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.03
 * @since 18/04/2021
 *
 */
public class Login {
    static String WRONGPASS = "Incorrect password!";

    protected Scanner scanner;
    protected User currentUser;
    protected ProductMenu productMenu;
    protected HashMap<String, User> users;

    public Login(Scanner scanner, ProductMenu productMenu, User currentUser, HashMap<String, User> users) {
        this.scanner = scanner;
        this.productMenu = productMenu;
        this.currentUser = currentUser;
        this.users = users;
    }

    /**
     * Level 4a menu.
     *
     * @return T/F whether login was a success.
     */
    protected boolean customerLogin() {
        boolean promptLogin = true;
        do {
            System.out.println("\n\tPlease Enter Login Details (\"b\" to go back)");
            System.out.print("\tLogin ID: ");
            String loginID = scanner.nextLine();

            if (loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
                return false; // Exits method and returns false to caller to go back up a level for menu.
            } else if (this.users.containsKey(loginID) && (users.get(loginID)instanceof Customer)) { // If user-specified login is in the database and is of customer-type...
                boolean promptPassword = true;

                do {
                    // Prompt user for their password.
                    System.out.print("\tPassword: ");
                    String password = scanner.nextLine();

                    if (this.users.get(loginID).getPassword().equals(password)) { // If user-specified password match
                        this.currentUser = this.users.get(loginID); // Saves current logged-in user.
                        System.out.println("\nLogin Successful, Welcome Back " + ((Customer) currentUser).getName()); // Welcomes user.
                        promptLogin = false;
                        promptPassword = false;
                        return true; // Exits method and returns true to caller.
                    } else if (password.equalsIgnoreCase("b")) { // If user wishes to go back up a level...
                        return false; // Exits method and returns false to caller to go back up a level for menu.
                    } else { // Otherwise....
                        System.out.println(WRONGPASS);
                        promptPassword = true;
                    }
                } while (promptPassword);
            } else { // If user-specified login ID is non-existent...
                System.out.println("\nSorry, " + loginID + " is not a registered customer!");
                promptLogin = true;
            }
        } while (promptLogin);

        return false;
    }

    /**
     * Level 4a menu.
     *
     * @return T/F whether login was a success.
     */
    protected boolean adminLogin() {
        boolean promptLogin = true;
        do {
            System.out.println("\n\tPlease Enter Login Details (\"b\" to go back)");
            System.out.print("\tLogin ID: ");
            String loginID = scanner.nextLine();

            if (loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
                return false; // Exits method and returns false to caller to go back up a level for menu.
            } else if (this.users.containsKey(loginID) && (users.get(loginID) instanceof Administrator)) { // If user-specified login is in the database and is of admin-typed...
                boolean promptPassword = true;

                do {
                    // Prompt user for their password.
                    System.out.print("\tPassword: ");
                    String password = scanner.nextLine();

                    if (this.users.get(loginID).getPassword().equals(password)) { // If user-specified password match
                        this.currentUser = this.users.get(loginID);
                        System.out.println("\nLogin Successful, Welcome Back " + ((Administrator) currentUser).getAdminName()); // Welcomes user.
                        promptLogin = false;
                        promptPassword = false;
                        return true; // Exits method and returns true to caller.
                    } else if (password.equalsIgnoreCase("b")) { // If user wishes to go back up a level...
                        return false; // Exits method and returns false to caller to go back up a level for menu.
                    } else { // Otherwise....
                        System.err.println(WRONGPASS);
                        promptPassword = true;
                    }
                } while (promptPassword);
            } else { // If user-specified login ID is non-existent...
                System.out.println("\nSorry, " + loginID + " is not a registered administrator!");
                promptLogin = true;
            }
        } while (promptLogin);

        return false;
    }
}
