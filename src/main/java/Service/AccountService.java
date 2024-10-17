package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerUser(Account account) {
        
        if(account.getUsername() == null || account.getUsername().isEmpty()) {
            return null;
        }

        if(account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        if(accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }

        accountDAO.createAccount(account);
        return account;
    }


    public Account loginUser(String username, String password) {
        Account account = accountDAO.getAccount(username, password);
        if (account != null) {
            return account; // Return the account if login is successful
        }
        return null; // Return null if login fails
    }

    public Account getAccountById(int accountId) {
        return accountDAO.getAccountByID(accountId);
    }
}
