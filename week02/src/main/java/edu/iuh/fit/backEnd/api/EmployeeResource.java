package edu.iuh.fit.backEnd.api;

import edu.iuh.fit.backEnd.models.Employee;
import edu.iuh.fit.backEnd.services.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/employees")
public class EmployeeResource {
    private final EmployeeService employeeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    public EmployeeResource() {
        employeeService = new EmployeeService();
    }
    @GET
    @Produces("application/json")
    public Response getAll() {
           Response.ResponseBuilder response = Response.ok();
              List<Employee> employees = employeeService.getAll();
                response.entity(employees);
                return response.build();
    }

    @POST
    @Consumes("application/json")
    public Response insertEmployee(Employee employee) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            employeeService.insertEmployee(employee);
            response = Response.status(Response.Status.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @POST
    @Path("/{id}")
    @Consumes("application/json")
    public Response updateEmployee(@PathParam("id") long id, Employee employee) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            employee.setId(id);
            if (employeeService.updateEmployee(employee)) {
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response deleteEmployee(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            if (employeeService.deleteEmployee(id)) {
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }


    @PUT
    @Path("/{id}/active")
    public Response updateStatus(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            if (employeeService.updateStatus(id)) {
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

    @PUT
    @Path("/{id}/rest")
    public Response restEmployee(@PathParam("id") long id) {
        Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        try {
            if (employeeService.restEmployee(id)) {
                response = Response.status(Response.Status.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response.build();
    }

}
