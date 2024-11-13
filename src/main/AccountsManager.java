package main;

import java.util.ArrayList;
import java.util.List;

public class AccountsManager {
    private List<Account> accounts = new ArrayList<Account>();

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

    //public List<Account> searchByName()

    public List<Account> getAccounts(){
        return accounts;
    }
}
