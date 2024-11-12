import java.util.ArrayList;
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
    }
}