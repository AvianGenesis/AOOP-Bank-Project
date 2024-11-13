package main;

interface TMInterface {
    static final CustomerReadWriter crw = new CustomerReadWriter();
    static final ManagerReadWriter mrw = new ManagerReadWriter();


    public abstract void checkBalance(Customer customer, Account account);

    public abstract boolean deposit(Account target, double amount);

    public abstract boolean withdraw(Account target, double amount);

    public abstract boolean transfer(Account from, Account to, double amount);

    public abstract boolean pay(Account from, Account to, double amount);
    
}
