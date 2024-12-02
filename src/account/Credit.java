package account;

import main.Customer;

/**
 * Represents a Credit account, which is a type of Account.
 * A credit account has a maximum limit and allows deposits and withdrawals within that limit.
 */
public class Credit extends Account {
    
    /** The maximum credit limit */
    private int max;

    /**
     * Default constructor for the Credit account.
     */
    public Credit() {
        // Default constructor
    }

    /**
     * Constructor to initialize a Credit account with an account number, balance, and credit limit.
     * 
     * @param num the account number
     * @param bal the initial account balance
     * @param max the maximum credit limit
     */
    public Credit(int num, double bal, int max) {
        super(num, bal);
        this.max = max;
    }

    /**
     * Constructor to initialize a Credit account with an owner, account number, balance, and credit limit.
     * 
     * @param customer the owner of the account
     * @param num the account number
     * @param bal the initial account balance
     * @param max the maximum credit limit
     */
    public Credit(Customer customer, int num, double bal, int max) {
        super(customer, num, bal);
        this.max = max;
    }

    /**
     * Deposits an amount to the credit account if the amount is positive and reduces the negative balance.
     * 
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    @Override
    public boolean deposit(double amount) {
        if (canDeposit(amount)) {
            accountBalance -= amount;
            return true;
        }
        return false;
    }

    
    /** 
     * @param amount
     * @return boolean
     */
    public boolean canDeposit(double amount){
        return amtIsPositive(amount) && amtAllowed(amount);
    }

    
    /** 
     * @param amount
     * @return boolean
     */
    private boolean amtAllowed(double amount){
        if(amount <= accountBalance){
            return true;
        } else {
            System.out.println("Depositing too much!");
            System.out.println("Amount: $" + amount);
            System.out.println("Balance owed: $" + accountBalance);
            return false;
        }
    }

    /**
     * Withdraws an amount from the credit account if the amount does not exceed the credit limit.
     * 
     * @param amount the amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    @Override
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            accountBalance += amount;
            return true;
        }
        return false;
    }

    
    /** 
     * @param amount
     * @return boolean
     */
    protected boolean amtExists(double amount) {
        if (amount + accountBalance <= max) {
            return true;
        } else {
            System.out.println("Not enough funds in account!");
            System.out.println("Requested: $" + amount);
            System.out.println("Balance owed: $" + accountBalance);
            System.out.println("Max: $" + max);
            return false;
        }
    }

    /**
     * Gets the maximum credit limit of the account.
     * 
     * @return the maximum credit limit
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum credit limit of the account.
     * 
     * @param max the new maximum credit limit
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the account type.
     * 
     * @return a string of the account type as "Credit"
     */
    public String getAccountType() {
        return CREDIT;
    }
}

