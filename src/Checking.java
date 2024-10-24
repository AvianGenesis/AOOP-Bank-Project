public class Checking extends Account{
    public Checking(){

    }

    public Checking(int num, double bal){
        super(num, bal);
    }
    public Checking(Customer customer, int num, double bal){
        super(customer, num, bal);
    }
    public String getAccountType() {
        return "Checking";
    }
}
