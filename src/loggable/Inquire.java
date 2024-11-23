package loggable;

import account.Account;

public class Inquire extends AccountAction {
    Inquire(Account from) {
        this.from = from;
    }

    public boolean action() {
        // print the inquisition
        return true;
    }

    public String getReport() {
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("Inquired balance of \n", acctInfo);

        return message;
    }

    public String getLog() {
        String name = from.getAccountOwner().getName();
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("%s performed a balance inquiry on %s. Its balance is $%s\n",
                name, acctInfo, from.getAccountBalance());

        return message;
    }

    public String getType() {
        return "Pay";
    }
}
