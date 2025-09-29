package com.dock.costumer.application.resources;

import com.dock.common.commands.CommandBus;
import com.dock.costumer.application.api.request.CreateCustomerRequest;
import com.dock.costumer.application.commands.CreateCustomerCommand;
import com.dock.costumer.application.api.response.CustomerResponse;
import com.dock.costumer.application.commands.DeleteCustomerCommand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/costumer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@RequiredArgsConstructor
public class CostumerResource {

    private final CommandBus commandBus;

    @POST
    public Response createCustomer(@Valid CreateCustomerRequest request) {

        final var command = new CreateCustomerCommand(
                request.fullName(),
                request.document()
        );

        final CustomerResponse response = commandBus.dispatch(command);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{document}")
    public Response deleteCustomer(@PathParam("document") String document) {
        final var command = new DeleteCustomerCommand(document);
        commandBus.dispatch(command);
        return Response.noContent().build();
    }
}
