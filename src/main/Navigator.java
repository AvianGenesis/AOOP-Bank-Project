package main;

import java.util.List;
import java.util.Scanner;

public class Navigator {
    // attributes
    private Scanner scanner = new Scanner(System.in);

    public Navigator() {

    }

    
    /** 
     * @return String
     */
    // methods

    // ask to login as customer, bank manager, or create new customer
    public String displayMainMenu() {
        System.out.println("Welcome to our bank!");
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.println("| Btn |      Options       |");
        System.out.println("|--------------------------|");
        System.out.println("|  1  | Login as Customer  |");
        System.out.println("|  2  | Login as Manager   |");
        System.out.println("|  3  | Create new account |");
        System.out.println("|--------------------------|");
        System.out.println();

        String input = "";
        input = requestInput(
                "Please press the button for which option you would like OR\ntype EXIT to exit the application:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    // display customer login methods
    public String displayCustomerLogin() {
        String input = "";
        input = requestInput(
                "Please provide your [ID], [First name], [Last name], or [First name + Last name] OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @param customers
     * @return String
     */
    // display customers with same name
    public String displaySameNames(List<Customer> customers) {
        // print customers here
        String input = "";
        input = requestInput(
                "Please press the button for which account you would like to log into OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @param customer
     * @param isAdmin
     * @return String
     */
    // display accounts of requested customer
    public String displayAccounts(Customer customer, boolean isAdmin) {
        int tick = 1;
        System.out.println("Welcome back, " + customer.getFirstName().toUpperCase() + "!");
        System.out.println("Below are your accounts.");
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.println("| Btn | Acct Type | Acct # |");
        System.out.println("|--------------------------|");
        for (Account acct : customer.getAccounts()) {
            System.out.printf("| %2s  | %-8s --- %-5s |%n", tick++, acct.getAccountType(), acct.getAccountNumber());
        }
        System.out.println("|  4  | User transactions  |");
        if(isAdmin){
            System.out.println("|  5  | Bank statement     |");
        }
        System.out.println("|--------------------------|");

        String input = "";
        input = requestInput(
                "Please press the button for which account you would like to log into OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @param account
     * @return String
     */
    // prompt user with available actions for selected account
    public String displayAccountActions(Account account) {
        System.out.println("|--------------------------|");
        System.out.printf("| btn | %-8s --- %-5s |%n", account.getAccountType(), account.getAccountNumber());
        System.out.println("|--------------------------|");
        System.out.println("|  1  | Check balance      |");
        System.out.println("|  2  | Deposit            |");
        System.out.println("|  3  | Withdraw           |");
        System.out.println("|  4  | Transfer           |");
        System.out.println("|  5  | Pay                |");
        System.out.println("|--------------------------|");

        String input = "";
        input = requestInput(
                "Please press the button for which action you would like to perform OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @param account
     */
    public void displayBalanceRequest(Account account) {
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.printf("| %-8s --- %-5s       |%n", account.getAccountType(), account.getAccountNumber());
        System.out.println("|--------------------------|");
        System.out.printf("| Balance: $%-,12.2f   |%n", account.getAccountBalance());
        if (account.getAccountType() == "Credit") {
            System.out.printf("| Max: $%-,12d       |%n", ((Credit) account).getMax());
        }
        System.out.println("|--------------------------|");
    }

    
    /** 
     * @return String
     */
    public String displayDepositRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to deposit [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayWithdrawRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to withdraw [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayTransferAmtRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to transfer [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayTransferTargetRequest() {
        String input = "";
        input = requestInput("Please provide the account number you would like to transfer to OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayPayAmtRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to pay [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayPayTargetRequest() {
        String input = "";
        input = requestInput("Please provide the name of who you would like to transfer to OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayAdminOptions(){
        System.out.println("|----------------------------|");
        System.out.println("|      Bank Manager View     |");
        System.out.println("|----------------------------|");
        System.out.println("|  1  | Inspect Customer     |");
        System.out.println("|  2  | Read in transactions |");
        System.out.println("|----------------------------|");
        String input = "";
        input = requestInput("Please press the button for which action you would like to perform OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayTransactionFileRequest(){
        String input = "";
        input = requestInput("Please provide the name of the Transactions file you would like to execute OR\ntype BACK:\n");
        return input;
    }

    
    /** 
     * @return String
     */
    public String displayFirstNameReq(){
        String in = requestInput("Please provide your first name for the account");
        return in;
    }

    
    /** 
     * @return String
     */
    public String displayLastNameReq(){
        String in = requestInput("Please Provide your last name for the account");
        return in;
    }

    
    /** 
     * @return String
     */
    public String displayDOBReq(){
        String in = requestInput("Please provide you Date of Birth: 11/11/11");
        return in;
    }

    
    /** 
     * @return String
     */
    public String displayAddressReq(){
        String in = requestInput("Please provide your current living address");
        return in;
    }

    
    /** 
     * @return String
     */
    public String displayCityReq(){
        String in = requestInput("Please provide the city you live in");
        return in;
    }

    
    /** 
     * @return String
     */
    public String displayStateReq(){
        String in = requestInput("Please provide the state");
        return in;
    }

    
    /** 
     * @return int
     */
    public int displayZipRequest(){
        //int in = requestInput("Please provide the zip code");
        //return in;
        return 0;
    }

    
    /** 
     * @return String
     */
    public String displayPhoneNumReq(){
        String in = requestInput("Please provide a Phone Number");
        return in;
    }

    
    /** 
     * @param input
     */
    // default input read error
    public void displayGenericInputError(String input) {
        System.out.println("Command [" + input + "] unrecognized, please press ENTER and try again.");
        scanner.nextLine();
    }

    
    /** 
     * @param msg
     * @return String
     */
    private String requestInput(String msg) {
        String input = "";

        System.out.print(msg);
        System.out.print("| ");
        input = scanner.nextLine();

        return input;
    }
}
