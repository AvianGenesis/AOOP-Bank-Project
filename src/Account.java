public abstract class Account {
    private Customer accountOwner;
    private int accountNumber;
    protected double accountBalance; 

    public Account(){
        
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

    public void deposit(double amount) {
        
    }

   
    public void withdraw(double amount) {
        
    }


}
