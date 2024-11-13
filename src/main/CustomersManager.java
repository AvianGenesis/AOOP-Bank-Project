package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomersManager {
    List<Customer> customers = new ArrayList<Customer>();

    public CustomersManager(List<Customer> customers) {
        this.customers = customers;
    }

    public Customer searchById(int id) {
        for (Customer cust : customers) {
            if (id == cust.getidNumber()) {
                return cust;
            }
        }

        return null;
    }

    public Customer searchByName(String first, String last) {
        //System.out.println("Checking: " + first.toUpperCase() + " " + last.toUpperCase());
        for (Customer cust : customers) {
            //System.out.println(cust.getFirstName().toUpperCase() + " " + cust.getLastName().toUpperCase());
            if (first.toUpperCase().equals(cust.getFirstName().toUpperCase())
                    && last.toUpperCase().equals(cust.getLastName().toUpperCase())) {
                return cust;
            }
        }

        System.out.println("No customer found with name " + first + " " + last);
        return null;
    }

    public List<Customer> searchByName(String name) {
        List<Customer> ret = new ArrayList<Customer>();
        for (Customer cust : customers) {
            if (name.toUpperCase().equals(cust.getFirstName().toUpperCase())
                    || name.toUpperCase().equals(cust.getLastName().toUpperCase())) {
                ret.add(cust);
            }
        }

        return ret;
    }

    public List<Customer> getCustomers() {
        return customers;
    }//privat method to get last Customer
    private Customer getLastCustomer() {
        return customers.stream()
                .max(Comparator.comparingInt(Customer::getidNumber)) // Find the customer with the highest ID
                .orElse(null); // Return null if the list is empty
    }

    public int getLastCustomerId() {
        Customer lastID = getLastCustomer();
        return lastID.getidNumber()+1;

    }

    public int getLastChecking(){
        
        Customer lastCustomer = getLastCustomer(); 
        Account[] AccArr = lastCustomer.getAccounts();
        int lastChecking = AccArr[0].getAccountNumber();
        return lastChecking+1;
        
    }

    public int getLastSaving(){
        Customer lastCustomer = getLastCustomer();
        Account[] AccArr = lastCustomer.getAccounts();
        int lastSaving = AccArr[1].getAccountNumber();
        return lastSaving+1;
        
    }

    public int getLastCredit(){
        Customer lastCustomer = getLastCustomer(); 
        Account[] AccArr = lastCustomer.getAccounts();
        int lastCredit = AccArr[2].getAccountNumber();
        return lastCredit+1;
        
    }

    public void addNewCustomer(Customer newCust){
        customers.add(newCust);
        System.out.println("You Have Successfully Made An Account!");
    }
}