package vn.edu.iuh.fit.frontend.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.iuh.fit.frontend.models.CalculatorModel;

import java.io.IOException;

@WebServlet(name = "controller",value ="/controller" )
public class ControllerServlet extends HttpServlet {
    @Inject
    private CalculatorModel model;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
//        model=new CalculatorModel();

        if(action.equalsIgnoreCase("add")){
            String a_param = req.getParameter("a");
            String b_param = req.getParameter("b");
            int result = model.add(Integer.parseInt(a_param),
                    Integer.parseInt(b_param));
            HttpSession session = req.getSession(true);
            session.setAttribute("result",result);
            resp.sendRedirect("views/addview.jsp");
        }
    }
}
