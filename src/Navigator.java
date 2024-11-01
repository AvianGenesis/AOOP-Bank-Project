import java.util.HashMap;
import java.util.Scanner;

public class Navigator {
    // attributes
    Scanner scanner = new Scanner(System.in);

    void Navigator() {

    }

    // methods

    // ask to login as customer, bank manager, or create new customer
    public String displayMainMenu() {
        System.out.println("Welcome to our bank!");
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.println("| Btn |      Options       |");
        System.out.println("|--------------------------|");
        System.out.println("|  1  | Login as Customer  |");
        System.out.println("|  2  | Login as Manager   |");
        System.out.println("|  3  | Create new account |");
        System.out.println("|--------------------------|");
        System.out.println();

        String input = "";
        input = requestInput(
                "Please press the button for which option you would like OR\ntype EXIT to exit the application:\n");
        return input;
    }

    // display customer login methods
    public String displayCustomerLogin() {
        String input = "";
        input = requestInput(
                "Please provide your [ID], [First name], [Last name], or [First name + Last name] OR\ntype BACK:\n");
        return input;
    }

    // display customers with same name
    public String displaySameNames(Customer[] customers) {
        // print customers here
        String input = "";
        input = requestInput(
                "Please press the button for which account you would like to log into OR\ntype BACK:\n");
        return input;
    }

    // display accounts of requested customer
    public String displayAccounts(Customer customer, HashMap<Integer, Account> accounts) {
        int tick = 1;
        System.out.println("Welcome back, " + customer.getFirstName().toUpperCase() + "!");
        System.out.println("Below are your accounts.");
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.println("| Btn | Acct Type | Acct # |");
        System.out.println("|--------------------------|");
        for (int acct : customer.getAccounts()) {
            System.out.printf("| %2s  | %-8s --- %-5s |%n", tick++, accounts.get(acct).getAccountType(), acct);
        }
        System.out.println("|--------------------------|");

        String input = "";
        input = requestInput(
                "Please press the button for which account you would like to log into OR\ntype BACK:\n");
        return input;
    }

    // prompt user with available actions for selected account
    public String displayAccountActions(Account account) {
        System.out.println("|--------------------------|");
        System.out.printf("| btn | %-8s --- %-5s |%n", account.getAccountType(), account.getAccountNumber());
        System.out.println("|--------------------------|");
        System.out.println("|  1  | Check balance      |");
        System.out.println("|  2  | Deposit            |");
        System.out.println("|  3  | Withdraw           |");
        System.out.println("|  4  | Transfer           |");
        System.out.println("|  5  | Pay                |");
        System.out.println("|--------------------------|");

        String input = "";
        input = requestInput(
                "Please press the button for which action you would like to perform OR\ntype BACK:\n");
        return input;
    }

    public void displayBalanceRequest(Account account) {
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.printf("| %-8s --- %-5s       |%n", account.getAccountType(), account.getAccountNumber());
        System.out.println("|--------------------------|");
        System.out.printf("| Balance: %-8s        |%n", -account.getAccountBalance());
        if (account.getAccountType() == "Credit") {
            System.out.printf("| Max: %-8s           |%n", ((Credit) account).getMax());
        }
        System.out.println("|--------------------------|");
    }

    public String displayDepositRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to deposit [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    public String displayWithdrawRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to withdraw [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    public String displayTransferAmtRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to transfer [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    public String displayTransferTargetRequest() {
        String input = "";
        input = requestInput("Please provide the account number you would like to transfer to OR\ntype BACK:\n");
        return input;
    }

    public String displayPayAmtRequest() {
        String input = "";
        input = requestInput("Please provide how much you would like to pay [xxx.xx] OR\ntype BACK:\n");
        return input;
    }

    public String displayPayTargetRequest() {
        String input = "";
        input = requestInput("Please provide the name of who you would like to transfer to OR\ntype BACK:\n");
        return input;
    }

    // default input read error
    public void displayGenericInputError(String input) {
        System.out.println("Command [" + input + "] unrecognized, please press ENTER and try again.");
        scanner.nextLine();
    }

    public String requestInput(String msg) {
        String input = "";

        System.out.print(msg);
        System.out.print("| ");
        input = scanner.nextLine();

        return input;
    }
}
