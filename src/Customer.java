/**
 * Represents a customer in the banking system, inheriting properties from the Person class.
 * A customer has an ID number and an array of associated accounts (e.g., checking, saving, credit).
 */
public class Customer extends Person {
    
    /** The unique identification number for the customer */
    private int idNumber;

    /** An array representing the customer's accounts: [checking, saving, credit] */
    private int[] accounts; 

    /**
     * Default constructor for the Customer class.
     */
    public Customer() {
        // Default constructor
    }

    /**
     * Constructor to initialize a Customer with personal details, ID number, and accounts.
     * 
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @param dob the date of birth of the customer
     * @param address the address of the customer
     * @param city the city of residence
     * @param state the state of residence
     * @param zip the zip code of the address
     * @param phoneNumber the phone number of the customer
     * @param idNumber the unique identification number for the customer
     * @param accounts an array of account identifiers for the customer (e.g., checking, saving, credit)
     */
    public Customer(String firstName, String lastName, String dob, String address, String city, String state, int zip, String phoneNumber, int idNumber, int[] accounts) {
        super(firstName, lastName, dob, address, city, state, zip, phoneNumber);
        this.idNumber = idNumber;
        this.accounts = accounts;
    }

    /**
     * Gets the unique identification number of the customer.
     * 
     * @return the ID number of the customer
     */
    public int getidNumber() {
        return idNumber;
    }

    /**
     * Sets the unique identification number for the customer.
     * 
     * @param idNumber the new ID number for the customer
     */
    public void setidNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Gets the array of account identifiers for the customer.
     * 
     * @return an array representing the customer's accounts
     */
    public int[] getAccounts() {
        return accounts;
    }

    /**
     * Sets the array of account identifiers for the customer.
     * 
     * @param accounts the new array of account identifiers for the customer
     */
    public void setAccounts(int[] accounts) {
        this.accounts = accounts;
    }
}
