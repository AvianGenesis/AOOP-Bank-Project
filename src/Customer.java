public class Customer extends Person {
    private int idNumber;
    private int[] accounts; // [checking, saving, credit]

    public Customer(){

    }

    public Customer(String firstName, String lastName, String dob, String address, String city, String state, int zip, String phoneNumber, int idNumber, int[] accounts) {
        super(firstName, lastName, dob, address, city, state, zip, phoneNumber);
        this.idNumber = idNumber;
        this.accounts = accounts;
    }

    public int getidNumber() {
        return idNumber;
    }

    public void setidNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    // Getter and Setter methods for accounts
    public int[] getAccounts() {
        return accounts;
    }

    public void setAccounts(int[] accounts) {
        this.accounts = accounts;
    }

}

