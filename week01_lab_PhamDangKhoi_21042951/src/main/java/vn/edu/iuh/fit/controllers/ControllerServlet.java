package vn.edu.iuh.fit.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.iuh.fit.entities.Account;
import vn.edu.iuh.fit.entities.Log;
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

    AccountRepository accountRepository = new AccountRepository();
    AccountServices accountServices = new AccountServices(accountRepository);

    RoleRepository roleRepository = new RoleRepository();
    RoleServices roleServices = new RoleServices(roleRepository);

    LogRepository logRepository = new LogRepository();

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
                        List<Role> rolesList = roleServices.getRoleName();

                        userAccessDashboard(req, resp, accountMap, rolesList);
                    } else {

                        enable = "";
                        role = "admin";
                        Map<Account, String> accountMap = RoleServices.gellAccountAndRole();
                        List<Role> rolesList = roleServices.getRoleName();

                        accessDashboard(req, resp, accountMap, rolesList);
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
                    List<Role> roles = roleServices.getRoleName();

                    accessDashboard(req, resp, accountMap, roles);
                    actionDetail = new StringBuilder(actionDetail).append("Add account: ").append(accountId).append(". \n").toString();
                } else {
//                    resp.sendRedirect("/error.jsp");
                    out.println("Error");
                }

                break;
            case "delete":
                String accountId1 = req.getParameter("accountId");
                handleDeleteAccount(req, resp, accountId1);
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
                handleUpdateAccount(req, resp);
                break;
            case "show":
                String roleId2 = req.getParameter("role");
                actionDetail = new StringBuilder(actionDetail).append("Show account by role: ").append(roleId2).append(". \n").toString();

                if (roleId2.equals("admin")) {
                    Map<Account, String> accountMapShow = RoleServices.showAllAccountByRole(roleId2);
                    List<Role> roles = roleServices.getRoleName();
                    accessDashboard(req, resp, accountMapShow, roles);
                } else if (roleId2.equals("user")) {
                    Map<Account, String> accountMapShow = RoleServices.showAllAccountByRole(roleId2);
                    List<Role> roles = roleServices.getRoleName();
                    userAccessDashboard(req, resp, accountMapShow, roles);
                } else {
                    Map<Account, String> accountMapUpdate = RoleServices.gellAccountAndRole();
                    List<Role> roles = roleServices.getRoleName();

                    accessDashboard(req, resp, accountMapUpdate, roles);
                }
                break;
            case "logout":
                actionDetail.concat("Logout. \n");
                actionDetail = new StringBuilder(actionDetail).append("Logout. \n").toString();
                session.invalidate();
                logoutTime = new Date();
                logRepository.addLog(name, loginTime, logoutTime, actionDetail);
                resp.sendRedirect("/index.jsp");
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
                    resp.sendRedirect("/index.jsp");
                } else {
                    out.println("Error");
                }
                break;
            case "showAddAccountPage":
                List<Role> roleIds = roleServices.getRoleName();
                req.setAttribute("roleId", roleIds);
                req.getRequestDispatcher("/add.jsp").forward(req, resp);
                break;
            case "editRole":
                String roleIdUpdate = req.getParameter("roleId");

                Role roleUpdate = roleServices.getRole(roleIdUpdate);
                List<Role> roles = roleServices.getRoleName();
                req.setAttribute("List", roles);
                req.setAttribute("roleUpdate", roleUpdate);
                req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
                break;
            case "addRole":
                handleAddRole(req, resp);
                break;
            case "deleteRole":
                String roleIdDelete = req.getParameter("roleId");

                handleDeleteRole(req, resp, roleIdDelete);
                break;
            case "updateRole":
                handleUpdateRole(req, resp);
                break;
            default:
                out.println("Invalid action");
                break;
        }

    }

    public void accessDashboard(HttpServletRequest req, HttpServletResponse resp, Map<Account, String> map, List<Role> roles) throws ServletException, IOException {
        AccountRepository accountRepository = new AccountRepository();
        AccountServices accountServices = new AccountServices(accountRepository);

        RoleRepository roleRepository = new RoleRepository();
        RoleServices roleServices = new RoleServices(roleRepository);

        List<String> roleIds = roleServices.getRoleOfAccount(name);
        String role = String.join(", ", roleIds);
        LogRepository logRepository = new LogRepository();

//        List<Log> log = logRepository.findLogbyAccount(name);
        Account account = accountServices.getAccount(name);

        for (Map.Entry<Account, String> entry : map.entrySet()) {
            Account acc = entry.getKey();
            List<String> userRoles = roleServices.getRoleOfAccount(acc.getAccountId());
            String rolesAsString = String.join(", ", userRoles);
            entry.setValue(rolesAsString);
        }
        HttpSession session = req.getSession(true);
//        session.setAttribute("log", log);
        session.setAttribute("account", account);
        session.setAttribute("role_List", roleIds);
        session.setAttribute("List", roles);
        session.setAttribute("role", role);
        session.setAttribute("List_Account", map);
        session.setAttribute("enable", enable);
        resp.sendRedirect("/dashboard.jsp");
    }


    public void userAccessDashboard(HttpServletRequest req, HttpServletResponse resp, Map<Account, String> map, List<Role> roles) throws ServletException, IOException {
        AccountRepository accountRepository = new AccountRepository();
        AccountServices accountServices = new AccountServices(accountRepository);

        RoleRepository roleRepository = new RoleRepository();
        RoleServices roleServices = new RoleServices(roleRepository);

        List<String> roleIds = roleServices.getRoleOfAccount(name);
        String role = String.join(", ", roleIds);
        LogRepository logRepository = new LogRepository();

//        List<Log> log = logRepository.findLogbyAccount(name);
        Account account = accountServices.getAccount(name);

        for (Map.Entry<Account, String> entry : map.entrySet()) {
            Account acc = entry.getKey();
            List<String> userRoles = roleServices.getRoleOfAccount(acc.getAccountId());
            String rolesAsString = String.join(", ", userRoles);
            entry.setValue(rolesAsString);
        }
        HttpSession session = req.getSession(true);
//        session.setAttribute("log", log);
        session.setAttribute("account", account);
        session.setAttribute("role", role);
        session.setAttribute("role_List", roles);
        session.setAttribute("List", roles);
        session.setAttribute("List_Account", map);
        session.setAttribute("enable", enable);

        resp.sendRedirect("/dashboard.jsp");
    }

    public void handleDeleteAccount(HttpServletRequest req, HttpServletResponse resp, String accountID) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (accountServices.deleteAccount(accountID)) {
            Map<Account, String> accountMap = RoleServices.gellAccountAndRole();
            List<Role> roles = roleServices.getRoleName();
            accessDashboard(req, resp, accountMap, roles);
            actionDetail = new StringBuilder(actionDetail).append("Delete account: ").append(accountID).append(". \n").toString();
        } else {
            out.println("Error");
        }
    }

    /**
     * Add role
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void handleAddRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleID = req.getParameter("roleId");
        String roleName = req.getParameter("roleName");
        String description = req.getParameter("description");
        String status = req.getParameter("status");
        Role role = new Role();
        role.setRoleId(roleID);
        role.setRoleName(roleName);
        role.setDescription(description);
        role.setStatus(Byte.parseByte(status));
        if (roleServices.addRole(role)) {
            List<Role> roles = roleServices.getRoleName();
            req.setAttribute("List", roles);
            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
            actionDetail = new StringBuilder(actionDetail).append("Add role: ").append(roleID).append(". \n").toString();

        } else {
            resp.sendRedirect("/error.jsp");
        }
    }

    public void handleUpdateAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String status = req.getParameter("status");
        String[] newRoleIdArray = req.getParameterValues("role");
        if (status.equalsIgnoreCase("-1")) {
            handleDeleteAccount(req, resp, accountId);
            return;
        }
        List<String> newRoleId = newRoleIdArray != null ? List.of(newRoleIdArray) : new ArrayList<>();

        List<String> userRoles = roleServices.getRoleOfAccount(name);
        if (accountServices.updateAccount(accountId, password, fullName, email, phone, Byte.parseByte(status))) {

            actionDetail = new StringBuilder(actionDetail).append("Update account: ").append(accountId).append(". \n").toString();
            List<String> userRole = roleServices.getRoleOfAccount(accountId);

            List<String> newAdd = new ArrayList<>();
            List<String> removedRole = new ArrayList<>();

            for (String role : userRole) {
                if (!newRoleId.contains(role)) {
                    removedRole.add(role);
                }
            }
            for (String role : newRoleId) {
                if (!userRole.contains(role)) {
                    newAdd.add(role);
                }
            }
            for (String role : removedRole) {
                accountServices.deleteGrantAccess(accountId, role);
            }
            for (String role : newAdd) {
                accountServices.insertGrantAccess(accountId, role);
            }

            if (userRoles.contains("admin")) {
                enable = "";
                role = "admin";
                Map<Account, String> accountMapUpdate = roleServices.gellAccountAndRole();
                List<Role> roles = roleServices.getRoleName();

                accessDashboard(req, resp, accountMapUpdate, roles);
            } else {
                enable = "disabled";
                role = "user";
                Map<Account, String> accountMapUpdate = roleServices.getRoleByAccountId(accountId);
                List<Role> roles = roleServices.getRoleName();

                userAccessDashboard(req, resp, accountMapUpdate, roles);
            }
        } else {
            resp.sendRedirect("/error.jsp");
        }
    }

    public void handleDeleteRole(HttpServletRequest req, HttpServletResponse resp, String roleId) throws ServletException, IOException {
        if (roleServices.deleteRole(roleId)) {
            Map<Account, String> accountMapUpdate = roleServices.gellAccountAndRole();
            List<Role> roles = roleServices.getRoleName();

            accessDashboard(req, resp, accountMapUpdate, roles);
            actionDetail = new StringBuilder(actionDetail).append("Delete role: ").append(roleId).append(". \n").toString();
        } else {
            resp.sendRedirect("/error.jsp");
        }
    }

    public void handleUpdateRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleId = req.getParameter("roleIdUpdate");
        String roleName = req.getParameter("roleNameUpdate");
        String description = req.getParameter("descriptionUpdate");
        String status = req.getParameter("statusUpdate");
        if (status.equalsIgnoreCase("-1")) {
            handleDeleteRole(req, resp, roleId);
            return;
        }
        if (roleServices.updateRole(roleName, description, Byte.parseByte(status), roleId)) {
            Map<Account, String> accountMapUpdate = roleServices.gellAccountAndRole();
            List<Role> roles = roleServices.getRoleName();

            accessDashboard(req, resp, accountMapUpdate, roles);
            actionDetail = new StringBuilder(actionDetail).append("Update role: ").append(roleId).append(". \n").toString();
        } else {
            resp.sendRedirect("/error.jsp");
        }
    }
}
