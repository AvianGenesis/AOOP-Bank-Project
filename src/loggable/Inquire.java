package loggable;

import account.Account;
import account.AccountTypes;
import account.Credit;

public class Inquire extends AccountAction implements AccountTypes {
    public Inquire(Account from) {
        this.from = from;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = INQUIRE;
        statement[3] = "";
        statement[4] = "";
        statement[5] = "";
    }

    /**
     * Print Account inquiry information to console
     * 
     * @return boolean
     */
    public boolean action() {
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.printf("| %-8s --- %-5s       |%n", from.getAccountType(), from.getAccountNumber());
        System.out.println("|--------------------------|");
        System.out.printf("| Balance: $%-,12.2f   |%n", from.getAccountBalance());
        if (from.getAccountType() == CREDIT) {
            System.out.printf("| Max: $%-,12d       |%n", ((Credit) from).getMax());
        }
        System.out.println("|--------------------------|");
        return true;
    }

    /**
     * @return String
     */
    public String getReport() {
        String message = String.format("Inquired balance of \n", getAcctInfo());

        return message;
    }

    /**
     * @return String
     */
    public String getLog() {
        String name = getOwner().getName();

        String message = String.format("%s performed a balance inquiry on %s. Its balance is $%s\n",
                name, getAcctInfo(), from.getAccountBalance());

        return message;
    }

    /**
     * @return String
     */
    public String getSuccess() {
        return "";
    }

    /**
     * @return String
     */
    public String getType() {
        return INQUIRE;
    }
}
