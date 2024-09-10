package vn.edu.iuh.fit.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.iuh.fit.entities.Account;
import vn.edu.iuh.fit.entities.Role;
import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.repositories.LogRepository;
import vn.edu.iuh.fit.repositories.RoleRepository;
import vn.edu.iuh.fit.services.AccountServices;
import vn.edu.iuh.fit.services.RoleServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/login", "/update", "/error", "/dashboard", "/add", "/logout", "/register"})
public class ControllerServlet extends HttpServlet {
    String enable = "disabled";
    String name = "";
    String pass = "";
    String role = "";

    Date loginTime;
    Date logoutTime;

    String actionDetail = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isLogin = false;

        String action = req.getParameter("action");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (action == null) {
            resp.sendRedirect("/error.jsp");
            return;
        }
        AccountRepository accountRepository = new AccountRepository();
        AccountServices accountServices = new AccountServices(accountRepository);

        RoleRepository roleRepository = new RoleRepository();
        RoleServices roleServices = new RoleServices(roleRepository);

        LogRepository logRepository = new LogRepository();
        HttpSession session = req.getSession(true);

        PrintWriter out = resp.getWriter();

        switch (action) {
            case "login":
                if (accountServices.login(username, password)) {
                    List<String> roles = roleServices.getRoleOfAccount(username);
                    name = username;
                    pass = password;
                    loginTime = new Date();
                    boolean isAdmin = roles.contains("admin");

                    actionDetail = "Login. \n";

                    if (!isAdmin) {
                        enable = "disabled";
                        role = "user";
                        Map<Account, String> accountMap = RoleServices.getRoleByAccountId(name);
                        userAccessDashboard(req, resp, accountMap);
                    } else {

                        enable = "";
                        role = "admin";
                        Map<Account, String> accountMap = RoleServices.gellAccountAndRole();
                        accessDashboard(req, resp, accountMap);
                    }
                } else {
                    resp.sendRedirect("/error.jsp");
                }
                break;
            case "add":
                String accountId = req.getParameter("accountId");
                String password1 = req.getParameter("password");
                String fullName = req.getParameter("fullName");
                String email = req.getParameter("email");
                String phone = req.getParameter("phone");
                String newRoleId = req.getParameter("role");
                Account account = new Account();
                account.setAccountId(accountId);
                account.setPassword(password1);
                account.setFullName(fullName);
                account.setEmail(email);
                account.setPhone(phone);
                account.setStatus((byte) 1);
                if (accountServices.addAccount(account)) {
                    accountServices.insertGrantAccess(accountId, newRoleId);
                    Map<Account, String> accountMap = RoleServices.gellAccountAndRole();

                    accessDashboard(req, resp, accountMap);
                    actionDetail = new StringBuilder(actionDetail).append("Add account: ").append(accountId).append(". \n").toString();
                } else {
//                    resp.sendRedirect("/error.jsp");
                    out.println("Error");
                }

                break;
            case "delete":
                String accountId1 = req.getParameter("accountId");
                if (accountServices.deleteAccount(accountId1)) {
                    Map<Account, String> accountMap = RoleServices.gellAccountAndRole();
                    accessDashboard(req, resp, accountMap);
                    actionDetail = new StringBuilder(actionDetail).append("Delete account: ").append(accountId1).append(". \n").toString();
                } else {
                    out.println("Error");
                }
                break;
            case "edit":
                String accountId2 = req.getParameter("accountId");
                Account account1 = accountServices.getAccount(accountId2);
                List<Role> roleId = roleServices.getRoleName();
                List<String> userRoles = roleServices.getRoleOfAccount(accountId2);

                session.setAttribute("account", account1);
                session.setAttribute("username", name);
                session.setAttribute("role", role);
                session.setAttribute("List_role", roleId);
                session.setAttribute("userRole", userRoles);
                session.setAttribute("enable", enable);
                resp.sendRedirect("/update.jsp");
                break;
            case "update":
                String accountId3 = req.getParameter("accountId");
                String password3 = req.getParameter("password");
                String fullName3 = req.getParameter("fullName");
                String email3 = req.getParameter("email");
                String phone3 = req.getParameter("phone");
                String status3 = req.getParameter("status");
                String[] newRoleIdArray = req.getParameterValues("role");
                List<String> newRoleId3 = newRoleIdArray != null ? List.of(newRoleIdArray) : new ArrayList<>();

                List<String> userRoles3 = roleServices.getRoleOfAccount(name);
                if (accountServices.updateAccount(accountId3, password3, fullName3, email3, phone3, Byte.parseByte(status3))) {

                    actionDetail = new StringBuilder(actionDetail).append("Update account: ").append(accountId3).append(". \n").toString();
                    List<String> userRole = roleServices.getRoleOfAccount(accountId3);

                    List<String> newAdd = new ArrayList<>();
                    List<String> removedRole = new ArrayList<>();

                    for (String role : userRole) {
                        if (!newRoleId3.contains(role)) {
                            removedRole.add(role);
                        }
                    }
                    for (String role : newRoleId3) {
                        if (!userRole.contains(role)) {
                            newAdd.add(role);
                        }
                    }
                    for (String role : removedRole) {
                        accountServices.deleteGrantAccess(accountId3, role);
                    }
                    for (String role : newAdd) {
                        accountServices.insertGrantAccess(accountId3, role);
                    }

                    if (userRoles3.contains("admin")) {
                        enable = "";
                        role = "admin";
                        Map<Account, String> accountMapUpdate = roleServices.gellAccountAndRole();
                        accessDashboard(req, resp, accountMapUpdate);
                    } else {
                        enable = "disabled";
                        role = "user";
                        Map<Account, String> accountMapUpdate = roleServices.getRoleByAccountId(accountId3);
                        userAccessDashboard(req, resp, accountMapUpdate);
                    }
                } else {
                    out.println("Error");
                }
                break;
            case "show":
                String roleId2 = req.getParameter("role");
                actionDetail = new StringBuilder(actionDetail).append("Show account by role: ").append(roleId2).append(". \n").toString();

                if (roleId2.equals("admin")) {
                    Map<Account, String> accountMapShow = RoleServices.showAllAccountByRole(roleId2);
                    accessDashboard(req, resp, accountMapShow);
                } else if (roleId2.equals("user")) {
                    Map<Account, String> accountMapShow = RoleServices.showAllAccountByRole(roleId2);
                    userAccessDashboard(req, resp, accountMapShow);
                } else {
                    Map<Account, String> accountMapUpdate = RoleServices.gellAccountAndRole();
                    accessDashboard(req, resp, accountMapUpdate);
                }
                break;
            case "logout":
                actionDetail.concat("Logout. \n");
                actionDetail = new StringBuilder(actionDetail).append("Logout. \n").toString();
                session.invalidate();
                logoutTime = new Date();
                logRepository.addLog(name, loginTime, logoutTime, actionDetail);
                resp.sendRedirect("/login.html");
                break;
            case "register":
                String accountIdNew = req.getParameter("accountId");
                String password1New = req.getParameter("password");
                String fullNameNew = req.getParameter("fullName");
                String emailNew = req.getParameter("email");
                String phoneNew = req.getParameter("phone");
                Account accountNew = new Account();
                accountNew.setAccountId(accountIdNew);
                accountNew.setPassword(password1New);
                accountNew.setFullName(fullNameNew);
                accountNew.setEmail(emailNew);
                accountNew.setPhone(phoneNew);
                accountNew.setStatus((byte) 1);
                String defaultRole = "user";
                if (accountServices.addAccount(accountNew)) {
                    accountServices.insertGrantAccess(accountIdNew, defaultRole);
                    resp.sendRedirect("/login.html");
                } else {
                    out.println("Error");
                }
                break;
            case "showAddAccountPage":
                List<Role> roleIds = roleServices.getRoleName();
                req.setAttribute("roleId", roleIds);
                req.getRequestDispatcher("/add.jsp").forward(req, resp);
                break;
            default:
                out.println("Invalid action");
                break;
        }

    }

    public void accessDashboard(HttpServletRequest req, HttpServletResponse resp, Map<Account, String> map) throws ServletException, IOException {
        AccountRepository accountRepository = new AccountRepository();
        AccountServices accountServices = new AccountServices(accountRepository);

        RoleRepository roleRepository = new RoleRepository();
        RoleServices roleServices = new RoleServices(roleRepository);

        List<String> roleIds = roleServices.getRoleOfAccount(name);
        String role = String.join(", ", roleIds);
        List<Role> roles = roleServices.getRoleName();


        Account account = accountServices.getAccount(name);

        for (Map.Entry<Account, String> entry : map.entrySet()) {
            Account acc = entry.getKey();
            List<String> userRoles = roleServices.getRoleOfAccount(acc.getAccountId());
            String rolesAsString = String.join(", ", userRoles);
            entry.setValue(rolesAsString);
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("account", account);
        session.setAttribute("role_List", roleIds);
        session.setAttribute("List", roles);
        session.setAttribute("role", role);
        session.setAttribute("List_Account", map);
        session.setAttribute("enable", enable);
        resp.sendRedirect("/dashboard.jsp");
    }


    public void userAccessDashboard(HttpServletRequest req, HttpServletResponse resp, Map<Account, String> map) throws ServletException, IOException {
        AccountRepository accountRepository = new AccountRepository();
        AccountServices accountServices = new AccountServices(accountRepository);

        RoleRepository roleRepository = new RoleRepository();
        RoleServices roleServices = new RoleServices(roleRepository);

        List<String> roleIds = roleServices.getRoleOfAccount(name);
        String role = String.join(", ", roleIds);

        Account account = accountServices.getAccount(name);
        List<Role> roles = roleServices.getRoleName();

        for (Map.Entry<Account, String> entry : map.entrySet()) {
            Account acc = entry.getKey();
            List<String> userRoles = roleServices.getRoleOfAccount(acc.getAccountId());
            String rolesAsString = String.join(", ", userRoles);
            entry.setValue(rolesAsString);
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("account", account);
        session.setAttribute("role", role);
        session.setAttribute("role_List", roles);
        session.setAttribute("List", roles);
        session.setAttribute("List_Account", map);
        session.setAttribute("enable", enable);

        resp.sendRedirect("/dashboard.jsp");
    }


}
