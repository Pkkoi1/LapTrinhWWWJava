package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.entities.Account;
import vn.edu.iuh.fit.entities.Role;
import vn.edu.iuh.fit.repositories.RoleRepository;

import java.util.List;
import java.util.Map;

public class RoleServices {

    static RoleRepository roleRepository;

    public RoleServices(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String getRoleIdByAccountId(String accountId) {
        return roleRepository.getRoleIdByAccountId(accountId);
    }

    public static Map<Account, String> getRoleByAccountId(String accountId) {
        return roleRepository.getRoleByAccountId(accountId);
    }

    public static Map<Account, String> gellAccountAndRole() {
        return roleRepository.gellAccountAndRole();
    }

    public static Map<Account, String> showAllAccountByRole(String role) {
        return roleRepository.showAllAccountByRole(role);
    }

    public static Map<Account, String> showAccountByRole(String username, String role) {
        return roleRepository.showAccountByRole(username, role);
    }

    public List<Role> getRoleName() {
        return roleRepository.getRoleName();
    }

    public List<String> getRoleOfAccount(String accountId) {
        return roleRepository.getRoleOfAccount(accountId);
    }
}
