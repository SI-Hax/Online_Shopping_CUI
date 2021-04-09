package online_shopping_cui;

import java.io.*;
import java.util.*;

/**
 * This class maintains a set of tools (static methods) essential
 * for importing/exporting user's data from/onto .csv files.
 *
 * <p>Attributes:</p>
 * <ul>
 *  <li>Customers' Database Filepath</li>
 *  <li>Administrators' Database Filepath</li>
 * </ul>
 * 
 * Behaviours:
 * <ul>
 *   <li>Import User Data</li>
 *   <li>Export User Data</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 09/04/2021
 **/
public final class UserFileIO 
{
    public static String customerFilepath = "./resources/customer_database.csv";
    public static String adminFilepath = "./resources/admin_database.csv";
    
    /**
     * This method reads Users' information from multiple .csv files 
     * and loads them onto a local HashMap. 
     * 
     * <p>It processes the information by reading each line, then it
     * splits each value using commas as indicators. It then loads the 
     * information onto the HashMap by calling the appropriate constructor,
     * passing in the processed data.</p>
     * 
     * @return A HashMap containing all the Users in the .csv files. Returns 
     *           a fresh HashMap if there are errors when reading from file.
     **/
    public static HashMap<String, User> importUserData()
    {
        HashMap<String, User> usersData = new HashMap<>();
        
        // Block to import data from customer_database.csv
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(UserFileIO.customerFilepath));
            String line = null;
            String[] data = new String[8];
            
            br.readLine(); // Skips the column headers.
            
            while((line = br.readLine()) != null)
            {
                // Splits each line into individual values and store them in the String array.
                data = line.split(","); 
                
                // Stores imported Customer data onto a local HashMap.
                usersData.put(data[0], new Customer(data[0], // LoginID
                        Utilities.decrypt(data[1]),          // Password
                        data[2],                             // Name 
                        data[3],                             // Phone
                        data[4],                             // Email
                        data[5].replaceAll(";", ","),        // Address
                        Utilities.decrypt(data[6]),          // Card number
                        data[7]));                           // Card holder
            }
        } catch (IOException e) {
            System.err.println("Error reading from file.");
        } finally {
            // Close BufferedReader at last.
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    System.err.println("Error closing buffered reader.");
                }
            }
        }
        
        // Block to import data from admin_database.csv
        BufferedReader br2 = null;
        try 
        {
            br2 = new BufferedReader(new FileReader(UserFileIO.adminFilepath));
            String line = null;
            String[] data = new String[4];
            
            br2.readLine(); // Skips the column headers.
            
            while((line = br2.readLine()) != null)
            {
                // Splits each line into individual values and store them in the String array.
                data = line.split(","); 
                
                // Stores imported Administrator data onto a local HashMap.
                usersData.put(data[0], new Administrator(data[0], // LoginID
                        Utilities.decrypt(data[1]),               // Password
                        data[2],                                  // Name
                        data[3]));                                // Email
                                                
            }
        } catch (IOException e) {
            System.err.println("Error reading from file.");
        } finally {
            // Close BufferedReader at last.
            if (br2 != null) {
                try {
                    br2.close();
                } catch (IOException ex) {
                    System.err.println("Error closing buffered reader.");
                }
            }
        }
       
        return usersData;
    }
    
    /**
     * This method writes locally stored Users' information onto  
     * multiple .csv files. 
     * 
     * <p>Steps:</p>
     * <ol>
     *  <li>Instantiate a Buffered PrintWriter Object with overwrite mode.</li>
     *  <li>Print Column headers.</li>
     *  <li>Traverse through the passed in HashMap</li>
     *  <li>Determine the type of user.</li>
     *  <li>Use Customer/Administrator class' toString method to serialise data.</li>
     *  <li>Write information onto the appropriate file.</li>
     * </ol>
     * 
     * @param users : A map of users in the system.
     * @return T/F whether the write was successful.
     **/
    public static boolean exportUserData(HashMap<String, User> users)
    {
        boolean writeSuccess = false;
        
        PrintWriter pw = null;
        PrintWriter pw2 = null;
        
        try 
        {
            // Buffered PrintWriter which overwrites existing files with new data.
            pw = new PrintWriter(new BufferedWriter(new FileWriter(UserFileIO.customerFilepath, false)));
            pw2 = new PrintWriter(new BufferedWriter(new FileWriter(UserFileIO.adminFilepath, false)));
            
            // Prints out column headers.
            pw.println("LoginID, Password, Name, Phone, Email, Address, Card Number, Card Holder");
            pw2.println("LoginID, Password, Name, Email");
           
            // Writes data from the passed in Hash Map onto the file specified.          
            for (Map.Entry<String, User> user : users.entrySet()) 
            {
                if(user.getValue() instanceof Customer) { // Check if user is an instance of a Customer...
                    pw.println(user.getValue().toString()); // Write data to customer_database.csv.
                } else if(user.getValue() instanceof Administrator) { // Check if user is an instance of an Administrator...
                    pw2.println(user.getValue().toString()); // Write data to admin_database.csv
                }
            }
        } catch (FileNotFoundException fe) { 
            System.err.println("File not found.");
            writeSuccess = false;
        } catch (IOException e) {
            System.err.println("Error writing onto file.");
            writeSuccess = false;
        } finally {
            if (pw != null && pw2 != null) {
                // Flushes then closes the PrintWriter objects.
                pw.close();
                pw2.close();
                writeSuccess = true;
            }    
        }
        
        return writeSuccess;
    }
}
