package edu.iuh.fit.frontEnd.Model;

import edu.iuh.fit.backEnd.enums.ProductStatus;
import edu.iuh.fit.backEnd.models.Product;
import edu.iuh.fit.backEnd.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductModel {
    private final ProductService productService = new ProductService();

    public void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String unit = request.getParameter("unit");
        String manufacturer = request.getParameter("manufacturer");
        String status = request.getParameter("status");

        Product product = new Product(name, description, unit, manufacturer, ProductStatus.valueOf(status));
        productService.insertProduct(product);
        System.out.println(status);
        response.sendRedirect("product.jsp");
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        productService.updateStatus(id, ProductStatus.TERMINATED);
        response.sendRedirect("product.jsp");
    }

    public void activeProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        productService.updateStatus(id, ProductStatus.ACTIVE);
        response.sendRedirect("product.jsp");
    }
}
