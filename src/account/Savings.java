package account;

import main.Customer;

/**
 * Represents a Savings account, which is a type of Account.
 * A savings account typically allows deposits and withdrawals
 */
public class Savings extends Account {

    /**
     * Default constructor for the Saving account.
     */
    public Savings() {
        // Default constructor
    }

    /**
     * Constructor to initialize a Savings account with an account number and balance.
     * 
     * @param num the account number
     * @param bal the initial account balance
     */
    public Savings(int num, double bal) {
        super(num, bal);
    }

    /**
     * Constructor to initialize a Savings account with an owner, account number, and balance.
     * 
     * @param customer the owner of the account
     * @param num the account number
     * @param bal the initial account balance
     */
    public Savings(Customer customer, int num, double bal) {
        super(customer, num, bal);
    }

    /**
     * Gets the account type.
     * 
     * @return a string indicating the account type as "Saving"
     */
    @Override
    public String getAccountType() {
        return SAVINGS;
    }
}

