package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import account.Account;

public class CustomersManager {
    List<Customer> customers = new ArrayList<Customer>();

    public CustomersManager(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Return customer via ID
     * 
     * @param id
     * @return Customer
     */
    public Customer searchById(int id) {
        for (Customer cust : customers) {
            if (id == cust.getidNumber()) {
                return cust;
            }
        }

        return null;
    }

    /**
     * Return customer based on first and last name
     * 
     * @param first
     * @param last
     * @return Customer
     */
    public Customer searchByName(String first, String last) {
        for (Customer cust : customers) {
            if (first.toUpperCase().equals(cust.getFirstName().toUpperCase())
                    && last.toUpperCase().equals(cust.getLastName().toUpperCase())) {
                return cust;
            }
        }

        System.out.println("No customer found with name " + first + " " + last);
        return null;
    }

    /**
     * Return list of customers with the same name
     * 
     * @param name
     * @return List of customers
     */
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

    /**
     * Return class's list of customers
     * 
     * @return List of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }// privat method to get last Customer

    /**
     * Identify the lest customer in the list
     * 
     * @return Customer
     */
    private Customer getLastCustomer() {
        return customers.stream()
                .max(Comparator.comparingInt(Customer::getidNumber)) // Find the customer with the highest ID
                .orElse(null); // Return null if the list is empty
    }

    /**
     * Return ID of last customer in list
     * 
     * @return int
     */
    public int getLastCustomerId() {
        Customer lastID = getLastCustomer();
        return lastID.getidNumber() + 1;

    }

    /**
     * Return the account number of the last checking account
     * 
     * @return int
     */
    public int getLastChecking() {

        Customer lastCustomer = getLastCustomer();
        Account[] AccArr = lastCustomer.getAccounts();
        int lastChecking = AccArr[0].getAccountNumber();
        return lastChecking + 1;

    }

    /**
     * Return the account number of the last savings account
     * 
     * @return int
     */
    public int getLastSaving() {
        Customer lastCustomer = getLastCustomer();
        Account[] AccArr = lastCustomer.getAccounts();
        int lastSaving = AccArr[1].getAccountNumber();
        return lastSaving + 1;

    }

    /**
     * Return the account number of the last credit account
     * 
     * @return int
     */
    public int getLastCredit() {
        Customer lastCustomer = getLastCustomer();
        Account[] AccArr = lastCustomer.getAccounts();
        int lastCredit = AccArr[2].getAccountNumber();
        return lastCredit + 1;

    }

    /**
     * Add new customer to the list
     * 
     * @param newCust
     */
    public void addNewCustomer(Customer newCust) {
        customers.add(newCust);
        System.out.println("You Have Successfully Made An Account!");
    }
}