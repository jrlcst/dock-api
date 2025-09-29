package com.dock.account.application.resources;

import com.dock.account.application.api.request.DepositRequest;
import com.dock.account.application.api.request.GetStatementRequest;
import com.dock.account.application.api.request.OpenAccountRequest;
import com.dock.account.application.api.request.WithdrawRequest;
import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.api.response.BalanceResponse;
import com.dock.account.application.api.response.StatementResponse;
import com.dock.account.application.commands.BlockAccountCommand;
import com.dock.account.application.commands.CloseAccountCommand;
import com.dock.account.application.commands.DepositCommand;
import com.dock.account.application.commands.OpenAccountCommand;
import com.dock.account.application.commands.UnblockAccountCommand;
import com.dock.account.application.commands.WithdrawCommand;
import com.dock.account.application.queries.GetAccountDetailsQuery;
import com.dock.account.application.queries.GetBalanceQuery;
import com.dock.account.application.queries.GetStatementQuery;
import com.dock.common.commands.CommandBus;
import com.dock.common.queries.QueryBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@RequiredArgsConstructor
public class AccountResource {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @GET
    @Path("/number/{number}/agency/{agency}/document/{costumerDocument}")
    public Response getAccountDetails(@PathParam("number") @NotBlank String number,
                                      @PathParam("agency") @NotBlank String agency,
                                      @PathParam("costumerDocument") @NotBlank String document) {
        final AccountResponse response = queryBus.dispatch(new GetAccountDetailsQuery(number, agency, document));
        return Response.ok(response).build();
    }

    @GET
    @Path("/{accountId}/balance")
    public Response getBalance(@PathParam("accountId") @NotBlank String accountId) {
        final BalanceResponse response = queryBus.dispatch(new GetBalanceQuery(accountId));
        return Response.ok(response).build();
    }

    @GET
    @Path("/{accountId}/statement")
    public Response getStatement(@PathParam("accountId") @NotBlank String accountId,
                                 @Valid @BeanParam GetStatementRequest request) {
        final StatementResponse response = queryBus.dispatch(new GetStatementQuery(accountId, request.start(), request.end()));
        return Response.ok(response).build();
    }

    @POST
    public Response openAccount(@Valid OpenAccountRequest request) {
        final AccountResponse response = commandBus.dispatch(new OpenAccountCommand(request.document()));
        return Response.ok(response).build();
    }

    @POST
    @Path("/{accountId}/deposit")
    public Response deposit(@PathParam("accountId") String accountId,
                            @Valid DepositRequest request
    ) {
        final BalanceResponse response = commandBus.dispatch(
                new DepositCommand(accountId, request.amount(), request.description())
        );
        return Response.ok(response).build();
    }

    @POST
    @Path("/{accountId}/withdraw")
    public Response withdraw(@PathParam("accountId") @NotBlank String accountId,
                             @Valid WithdrawRequest request
    ) {
        final BalanceResponse response = commandBus.dispatch(
                new WithdrawCommand(accountId, request.amount(), request.description())
        );
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{accountId}/block")
    public Response block(@PathParam("accountId") @NotBlank String accountId) {
        final AccountResponse response = commandBus.dispatch(new BlockAccountCommand(accountId));
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{accountId}/unblock")
    public Response unblock(@PathParam("accountId") @NotBlank String accountId) {
        final AccountResponse response = commandBus.dispatch(new UnblockAccountCommand(accountId));
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{accountId}/close")
    public Response close(@PathParam("accountId") @NotBlank String accountId) {
        final AccountResponse response = commandBus.dispatch(new CloseAccountCommand(accountId));
        return Response.ok(response).build();
    }
}
