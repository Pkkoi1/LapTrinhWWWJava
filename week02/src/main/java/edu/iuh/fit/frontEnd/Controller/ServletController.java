package edu.iuh.fit.frontEnd.Controller;


import edu.iuh.fit.backEnd.models.Employee;
import edu.iuh.fit.frontEnd.Model.EmployeeModel;
import edu.iuh.fit.frontEnd.Model.ProductModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/controls")
public class ServletController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Object obj = request.getParameter("action");
            if (obj != null) {
                String action = obj.toString();
                switch (action) {
                    case "insert_employee":
                        EmployeeModel employeeModel = new EmployeeModel();
                        employeeModel.insertEmployee(request, response);
                        break;
                    case "insert_products":
                        ProductModel productModel = new ProductModel();
                        productModel.insertProduct(request, response);
                        break;
                    default:
                        response.sendRedirect("index.jsp");
                        break;
                }
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Object obj = request.getParameter("action");
            if (obj != null) {
                String action = obj.toString();
                switch (action) {
                    case "delete_product":
                        ProductModel productModel = new ProductModel();
                        productModel.deleteProduct(request, response);
                        break;
                    case "delete_employee":
                        EmployeeModel employeeModel = new EmployeeModel();
                        employeeModel.deleteEmployee(request, response);
                        break;
                    case "active_employee":
                        EmployeeModel employeeModel2 = new EmployeeModel();
                        employeeModel2.activeEmployee(request, response);
                        break;
                    case "active_product":
                        ProductModel productModel2 = new ProductModel();
                        productModel2.activeProduct(request, response);
                        break;
                    default:
                        response.sendRedirect("index.jsp");
                        break;
                }
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


}
