package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.entities.Account;

public class AccountServices {

    private AccountRepository accountRepository;

    public AccountServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public boolean login(String username, String password) {
        return accountRepository.login(username, password);
    }

    public Account getAccount(String username) {
        return accountRepository.getAccount(username);
    }

    public boolean addAccount(Account account) {
        return accountRepository.addAccount(account);
    }


}
