package edu.iuh.fit.backEnd.Resource;

import edu.iuh.fit.backEnd.enums.ProductStatus;
import edu.iuh.fit.backEnd.models.Product;
import edu.iuh.fit.backEnd.models.Productprice;
import edu.iuh.fit.backEnd.models.ProductpriceId;
import edu.iuh.fit.backEnd.services.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

@Path("/products")
public class ProductResource {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    public ProductResource() {
        productService = new ProductService();
    }

    @GET
    @Produces("application/json")
    public Response getAll() {
        Response.ResponseBuilder response = Response.ok();
        response.entity(productService.getAll());
        return response.build();
    }

    @GET
    @Path("id/{id}")
    @Produces("application/json")
    public Response getProductById(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                response = Response.ok(product.get());
            } else {
                response = Response.status(Response.Status.NOT_FOUND).entity("Product not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching product by ID: " + id, e);
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error");
        }
        return response.build();
    }


    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response insertProduct(Product product) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            productService.insertProduct(product);
            response = Response.status(Response.Status.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @PUT
    @Path("/price/{id}/{price}/product/{id2}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response insertProductPrice(@PathParam("id") long id, @PathParam("price") Double price, @PathParam("id2") long id2) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            Productprice productprice = productService.findPrice(id, price);

            ProductpriceId productpriceId = new ProductpriceId(id2, productprice.getId().getPriceDateTime());
            Optional<Product> product = productService.findById(id2);

            Productprice productprice2 = new Productprice(productpriceId, product.get(), productprice.getPrice(), productprice.getNote());
            productService.insertProductPrice(productprice2);
            response = Response.status(Response.Status.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }
    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateProduct(@PathParam("id") long id, Product product) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            product.setId(id);
            if (productService.updateProduct(product)) {
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteProduct(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            if (productService.deleteProduct(id)) {
                productService.updateStatus(id, ProductStatus.TERMINATED);
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findById(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            response = Response.ok(productService.findById(id));
//            productService.getProductImages(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @PUT
    @Path("/{id}/active")
    @Produces("application/json")
    public Response activeProduct(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            if (productService.activeProduct(id)) {
                productService.updateStatus(id, ProductStatus.ACTIVE);
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @PUT
    @Path("/{id}/rest")
    @Produces("application/json")
    public Response restProduct(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            if (productService.restProduct(id)) {
                productService.updateStatus(id, ProductStatus.IN_ACTIVE);
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @GET
    @Path("/ProductImage/{id}")
    @Produces("application/json")
    public Response getProductImage(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.ok();
        response.entity(productService.getProductImages(id));
        return response.build();
    }





}
