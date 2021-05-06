package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Users {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirUsuario() throws IOException {
        String json = lerJson("data/usuario.json");
        String id = "18";

        given()
            .contentType("application/json")
            .log().all()
            .body(json)
        .when()
            .post("https://petstore.swagger.io/v2/user")
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(id))
            ;
    }

    @Test
    public void consultarUsuario()
    {
        String username = "caio123";
        String lastname = "Guilherme";
        String firstname = "Caio";
        String email = "caio@guilherme.com";

        int id =  18;

        given()
            .contentType("application/json")
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/user/"+username)
        .then()
            .log().all()
            .statusCode(200)
                .body("id", is(id))
                .body("firstName", is(firstname))
                .body("lastName", is(lastname))
                .body("email", is(email))
                ;
    }

    @Test
    public void alterarUsuario() throws IOException {
        String json = lerJson("data/usuarioAlterar.json");
        String username = "caio123";
        String id = "18";
        String email = "caio@guilherme.com";
        String password = "1234567";

        given()
                .contentType("application/json")
                .log().all()
                .body(json)
            .when()
                .put("https://petstore.swagger.io/v2/user/"+username)
            .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(id))
        ;
    }

    @Test
    public void excluirUsuario()
    {
        String username = "caio123";
        String id = "18";

        given()
            .contentType("application/json")
            .log().all()
        .when()
            .delete("https://petstore.swagger.io/v2/user/"+username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(username))
        ;
    }
}
