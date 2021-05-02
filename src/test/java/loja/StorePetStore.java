package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class StorePetStore {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void pedido() throws IOException {
        String requisicao = lerJson("data/store.json");

        given()
            .contentType("application/json")
            .log().all()
            .body(requisicao)
        .when()
            .post("https://petstore.swagger.io/v2/store/order")
        .then()
            .log().all()
            .statusCode(200)
            .body("petId", is(4203))
        ;
    }
}
