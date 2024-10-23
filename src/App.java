import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        List<Customer> customers = new ArrayList<Customer>();
        HashMap<Integer, Account> accounts = new HashMap<>();
        final String file = "resources/BankUsers.csv";
        customers = loadCustomers(file);

        Scanner scanner = new Scanner(System.in);
        String input;
        Customer activeCustomer = null;
        Account activeAccount = null;
        Account receivingAccount = null;

        class Mode {
            public int EXIT = 0;
            public int CREDENTIALS = 1;
            public int CHOOSE_ACCOUNT = 2;
            public int CHOOSE_ACTION = 3; // choosing to inquire about a balance does not require an additional mode
            public int DEPOSIT = 4;
            public int WITHDRAW = 5;
            public int SEND = 6;
        }
        Mode modes = new Mode();
        int uiMode = modes.CREDENTIALS;

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        while (uiMode != modes.EXIT) {
            if (uiMode == modes.CREDENTIALS) {
                System.out.println("Please provide your ID to log in OR");
                System.out.println("type EXIT to exit the application:");
                System.out.print("| ");
                input = scanner.nextLine();
                int uId = tryParseInt(input);
                if (uId != -1) {
                    if ((activeCustomer = findCustomer(uId, customers)) != null) {
                        uiMode = modes.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Unrecognized ID, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("EXIT")) {
                    uiMode = modes.EXIT;
                } else {
                    System.out.println("Unknown command, please try again.");
                }
                System.out.println();
            } else if (uiMode == modes.CHOOSE_ACCOUNT) {
                // welcome msg
                int tick = 1;
                System.out.println("Welcome back " + activeCustomer.getFirstName().toUpperCase() + "!");
                System.out.println("Below are your accounts.");
                System.out.println();
                System.out.println("|--------------------------|");
                System.out.println("| Btn | Acct Type | Acct # |");
                System.out.println("|--------------------------|");
                for (int acct : activeCustomer.getAccounts()) {
                    System.out.printf("| %2s  | %-8s -- %-6s |%n", tick++, "Checking", acct);
                }
                System.out.println("|--------------------------|");
                System.out.println();
                System.out.println("Please press the button for which account you would like to access OR");
                System.out.println("type BACK or EXIT:");
                System.out.print("| ");
                input = scanner.nextLine();
                int btn = tryParseInt(input);
                if (btn != -1 && btn <= activeCustomer.getAccounts().length) {
                    // activeAccount = accounts.get(activeCustomer.getAccounts()[btn - 1]);
                    // uiMode = modes.CHOOSE_ACTION;
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = modes.CREDENTIALS;
                } else if (input.trim().toUpperCase().equals("EXIT")) {
                    uiMode = modes.EXIT;
                } else {
                    System.out.println("Unknown command, please try again.");
                }
                System.out.println();
            }
        }
        System.out.println("Thank you for banking with us today! Please press ENTER to close the application.");
        scanner.nextLine();
        scanner.close();
    }

    static List<Customer> loadCustomers(String file) throws FileNotFoundException, IOException {
        List<Customer> ret = new ArrayList<Customer>();
        String[] values;
        int id;
        String firstName;
        String lastName;
        String dob;
        String address;
        String city;
        String state;
        int zip;
        String phone;
        int chkNum;
        double chkBal;
        int savNum;
        double savBal;
        int creNum;
        int creMax;
        double creBal;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String headers = br.readLine();
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                id = Integer.parseInt(values[0]);
                firstName = values[1];
                lastName = values[2];
                dob = values[3];
                address = values[4];
                city = values[5];
                state = values[6];
                zip = Integer.parseInt(values[7]);
                phone = values[8];
                chkNum = Integer.parseInt(values[9]);
                chkBal = Double.parseDouble(values[10]);
                savNum = Integer.parseInt(values[11]);
                savBal = Double.parseDouble(values[12]);
                creNum = Integer.parseInt(values[13]);
                creMax = Integer.parseInt(values[14]);
                creBal = Double.parseDouble(values[15]);

                Customer newCust = new Customer(firstName, lastName, dob, address, city, state, zip, phone, id,
                        new int[] { chkNum, savNum, creNum });
                ret.add(newCust);
            }
        }
        return ret;
    }

    static Customer findCustomer(int acctId, List<Customer> customers) {
        for (Customer cust : customers) {
            if (acctId == cust.getidNumber()) {
                return cust;
            }
        }
        return null;
    }

    static int tryParseInt(String str) {
        int ret = -1;
        try {
            ret = Integer.parseInt(str);
        } catch (Throwable e) {
        }
        return ret;
    }
}