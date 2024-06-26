package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        if(account.getUsername().length() > 0 && account.getUsername().length() < 256 && account.getPassword().length() >= 4 && account.getPassword().length() < 256)
        if(accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account retrieveAccount(String username){
        return accountDAO.getAccountByUsername(username);
    }

    public boolean exists(Account account){
        if(accountDAO.getAccountByUsername(account.getUsername()) != null){
            return true;
        }
        return false;
    }
}
