package edu.iuh.fit.backEnd.api;

import edu.iuh.fit.backEnd.models.Product;
import edu.iuh.fit.backEnd.services.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }
}
