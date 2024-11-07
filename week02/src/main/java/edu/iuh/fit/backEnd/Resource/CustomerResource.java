package edu.iuh.fit.backEnd.Resource;


import edu.iuh.fit.backEnd.models.Customer;
import edu.iuh.fit.backEnd.services.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/customers")
public class CustomerResource {
    private final CustomerService customerService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    public CustomerResource() {
        customerService = new CustomerService();
    }

    @GET
    @Produces("application/json")
    public Response getAll() {
        Response.ResponseBuilder response = Response.ok();
        response.entity(customerService.getAll());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.ok();
        response.entity(customerService.findById(id));
        return response.build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response insertCustomer(Customer customer) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            customerService.insertCustomer(customer);
            response = Response.status(Response.Status.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }
}
