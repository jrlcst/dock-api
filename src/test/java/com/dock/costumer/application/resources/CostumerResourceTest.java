package com.dock.costumer.application.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.dock.common.TestsUtils.readJson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CostumerResourceTest {

    @Test
    @DisplayName("POST /api/costumer - deve criar um portador com sucesso")
    void createCustomer_success() {
        given()
                .contentType(ContentType.JSON)
                .body(readJson("costumer/create-customer-success.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("fullName", equalTo("Fulano da Silva"))
                .body("document.value", equalTo("44772904034"))
                .body("document.type", equalTo("CPF"));
    }

    @Test
    @DisplayName("POST /api/costumer - deve retornar 422 ao tentar criar portador duplicado")
    void createCustomer_duplicate_returns422() {
        given()
                .contentType(ContentType.JSON)
                .body(readJson("costumer/create-customer-duplicate.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body(readJson("costumer/create-customer-duplicate.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(422)
                .body("status", equalTo(422))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Não foi possível processar sua solicitação. Entre em contato com o suporte para mais informações."));
    }

    @Test
    @DisplayName("POST /api/costumer - deve retornar 400 com erros de validação para payload inválido")
    void createCustomer_validationErrors_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body(readJson("costumer/create-customer-invalid.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Validation failed"))
                .body("errors", notNullValue())
                .body("errors.size()", greaterThan(0))
                .body("errors.field", hasItems(anyOf(equalTo("fullName"), equalTo("document"))));
    }

    @Test
    @DisplayName("DELETE /api/costumer/{document} - deve excluir e depois retornar 422 na segunda tentativa")
    void deleteCustomer_thenSecondTimeReturns422() {
        given()
                .contentType(ContentType.JSON)
                .body(readJson("costumer/delete-customer.json"))
        .when()
                .post("/costumer")
        .then()
                .statusCode(200)
                .body("document.value", equalTo("95205956003"));
        given()
        .when()
                .delete("/costumer/{document}", "95205956003")
        .then()
                .statusCode(204);
        given()
        .when()
                .delete("/costumer/{document}", "95205956003")
        .then()
                .statusCode(422)
                .body("message", equalTo("Portador nao encontrado"));
    }
}
