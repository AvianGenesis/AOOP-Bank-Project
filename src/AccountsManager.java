import java.util.ArrayList;
import java.util.List;

public class AccountsManager {
    List<Account> accounts = new ArrayList<Account>();

    public AccountsManager(List<Account> accounts){
        this.accounts = accounts;
    }

    public Account searchByNum(int num){
        for(Account acc : accounts){
            if(num == acc.getAccountNumber()){
                return acc;
            }
        }

        return null;
    }
}
