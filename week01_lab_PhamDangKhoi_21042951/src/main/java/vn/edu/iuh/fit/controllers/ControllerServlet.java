package vn.edu.iuh.fit.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.services.AccountServices;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ControllerServlet", value = {"/login", "/welcome", "/error"})

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (action == null || username == null || password == null) {
            resp.sendRedirect("/error.jsp");
            return;
        }
        AccountRepository accountRepository = new AccountRepository();
        AccountServices accountServices = new AccountServices(accountRepository);

        PrintWriter out = resp.getWriter();

        switch (action) {
            case "login":
                if (accountServices.login(username, password)) {
//            out.println("Login success");

                    resp.sendRedirect("/welcome.jsp");
                } else {
                    resp.sendRedirect("/error.jsp");

//            out.println("Login fail");
                }
                break;

            default:
                System.out.println("Invalid action");
                out.println("Invalid action");
//                resp.sendRedirect("/error.jsp");

                break;
        }

    }

}
