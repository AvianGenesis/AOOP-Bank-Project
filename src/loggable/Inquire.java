package loggable;

import account.Account;
import account.Credit;

public class Inquire extends AccountAction {
    public Inquire(Account from) {
        this.from = from;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = "Inquiry";
        statement[3] = "";
        statement[4] = "";
        statement[5] = "";
    }

    public boolean action() {
        System.out.println();
        System.out.println("|--------------------------|");
        System.out.printf("| %-8s --- %-5s       |%n", from.getAccountType(), from.getAccountNumber());
        System.out.println("|--------------------------|");
        System.out.printf("| Balance: $%-,12.2f   |%n", from.getAccountBalance());
        if (from.getAccountType() == "Credit") {
            System.out.printf("| Max: $%-,12d       |%n", ((Credit) from).getMax());
        }
        System.out.println("|--------------------------|");
        return true;
    }

    public String getReport() {
        String message = String.format("Inquired balance of \n", getAcctInfo());

        return message;
    }

    public String[] getStatement(){
        return statement;
    }

    public String getLog() {
        String name = getOwner().getName();

        String message = String.format("%s performed a balance inquiry on %s. Its balance is $%s\n",
                name, getAcctInfo(), from.getAccountBalance());

        return message;
    }

    public String getSuccess(){
        return "";
    }

    public String getType() {
        return "Inquire";
    }
}
