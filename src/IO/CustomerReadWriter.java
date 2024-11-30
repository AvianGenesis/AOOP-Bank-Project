package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import account.Account;
import account.AccountTypes;
import loggable.AccountAction;
import main.Customer;

public class CustomerReadWriter implements AccountTypes{
    static final String OUTPUT_LOG = "resources/Log.txt";

    /**
     * 
     * @param transFile
     * @return List of String[]
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String[]> readTransactions(String transFile) throws FileNotFoundException, IOException { // FIX
        List<String[]> ret = new ArrayList<String[]>();

        try (BufferedReader br = new BufferedReader(new FileReader("resources/" + transFile))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                ret.add(line.split(","));
            }
        } catch (Exception e) {
            System.out.println("Unrecognized file");
        }
        return ret;
    }

    /**
     * @param action
     */
    public void logAction(String action) {
        try (FileWriter writer = new FileWriter(OUTPUT_LOG, true)) {
            writer.write(action + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void generateReport(Customer customer) throws IOException {
        String message = "";

        Account checking = customer.searchAccounts(CHECKING);
        Account savings = customer.searchAccounts(SAVINGS);
        Account credit = customer.searchAccounts(CREDIT);
        try (FileWriter writer = new FileWriter("resources/User" + customer.getidNumber() + "Transactions.txt",
                false)) {
            message += ("ID: " + customer.getidNumber() + "\n");
            message += ("Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            message += ("Timestamp: " + Instant.now() + "\n");
            message += ("Checking start balance: $" + checking.getStartBalance() + "\n");
            message += ("Checking end balance: $" + checking.getAccountBalance() + "\n");
            message += ("Savings start balance: $" + savings.getStartBalance() + "\n");
            message += ("Savings end balance: $" + savings.getAccountBalance() + "\n");
            message += ("Credit start balance: $" + credit.getStartBalance() + "\n");
            message += ("Credit end balance: $" + credit.getAccountBalance() + "\n");
            for (AccountAction act : customer.getLogs()) {
                message += (act.getReport());
            }
            writer.write(message);
        } catch (IOException e) {
            System.out.println("Unable to generate report");
        }
    }

    /**
     * @param customer
     * @throws IOException
     */
    public void generateStatement(Customer customer) throws IOException {
        String message = "";

        try (FileWriter writer = new FileWriter("resources/User" + customer.getidNumber() + "BankStatement.txt",
                false)) {
            message += ("ID: " + customer.getidNumber() + "\n");
            message += ("Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            message += ("Address: " + customer.getAddress() + ", " + customer.getCity() + ", " + customer.getState()
                    + " " + customer.getZip() + "\n");
            message += ("Phone: " + customer.getPhoneNumber() + "\n");
            message += ("Timestamp: " + Instant.now() + "\n");
            message += ("\n");
            message += ("|-------------------------------------------------------------------------------------|\n");
            message += ("| Sender ID | Sending Account | Description | Receiver ID | Receiver Account | Amount |\n");
            message += ("|-------------------------------------------------------------------------------------|\n");
            for (AccountAction act : customer.getLogs()) {
                String[] statement = act.getStatement();
                message += (String.format("| %-9s | %-15s | %-11s | %-11s | %-16s | %-6s |\n",
                        statement[0], statement[1], statement[2], statement[3], statement[4], statement[5]));
            }
            message += ("|-------------------------------------------------------------------------------------|\n");
            writer.write(message);
        } catch (IOException e) {
            System.out.println("Unable to generate statement");
        }
    }

}
