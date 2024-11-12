import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadWriter {
    private static final String INPUT_CSV = "resources/BankUsers.csv";
    private static final String OUTPUT_CSV = "resources/BankUsersOutput.csv";
    private static final String OUTPUT_LOG = "resources/Log.txt";
    private static final DecimalFormat DF = new DecimalFormat("$#,##0.00");

    private static final String ID = "Identification Number";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String DOB = "Date of Birth";
    private static final String ADDRESS = "Address";
    private static final String CITY = "City";
    private static final String STATE = "State";
    private static final String ZIP = "Zip";
    private static final String PHONE_NUMBER = "Phone Number";
    private static final String CHECKING_NUMBER = "Checking Account Number";
    private static final String CHECKING_BALANCE = "Checking Starting Balance";
    private static final String SAVINGS_NUMBER = "Savings Account Number";
    private static final String SAVINGS_BALANCE = "Savings Starting Balance";
    private static final String CREDIT_NUMBER = "Credit Account Number";
    private static final String CREDIT_MAX = "Credit Max";
    private static final String CREDIT_BALANCE = "Credit Starting Balance";

    // List<String> test = new ArrayList<String>();
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

    public ReadWriter() {
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
                Account newSav = new Saving(newCust, Integer.parseInt(savNum.data), Double.parseDouble(savBal.data));
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

    public List<String[]> readTransactions(String transFile) throws FileNotFoundException, IOException { // FIX
        List<String[]> ret = new ArrayList<String[]>();
        String[] values;

        try (BufferedReader br = new BufferedReader(new FileReader("resources/" + transFile))) {
            String line;
            headers = new ArrayList<String>(Arrays.asList(br.readLine().split(",")));

            while ((line = br.readLine()) != null) {
                ret.add(line.split(","));
            }
        } 
        catch (Exception e){
            System.out.println("Unrecognized file");
        }
        return ret;
    }

    public void logBalanceInquiry(Customer customer, Account account) {
        String message = String.format("%s %s made a balance inquiry on %s-%s. %s %s’s Balance for %s-%s: %s",
                customer.getFirstName(), customer.getLastName(), account.getAccountType(),
                String.valueOf(account.getAccountNumber()),
                customer.getFirstName(), customer.getLastName(), account.getAccountType(),
                String.valueOf(account.getAccountNumber()), DF.format(account.getAccountBalance()));
        logAction(message);
    }

    public void logDeposit(Account account, double amount) { // clean up
        String message = String.format("%s %s deposited %s into %s-%s. %s %s’s New Balance for %s-%s: %s",
                account.getAccountOwner().getFirstName(), account.getAccountOwner().getLastName(),
                DF.format(amount), account.getAccountType(),
                String.valueOf(account.getAccountNumber()),
                account.getAccountOwner().getFirstName(), account.getAccountOwner().getLastName(),
                account.getAccountType(),
                String.valueOf(account.getAccountNumber()), DF.format(account.getAccountBalance()));
        logAction(message);
    }

    public void logWithdrawal(Account account, double amount) { // clean up
        String message = String.format("%s %s withdrew %s in cash from %s-%s. %s %s’s Balance for %s-%s: %s",
                account.getAccountOwner().getFirstName(), account.getAccountOwner().getLastName(), DF.format(amount),
                account.getAccountType(),
                String.valueOf(account.getAccountNumber()),
                account.getAccountOwner().getFirstName(), account.getAccountOwner().getLastName(),
                account.getAccountType(),
                String.valueOf(account.getAccountNumber()), DF.format(account.getAccountBalance()));
        logAction(message);
    }

    public void logTransfer(Account fromAccount, Account toAccount, double amount) {
        String name = fromAccount.getAccountOwner().getFirstName() + " " + fromAccount.getAccountOwner().getLastName();
        String fromType = fromAccount.getAccountType();
        String fromNum = String.valueOf(fromAccount.getAccountNumber());
        String toType = toAccount.getAccountType();
        String toNum = String.valueOf(toAccount.getAccountNumber());

        String message = String.format(
                "%s transferred %s from %s-%s to %s-%s. %s’s Balance for %s-%s: %s. %s’s Balance for %s-%s: %s", name,
                DF.format(amount), fromType, fromNum, toType, toNum, name, fromType, fromNum,
                DF.format(fromAccount.getAccountBalance()), name, toType, toNum,
                DF.format(toAccount.getAccountBalance()));
        logAction(message);
    }

    public void logPayment(Account fromAccount, Account toAccount, double amount) {
        String fromName = fromAccount.getAccountOwner().getFirstName() + " "
                + fromAccount.getAccountOwner().getLastName();
        String fromType = fromAccount.getAccountType();
        String fromNum = String.valueOf(fromAccount.getAccountNumber());
        String toName = toAccount.getAccountOwner().getFirstName() + " " + toAccount.getAccountOwner().getLastName();
        String toType = toAccount.getAccountType();
        String toNum = String.valueOf(toAccount.getAccountNumber());

        String fromMessage = String.format("%s paid %s %s from %s-%s. %s’s New Balance for %s-%s: %s",
                fromName, toName,
                DF.format(amount),
                fromType, fromNum,
                fromName, fromType,
                fromNum, DF.format(fromAccount.getAccountBalance()));
        logAction(fromMessage);

        String toMessage = String.format("%s received %s from %s. %s’s New Balance for %s-%s: %s",
                toName, DF.format(amount), fromName,
                toName, toType,
                toNum, DF.format(toAccount.getAccountBalance()));
        logAction(toMessage);
    }

    public void logAction(String action) {
        try (FileWriter writer = new FileWriter(OUTPUT_LOG, true)) {
            writer.write(action + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void writeChanges(CustomersManager customers) {
        //String changes = "Identification Number,First Name,Last Name,Date of Birth,Address,City,State,Zip,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance\n";
        String changes = headers.get(0);
        for(int i = 1; i < headers.size(); i++){
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
            for(int i = 1; i < 16; i++){
                changes += "," + newLine[i];
            }
            changes += "\n";
/*             changes += new String(
                    cust.getidNumber() + "," +
                            cust.getFirstName() + "," +
                            cust.getLastName() + "," +
                            cust.getDob() + "," +
                            cust.getAddress() + "," +
                            cust.getCity() + "," +
                            cust.getState() + "," +
                            cust.getZip() + "," +
                            cust.getPhoneNumber() + "," +
                            cust.getAccounts()[0].getAccountNumber() + "," +
                            cust.getAccounts()[0].getAccountBalance() + "," +
                            cust.getAccounts()[1].getAccountNumber() + "," +
                            cust.getAccounts()[1].getAccountBalance() + "," +
                            cust.getAccounts()[2].getAccountNumber() + "," +
                            ((Credit) cust.getAccounts()[2]).getMax() + "," +
                            cust.getAccounts()[2].getAccountBalance() + "\n"); */
        }
        try (FileWriter writer = new FileWriter(OUTPUT_CSV, false)) {
            writer.write(changes);
        } catch (IOException e) {
            System.err.println("Error writing to BankUsersOutput.csv file: " + e.getMessage());
        }
    }

}