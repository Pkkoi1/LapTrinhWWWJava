package edu.iuh.fit.backEnd.config;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class Applications extends Application  {
    private static final long serialVersionUID = 1L;

    public Applications() {
        super();
    }





}
