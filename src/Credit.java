public class Credit extends Account {
    int max;

    public Credit() {

    }

    public Credit(int num, double bal, int max) {
        super(num, bal);
        this.max = max;
    }

    public Credit(Customer customer, int num, double bal, int max) {
        super(customer, num, bal);
        this.max = max;
    }

    public boolean deposit(double amount) {
        if (amount > 0 && amount < -accountBalance) {
            accountBalance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount + -accountBalance <= max) {
            accountBalance -= amount;
            return true;
        }
        return false;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getAccountType() {
        return "Credit";
    }
}
