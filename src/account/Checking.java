package account;

import main.Customer;

/**
 * Represents a Checking account, which is a subclass of Account.
 */
public class Checking extends Account {

    /**
     * Default constructor for the Checking account.
     */
    public Checking() {
       
    }

    /**
     * Constructor to initialize a Checking account with a specified account number and balance.
     * 
     * @param num the account number
     * @param bal the initial account balance
     */
    public Checking(int num, double bal) {
        super(num, bal);
    }

    /**
     * Constructor to initialize a Checking account with an owner, account number, and balance.
     * 
     * @param customer the owner of the account
     * @param num the account number
     * @param bal the initial account balance
     */
    public Checking(Customer customer, int num, double bal) {
        super(customer, num, bal);
    }

    /**
     * Gets the account type.
     * 
     * @return a string indicating the account type as "Checking"
     */
    public String getAccountType() {
        return CHECKING;
    }
}

