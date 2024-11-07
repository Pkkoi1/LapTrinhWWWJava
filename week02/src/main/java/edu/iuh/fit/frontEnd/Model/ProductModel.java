package edu.iuh.fit.frontEnd.Model;

import edu.iuh.fit.backEnd.enums.ProductStatus;
import edu.iuh.fit.backEnd.models.Product;
import edu.iuh.fit.backEnd.models.Productimage;
import edu.iuh.fit.backEnd.models.Productprice;
import edu.iuh.fit.backEnd.models.ProductpriceId;
import edu.iuh.fit.backEnd.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.text.DefaultFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class ProductModel {
    private final ProductService productService = new ProductService();

    public void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String unit = request.getParameter("unit");
        String manufacturer = request.getParameter("manufacturer");
        String status = request.getParameter("status");
        String[] priceParts = new String[0];
        String priceParam = request.getParameter("price");
        String price = "";
        String priceDateTime = "";
        if (priceParam != null && !priceParam.isEmpty()) {
            priceParts = priceParam.split(";");
            price = priceParts[1];
            priceDateTime = priceParts[0];
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date priceDate = null;
        try {
            priceDate = dateFormat.parse(priceDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String priceNew = request.getParameter("priceNew");
        String note = request.getParameter("noteNew");
        String finalPrice = "";

        Product product = new Product(name, description, unit, manufacturer, ProductStatus.valueOf(status));
        productService.insertProduct(product);

        Date date = new Date();
        Long proId = product.getId();
        ProductpriceId productpriceId = new ProductpriceId();

        if (priceNew != null && !priceNew.isEmpty()) {
            finalPrice = priceNew;
            productpriceId.setProductId(proId);
            productpriceId.setPriceDateTime(date);

            Productprice productprice = new Productprice(productpriceId, product, Double.parseDouble(finalPrice), note);
            productService.insertProductPrice(productprice);
        } else {
            finalPrice = price;
            productpriceId.setProductId(proId);
            productpriceId.setPriceDateTime(priceDate);
            Productprice productprice = new Productprice(productpriceId, product, Double.parseDouble(finalPrice), note);
            productService.insertProductPrice(productprice);
        }

        String imgFile = request.getParameter("imageFile");
        String imgURL = request.getParameter("imageUrl");

        List<String> list = new ArrayList<>();
        if (imgFile != null && !imgFile.isEmpty()) {
            list.add(imgFile);
        } else if (imgURL != null && !imgURL.isEmpty()) {
            list.add(imgURL);
        }
        String alt = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmGS8dZ2pWxkEKMhN11hkrU2SiFTnyzrHRcg&s";

        for (String img : list) {
            if (img != null && !img.isEmpty()) {
                Productimage productimage = new Productimage(product, img, alt);
                productService.insertImage(productimage);
            }
        }

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
