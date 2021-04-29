package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Servico {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Create/Incluir/POST
    @Test
    public void IncluirPet() throws IOException {
        //Ler o conteudo do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                             //Dado que
            .contentType("application/json")//tipo de conteudo da requisição
                                            //"text/xml" para web services comuns
                                            //"application/json" para APIs REST
            .log().all()                    //gera um log completo da requisição
            .body(jsonBody)                 //Conteúdo do corpo da requisição
        .when()                             //Quando
            .post("https://petstore.swagger.io/v2/pet") //operação e endpoint
        .then()                             //Então
            .log().all()                    //Gera um log completo da resposta
            .statusCode(200)                //Valida ocódigo de status da requisição como 200
            //.body("code", is(200)) //Validao code como 200
            .body("id",is(4203))   //Validou a tag id com o conteúdo esperado
            .body("tags.name",contains("adotar")) //Validou a tag name, filha da tags
            .body("name", is("Lili"));//Validou a tag name como Lili

        System.out.println("********** EXECUTOU **********");

    }
}