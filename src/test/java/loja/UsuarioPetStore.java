package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UsuarioPetStore {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void loginUser()
    {
        String login = "charlie";
        String senha = "brown";

        String token =
        given()
            .contentType("application/json")
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/user/login?username="+login+"&password="+senha)
        .then()
            .log().all()
            .statusCode(200)
                //Comparar se contem a string na na sua composição
            .body("message", containsString("logged in user session:"))
            .extract()
            .path("message")
        ;

        System.out.println("**************** "+token.substring(23));
    }

    @Test
    public void incluirUsuario() throws IOException {
        String usuario = lerJson("data/Usuario.json");

        given()
            .contentType("application/json")
            .log().all()
            .body(usuario)
        .when()
            .post("https://petstore.swagger.io/v2/user")
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("message", is("15"));
    }

    @Test
    public void consultarUsuario()
    {
        given()
            .contentType("application/json")
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/user/caio123")
        .then()
            .log().all()
            .statusCode(200)
            .body("username", is("caio123"))
        ;
    }

    @Test
    public void alterarUsuario() throws IOException {
        String alteracao = lerJson("data/UsuarioAlterar.json");

        given()
            .contentType("application/json")
            .log().all()
                .body(alteracao)
        .when()
            .put("https://petstore.swagger.io/v2/user/caio123")
        .then()
            .log().all()
            .statusCode(200)
            .body("message",is("15"))
        ;
    }

    @Test
    public void excluirUsuario()
    {
        given()
            .contentType("application/json")
            .log().all()
        .when()
            .delete("https://petstore.swagger.io/v2/user/caio123")
        .then()
            .log().all()
            .statusCode(200)
            .body("message",is("caio123"))
        ;
    }
}
