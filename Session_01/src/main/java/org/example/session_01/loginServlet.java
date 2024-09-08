package org.example.session_01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session_01.Bean.loginBean;
import org.example.session_01.Database.connectDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "loginServlet", value = "/login")
public class loginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String us = request.getParameter("username");
        String pass = request.getParameter("password");

        Connection connection = connectDatabase.getConnection();
        loginBean bean = new loginBean(connection);

        boolean result = bean.login(us, pass);
        PrintWriter out = response.getWriter();
        if (result)
            out.println("Login success");
        else
            out.println("Login fail");
    }
}