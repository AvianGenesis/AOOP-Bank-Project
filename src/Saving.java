public class Saving extends Account{
    public Saving(){

    }

    public Saving(int num, double bal){
        super(num,bal);
    }
    public Saving(Customer customer, int num, double bal){
        super(customer, num, bal);
    }
    public String getAccountType() {
        return "Saving";
    }
}
