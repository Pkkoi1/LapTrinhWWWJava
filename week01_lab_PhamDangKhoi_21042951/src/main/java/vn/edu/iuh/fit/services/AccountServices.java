package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.entities.Account;

import java.util.List;

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

    public List<Account> getListAccount(String username) {
        return accountRepository.getListAccount(username);
    }

    public boolean addAccount(Account account) {
        return accountRepository.addAccount(account);
    }

    public List<Account> getAllAccount() {
        return accountRepository.getAllAccount();
    }

    public boolean deleteAccount(String username) {
        return accountRepository.deleteAccount(username);
    }

    public boolean insertGrantAccess(String accountId, String roleId) {
        return accountRepository.insertGrantAccess(accountId, roleId);
    }

    public boolean updateAccount(String username, String password, String fullName, String email, String phone, Byte status) {
        return accountRepository.updateAccount(username, password, fullName, email, phone, status);
    }

    public boolean deleteGrantAccess(String accountId, String roleId) {
        return accountRepository.deleteGrantAccess(accountId, roleId);
    }
}
