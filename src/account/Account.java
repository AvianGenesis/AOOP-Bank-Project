package account;

import main.Customer;

/**
 * Abstract class representing a bank account.
 * Contains basic account details and common methods for deposit and withdrawal
 * operations.
 */
public abstract class Account {

    /** The owner of the account */
    private Customer accountOwner;

    /** The unique account number */
    private int accountNumber;

    /** The balance of the account */
    protected double accountBalance;

    protected double startBalance;

    /**
     * Default constructor for the Account class.
     */
    public Account() {
        // Default constructor
    }

    /**
     * Constructor to initialize account number and balance.
     * 
     * @param num the account number
     * @param bal the initial account balance
     */
    public Account(int num, double bal) {
        this.accountNumber = num;
        this.accountBalance = bal;
        this.startBalance = bal;
    }

    /**
     * Constructor to initialize the account with an owner, number, and balance.
     * 
     * @param accountOwner   the owner of the account
     * @param accountNumber  the unique account number
     * @param accountBalance the initial account balance
     */
    public Account(Customer accountOwner, int accountNumber, double accountBalance) {
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.startBalance = accountBalance;
    }

    /**
     * Gets the owner of the account.
     * 
     * @return the account owner
     */
    public Customer getAccountOwner() {
        return accountOwner;
    }

    /**
     * Sets the owner of the account.
     * 
     * @param accountOwner the new owner of the account
     */
    public void setAccountOwner(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    /**
     * Gets the account number.
     * 
     * @return the account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number.
     * 
     * @param accountNumber the new account number
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the current account balance.
     * 
     * @return the current account balance
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets the account balance.
     * 
     * @param accountBalance the new account balance
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getStartBalance(){
        return startBalance;
    }

    /**
     * Deposits an amount to the account balance if the amount is positive.
     * 
     * @param amount the amount to deposit
     * @return true if the deposit was successful; false otherwise
     */
    public boolean deposit(double amount) {
        if (canDeposit(amount)) {
            accountBalance += amount;
            return true;
        }
        return false;
    }

    public boolean canDeposit(double amount) {
        return amtIsPositive(amount);
    }

    /**
     * Withdraws an amount from the account balance if the amount is positive
     * and does not exceed the current balance.
     * 
     * @param amount the amount to withdraw
     * @return true if the withdrawal was successful; false otherwise
     */
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            accountBalance -= amount;
            return true;
        }
        return false;
    }

    public boolean canWithdraw(double amount) {
        return amtIsPositive(amount) && amtExists(amount);
    }

    protected boolean amtIsPositive(double amount) {
        if (amount > 0.0) {
            return true;
        } else {
            System.out.println("Amount must be positive!\n");
            return false;
        }
    }

    protected boolean amtExists(double amount) {
        if (amount <= accountBalance) {
            return true;
        } else {
            System.out.println("Not enough funds in account!");
            System.out.println("Requested: $" + amount);
            System.out.println("Available: $" + accountBalance);
            return false;
        }
    }

    /**
     * Abstract method to get the account type (e.g., Checking, Saving, Credit).
     * 
     * @return the account type as a String
     */
    public abstract String getAccountType();
}
