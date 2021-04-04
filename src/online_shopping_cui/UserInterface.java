package online_shopping_cui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class UserInterface {
    public static final String filePath = "./resources/customers.txt";
    //public static LinkedHashMap < String, String > data = new LinkedHashMap < > ();

    private void mainMenu() {
        System.out.println("\n\t1. Login");
        System.out.println("\t2. Create Account");
        // TODO view product without login or register?
        System.out.println("\t3. Exit");
    }

    public void menuSelections() {
        Scanner scanner = new Scanner(System.in);
        boolean stop = true;
        boolean stop2 = true;
        boolean stop3 = true;


        do {
            try {
                mainMenu();
                System.out.print("\nPlease Choose Your Option: ");
                int uAnswer = scanner.nextInt();
                scanner.nextLine();
                switch (uAnswer) {
                    // Choose To Login As Administrator Or Customer
                    case 1:
                        do {
                            try {
                                //readFile(data);
                                System.out.println("\n\t1. Login As Customer");
                                System.out.println("\t2. Login As Administrator");
                                System.out.print("\nPlease Choose Your Option: ");
                                uAnswer = scanner.nextInt();
                                scanner.nextLine();

                                switch (uAnswer) {
                                    case 1:
                                        customerLogin(scanner);
                                        stop3 = false;
                                        break;
                                    case 2:
                                        adminLogin(scanner);
                                        stop3 = false;
                                        break;
                                    default:
                                        throw new IndexOutOfBoundsException();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("Please Enter The Correct Options, 1 - 2");
                                System.err.flush();
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.err.println("Please Enter The Correct Options, 1 - 2");
                                System.err.flush();
                                scanner.nextLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } while (stop3);
                        break;
                    // Choose To Register As Administrator Or Customer
                    case 2:
                        do {
                            try {
                                System.out.println("\n\t1. Create Account For Customer");
                                System.out.println("\t2. Create Account For Administrator");
                                System.out.print("\nPlease Choose Your Option: ");
                                uAnswer = scanner.nextInt();
                                scanner.nextLine();

                                switch (uAnswer) {
                                    case 1:
                                        stop2 = createCustomerAccount(scanner);
                                        break;
                                    case 2:
                                        stop2 = createAdministratorAccount(scanner);
                                        break;
                                    default:
                                        throw new IndexOutOfBoundsException();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("Please Enter The Correct Options, 1 - 2");
                                System.err.flush();
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.err.println("Please Enter The Correct Options, 1 - 2");
                                System.err.flush();
                                scanner.nextLine();
                            }
                        } while (stop2);
                        break;
                    case 3:
                        System.out.println("\nGood Bye");
                        stop = false;
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
                scanner.nextLine();
            }
        } while (stop);
    }

    private boolean createCustomerAccount(Scanner scanner) {
        // TODO data validation in each prompt
        System.out.print("\nLogin ID: ");
        String loginID = scanner.nextLine();

        while (loginID.isEmpty() || loginID.contains(" ")) {
            System.out.println("Please enter a valid name!");
            System.out.print("What Is Your User Name: ");
            loginID = scanner.nextLine();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        String encryptedPassword = Utilities.encrypt(password);

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("address: ");
        String address = scanner.nextLine();


        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Card Holder Name: ");
        String cardHolder = scanner.nextLine();

        Customer customer = new Customer(loginID,encryptedPassword,name,phone,email,address,cardNumber,cardHolder);
        customer.writeCSV();

        return false;
    }

    private boolean createAdministratorAccount(Scanner scanner) {
        // TODO create admin database
        System.out.print("\nName Of The Company: ");
        String companyName = scanner.nextLine();

        System.out.print("\nUser Name: ");
        String loginID = scanner.nextLine();

        while (loginID.isEmpty() || loginID.contains(" ")) {
            System.out.println("Please enter a valid name!");
            System.out.print("What Is Your User Name: ");
            loginID = scanner.nextLine();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        String encryptedPassword = Utilities.encrypt(password);

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("address: ");
        String address = scanner.nextLine();


        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Card Holder Name: ");
        String cardHolder = scanner.nextLine();

        return false;
    }

    private void customerLogin(Scanner scanner) throws IOException {
        boolean stop = true;
        do {
            try {
                System.out.print("\n\tLogin ID: ");
                String loginID = scanner.nextLine();

                while (loginID.isEmpty() || loginID.contains(" ")) {
                    System.out.println("Please enter a valid name!");
                    System.out.print("What Is Your User Name: ");
                    loginID = scanner.nextLine();
                }

                System.out.print("\tPassword: ");
                String password = scanner.nextLine();

                Customer customer = new Customer(loginID,password);

                if (customer.checkLoginID(loginID)) {
                    if (customer.checkPassword(password)) {
                        System.out.println("\nLogin Successful, Welcome Back " + customer.getName());
                        stop = false;
                    } else {
                        System.out.println("\nWrong Password");
                    }
                } else {
                    System.out.println("Sorry, " + loginID + " is not a registered user!");
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
                scanner.nextLine();
            }
        } while (stop);

    }

    private void adminLogin(Scanner scanner) {
        System.out.print("\n\tUser Name: ");
        String loginID = scanner.nextLine();

        System.out.print("\tPassword: ");
        String password = scanner.nextLine();

        System.out.print("\tAdmin Name: ");
        String adminName = scanner.nextLine();

        System.out.print("\tAdmin Email: ");
        String adminEmail = scanner.nextLine();

        Administrator administrator = new Administrator(loginID, password, adminName, adminEmail);
    }

    // Old method to read form txt file to validate user login
    private void readFile(LinkedHashMap < String, String > data) throws FileNotFoundException {
        String password;
        String loginID;
        Scanner scanner2 = new Scanner(new BufferedReader(new FileReader(filePath)));

        while (scanner2.hasNext()) {
            loginID = scanner2.next();
            password = scanner2.next();

            data.put(loginID, password);
        }
        scanner2.close();
    }
}
