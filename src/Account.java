public abstract class Account {
    private Customer accountOwner;
    private int accountNumber;
    protected double accountBalance;

    public Account() {

    }

    public Account(int num, double bal) {
        this.accountNumber = num;
        this.accountBalance = bal;
    }

    public Account(Customer accountOwner, int accountNumber, double accountBalance) {
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    // Getter and Setter methods
    public Customer getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            accountBalance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0) {
            if (amount <= accountBalance) {
                accountBalance -= amount;
                return true;
            }
        }
        return false;
    }

    public abstract String getAccountType();

}
