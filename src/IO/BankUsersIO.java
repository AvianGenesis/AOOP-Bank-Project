package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import account.Account;
import account.Checking;
import account.Credit;
import account.Savings;
import main.Customer;
import main.CustomersManager;

public class BankUsersIO {

    static final String INPUT_CSV = "resources/BankUsers.csv";
    static final String OUTPUT_CSV = "resources/BankUsersOutput.csv";
    static final DecimalFormat DF = new DecimalFormat("$#,##0.00");

    static final String ID = "Identification Number";
    static final String FIRST_NAME = "First Name";
    static final String LAST_NAME = "Last Name";
    static final String DOB = "Date of Birth";
    static final String ADDRESS = "Address";
    static final String CITY = "City";
    static final String STATE = "State";
    static final String ZIP = "Zip";
    static final String PHONE_NUMBER = "Phone Number";
    static final String CHECKING_NUMBER = "Checking Account Number";
    static final String CHECKING_BALANCE = "Checking Starting Balance";
    static final String SAVINGS_NUMBER = "Savings Account Number";
    static final String SAVINGS_BALANCE = "Savings Starting Balance";
    static final String CREDIT_NUMBER = "Credit Account Number";
    static final String CREDIT_MAX = "Credit Max";
    static final String CREDIT_BALANCE = "Credit Starting Balance";

    private class DataPoint {
        public String head;
        public String data;
        public int loc;

        public DataPoint(String head) {
            this.head = head;
        }
    }

    List<String> headers;
    List<DataPoint> points = new ArrayList<DataPoint>();
    DataPoint id = new DataPoint(ID);
    DataPoint firstName = new DataPoint(FIRST_NAME);
    DataPoint lastName = new DataPoint(LAST_NAME);
    DataPoint dob = new DataPoint(DOB);
    DataPoint address = new DataPoint(ADDRESS);
    DataPoint city = new DataPoint(CITY);
    DataPoint state = new DataPoint(STATE);
    DataPoint zip = new DataPoint(ZIP);
    DataPoint phone = new DataPoint(PHONE_NUMBER);
    DataPoint chkNum = new DataPoint(CHECKING_NUMBER);
    DataPoint chkBal = new DataPoint(CHECKING_BALANCE);
    DataPoint savNum = new DataPoint(SAVINGS_NUMBER);
    DataPoint savBal = new DataPoint(SAVINGS_BALANCE);
    DataPoint creNum = new DataPoint(CREDIT_NUMBER);
    DataPoint creMax = new DataPoint(CREDIT_MAX);
    DataPoint creBal = new DataPoint(CREDIT_BALANCE);

    public BankUsersIO() {
        points.add(id);
        points.add(firstName);
        points.add(lastName);
        points.add(dob);
        points.add(address);
        points.add(city);
        points.add(state);
        points.add(zip);
        points.add(phone);
        points.add(chkNum);
        points.add(chkBal);
        points.add(savNum);
        points.add(savBal);
        points.add(creNum);
        points.add(creMax);
        points.add(creBal);
    }

    /**
     * @param accounts
     * @return CustomersManager
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CustomersManager loadCustomers(List<Account> accounts) throws FileNotFoundException, IOException {
        List<Customer> ret = new ArrayList<Customer>();
        String[] values;

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_CSV))) {
            String line;
            headers = new ArrayList<String>(Arrays.asList(br.readLine().split(",")));
            for (DataPoint dp : points) {
                dp.loc = headers.indexOf(dp.head);
            }

            while ((line = br.readLine()) != null) {
                values = line.split(",");
                for (DataPoint dp : points) {
                    dp.data = values[dp.loc];
                }

                Customer newCust = new Customer(firstName.data, lastName.data, dob.data, address.data, city.data,
                        state.data, Integer.parseInt(zip.data), phone.data, Integer.parseInt(id.data));
                Account newChk = new Checking(newCust, Integer.parseInt(chkNum.data), Double.parseDouble(chkBal.data));
                Account newSav = new Savings(newCust, Integer.parseInt(savNum.data), Double.parseDouble(savBal.data));
                Account newCre = new Credit(newCust, Integer.parseInt(creNum.data), -Double.parseDouble(creBal.data),
                        Integer.parseInt(creMax.data));
                accounts.add(newChk);
                accounts.add(newSav);
                accounts.add(newCre);
                newCust.setAccounts(new Account[] { newChk, newSav, newCre });
                ret.add(newCust);
            }
        }

        return new CustomersManager(ret);
    }

    /**
     * @param customers
     */
    public void writeChanges(CustomersManager customers) {
        // String changes = "Identification Number,First Name,Last Name,Date of
        // Birth,Address,City,State,Zip,Phone Number,Checking Account Number,Checking
        // Starting Balance,Savings Account Number,Savings Starting Balance,Credit
        // Account Number,Credit Max,Credit Starting Balance\n";
        String changes = headers.get(0);
        for (int i = 1; i < headers.size(); i++) {
            changes += "," + headers.get(i);
        }
        changes += "\n";
        String[] newLine = new String[16];
        for (Customer cust : customers.getCustomers()) {
            newLine[id.loc] = String.valueOf(cust.getidNumber());
            newLine[firstName.loc] = cust.getFirstName();
            newLine[lastName.loc] = cust.getLastName();
            newLine[dob.loc] = cust.getDob();
            newLine[address.loc] = cust.getAddress();
            newLine[city.loc] = cust.getCity();
            newLine[state.loc] = cust.getState();
            newLine[zip.loc] = String.valueOf(cust.getZip());
            newLine[phone.loc] = cust.getPhoneNumber();
            newLine[chkNum.loc] = String.valueOf(cust.getAccounts()[0].getAccountNumber());
            newLine[chkBal.loc] = String.valueOf(cust.getAccounts()[0].getAccountBalance());
            newLine[savNum.loc] = String.valueOf(cust.getAccounts()[1].getAccountNumber());
            newLine[savBal.loc] = String.valueOf(cust.getAccounts()[1].getAccountBalance());
            newLine[creNum.loc] = String.valueOf(cust.getAccounts()[2].getAccountNumber());
            newLine[creMax.loc] = String.valueOf(((Credit) cust.getAccounts()[2]).getMax());
            newLine[creBal.loc] = "-" + String.valueOf(cust.getAccounts()[2].getAccountBalance());

            changes += newLine[0];
            for (int i = 1; i < 16; i++) {
                changes += "," + newLine[i];
            }
            changes += "\n";
        }
        try (FileWriter writer = new FileWriter(OUTPUT_CSV, false)) {
            writer.write(changes);
        } catch (IOException e) {
            System.err.println("Error writing to BankUsersOutput.csv file: " + e.getMessage());
        }
    }
}
