package loggable;

import IO.ReadWriter;
import main.Customer;

public class GenerateStatement implements Loggable {
    private Customer customer;

    public GenerateStatement(Customer customer) {
        this.customer = customer;
    }

    /**
     * Generates bank statement for Customer and writes it to file
     * 
     * @return boolean
     */
    @Override
    public boolean action() {
        ReadWriter rw = new ReadWriter();
        try {
            rw.generateStatement(customer);
            return true;
        } catch (Exception e) {

        }
        System.out.println("Statement generation failed");
        return false;
    }

    /**
     * @return String
     */
    @Override
    public String getLog() {
        String message = String.format("Manager generated bank statement for %s", customer.getName());

        return message;
    }

    /**
     * @return String
     */
    @Override
    public String getType() {
        return GENSTATEMENT;
    }

    /**
     * @return String
     */
    @Override
    public String getSuccess() {
        String message = String.format("Generated bank statement for %s", customer.getName());

        return message;
    }

}
