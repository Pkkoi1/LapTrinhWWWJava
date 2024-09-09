package vn.edu.iuh.fit.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.iuh.fit.entities.Account;
import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.repositories.LogRepository;
import vn.edu.iuh.fit.repositories.RoleRepository;
import vn.edu.iuh.fit.services.AccountServices;
import vn.edu.iuh.fit.services.RoleServices;

import java.io.IOException;
import java.io.PrintWriter;
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
    Account currentAccount;
    Map<Account, String> currentAccountMap;

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
                    String roleId = roleServices.getRoleIdByAccountId(username);
                    role = roleId;
                    name = username;
                    pass = password;
                    loginTime = new Date();
                    if (roleId == null) {
                        resp.sendRedirect("/error.jsp");
                        return;
                    } else if (roleId.equals("admin")) {
                        enable = "";

                        Map<Account, String> accountMap = RoleServices.gellAccountAndRole();

                        accessDashboard(req, resp, accountMap);
                    } else {
                        enable = "disabled";

                        Map<Account, String> accountMap = RoleServices.getRoleByAccountId(name);

                        userAccessDashboard(req, resp, accountMap);
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
//                    out.println("Success");
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
                } else {
                    out.println("Error");
                }
                break;
            case "edit":
                String accountId2 = req.getParameter("accountId");
                Account account1 = accountServices.getAccount(accountId2);
                String roleId = roleServices.getRoleIdByAccountId(accountId2);

                session.setAttribute("account", account1);
                session.setAttribute("username", name);
                session.setAttribute("role", role);
                session.setAttribute("userRole", roleId);
                session.setAttribute("enable", enable);
                resp.sendRedirect("/update.jsp");
                break;
            case "update":
                String accountId3 = req.getParameter("accountId");
                String password3 = req.getParameter("password");
                String fullName3 = req.getParameter("fullName");
                String email3 = req.getParameter("email");
                String phone3 = req.getParameter("phone");
                String newRoleId3 = req.getParameter("role");
                String status3 = req.getParameter("status");
                String roleId1 = roleServices.getRoleIdByAccountId(accountId3);

                if (accountServices.updateAccount(accountId3, password3, fullName3, email3, phone3, Byte.parseByte(status3))) {
                    accountServices.deleteGrantAccess(accountId3, roleId1);
                    accountServices.insertGrantAccess(accountId3, newRoleId3);

                    String userRole = roleServices.getRoleIdByAccountId(name);
                    role = userRole;
                    if (userRole.equals("admin")) {
                        enable = "";
                        Map<Account, String> accountMapUpdate = RoleServices.gellAccountAndRole();
                        accessDashboard(req, resp, accountMapUpdate);
                    } else {
                        enable = "disabled";
                        Map<Account, String> accountMapUpdate = RoleServices.getRoleByAccountId(name);
                        userAccessDashboard(req, resp, accountMapUpdate);
                    }

                } else {
                    out.println("Error");
                }
                break;
            case "show":
                String roleId2 = req.getParameter("role");
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
                session.invalidate();
                logoutTime = new Date();
                logRepository.addLog(name, loginTime, logoutTime);
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

        String roleId = roleServices.getRoleIdByAccountId(name);
        role = roleId;
        Account account = accountServices.getAccount(name);

        HttpSession session = req.getSession(true);
        session.setAttribute("account", account);
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

        String roleId = roleServices.getRoleIdByAccountId(name);
        role = roleId;
        Account account = accountServices.getAccount(name);

        HttpSession session = req.getSession(true);
        session.setAttribute("account", account);
        session.setAttribute("role", roleId);
        session.setAttribute("List_Account", map);
        session.setAttribute("enable", enable);
        resp.sendRedirect("/dashboard.jsp");

    }


}
