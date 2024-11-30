package loggable;

import IO.CustomerReadWriter;
import main.Customer;

public class GenerateStatement implements Loggable {
    Customer customer;

    public GenerateStatement(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean action() {
        CustomerReadWriter rw = new CustomerReadWriter();
        try {
            rw.generateStatement(customer);
            return true;
        } catch (Exception e) {

        }
        System.out.println("Statement generation failed");
        return false;
    }

    @Override
    public String getLog() {
        String message = String.format("Manager generated bank statement for %s", customer.getName());

        return message;
    }

    @Override
    public String getType() {
        return GENSTATEMENT;
    }

    @Override
    public String getSuccess() {
        String message = String.format("Generated bank statement for %s", customer.getName());

        return message;
    }

}
