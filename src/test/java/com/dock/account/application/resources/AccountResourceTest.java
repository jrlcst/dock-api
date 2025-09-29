package com.dock.account.application.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.dock.common.TestsUtils.readJson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class AccountResourceTest {

    @Test
    @DisplayName("POST /api/account - deve abrir conta com sucesso para um portador existente")
    void openAccount_success() {

        var customerResp = given()
                .contentType(ContentType.JSON)
                .body(readJson("account/create-customer-success.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(200);

        final String document = customerResp.extract().path("document.value");

        given()
                .contentType(ContentType.JSON)
                .body("{\"document\":\"" + document + "\"}")
        .when()
                .post("/account")
        .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("customer.document", equalTo(document))
                .body("status", equalTo("ACTIVE"));
    }

    @Test
    @DisplayName("POST /api/account - deve retornar 422 ao tentar abrir uma segunda conta para o mesmo portador")
    void openAccount_duplicate_returns422() {
        var customerResp = given()
                .contentType(ContentType.JSON)
                .body(readJson("account/create-customer-duplicate.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(200);

        final String document = customerResp.extract().path("document.value");

        given()
                .contentType(ContentType.JSON)
                .body("{\"document\":\"" + document + "\"}")
        .when()
                .post("/account")
        .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body("{\"document\":\"" + document + "\"}")
        .when()
                .post("/account")
        .then()
                .statusCode(422)
                .body("status", equalTo(422))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Já existe uma conta vinculada a este portador."));
    }

    @Test
    @DisplayName("POST /api/account - deve retornar 422 quando portador não existe")
    void openAccount_customerNotFound_returns422() {
        given()
                .contentType(ContentType.JSON)
                .body(readJson("account/open-account-nonexistent.json"))
        .when()
                .post("/account")
        .then()
                .statusCode(422)
                .body("status", equalTo(422))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Portador não encontrado"));
    }

    @Test
    @DisplayName("GET/POST /api/account - deve consultar saldo, detalhar conta, depositar e validar novo saldo")
    void getDetails_balance_and_deposit_flow() {
        var customerResp = given()
                .contentType(ContentType.JSON)
                .body(readJson("account/create-customer-flow.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(200);

        final String document = customerResp.extract().path("document.value");

        var resp =
                given()
                        .contentType(ContentType.JSON)
                        .body("{\"document\":\"" + document + "\"}")
                .when()
                        .post("/account")
                .then()
                        .statusCode(200);

        String accountId = resp.extract().path("id");
        String agency = resp.extract().path("agency");
        String number = resp.extract().path("number");

        given()
        .when()
                .get("/account/{id}/balance", accountId)
        .then()
                .statusCode(200)
                .body("accountId", equalTo(accountId))
                .body("balance", equalTo("0.00"))
                .body("status", equalTo("ACTIVE"));

        given()
                .contentType(ContentType.JSON)
                .body(readJson("account/deposit-100.json"))
        .when()
                .post("/account/{id}/deposit", accountId)
        .then()
                .statusCode(200)
                .body("accountId", equalTo(accountId))
                .body("balance", equalTo("100.00"))
                .body("status", equalTo("ACTIVE"));

        given()
        .when()
                .get("/account/number/{number}/agency/{agency}/document/{doc}", number, agency, document)
        .then()
                .statusCode(200)
                .body("id", equalTo(accountId))
                .body("customer.document", equalTo(document))
                .body("agency", equalTo(agency))
                .body("number", equalTo(number));
    }
}
