package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Pets {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Create / Incluir / POST
    @Test
    public void incluirPet() throws IOException {
        //Ler o conteudo do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                             //Dado que
            .contentType("application/json")//tipo de conteudo da requisi��o
                                            //"text/xml" para web services comuns
                                            //"application/json" para APIs REST
            .log().all()                    //gera um log completo da requisi��o
            .body(jsonBody)                 //Conte�do do corpo da requisi��o
        .when()                             //Quando
            .post("https://petstore.swagger.io/v2/pet") //opera��o e endpoint
        .then()                             //Ent�o
            .log().all()                    //Gera um log completo da resposta
            .statusCode(200)                //Valida oc�digo de status da requisi��o como 200
            //.body("code", is(200)) //Validao code como 200
            .body("id",is(4203))   //Validou a tag id com o conte�do esperado
            .body("tags.name",contains("adotar")) //Validou a tag name, filha da tags
            .body("name", is("Lili"));//Validou a tag name como Lili

        System.out.println("********** EXECUTOU **********");

    }

    //Reach or Research / Consultar / GET
    @Test
    public void consultarPet()
    {
        String petId = "4203";

        given()
            .contentType("application/json") //Tipo de conteudo da requisi��o
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/pet/"+petId)//Mostra pelo petId
        .then()
            .log().all()
            .statusCode(200) //Validou que a opera��o foi realizada
            .body("name", is("Lili"))
            .body("category.name", is("dog"))
        ;
    }

    //Alterar / update/ PUT
    @Test
    public void alterarPet() throws IOException {
        //Ler o conteudo do arquivo pet.json
        String jsonBody = lerJson("data/petAlterar.json");

        given()                             //Dado que
                .contentType("application/json")//tipo de conteudo da requisi��o
                //"text/xml" para web services comuns
                //"application/json" para APIs REST
                .log().all()                    //gera um log completo da requisi��o
                .body(jsonBody)                 //Conte�do do corpo da requisi��o
        .when()                             //Quando
                .put("https://petstore.swagger.io/v2/pet") //opera��o e endpoint
        .then()                             //Ent�o
                .log().all()                    //Gera um log completo da resposta
                .statusCode(200)                //Valida oc�digo de status da requisi��o como 200
                //.body("code", is(200)) //Validao code como 200
                .body("id",is(4203))   //Validou a tag id com o conte�do esperado
                .body("name", is("Lili"))//Validou a tag name como Lili
                .body("status", is("adopted"));
    }

    //Delete / Excluir / DELETE
    @Test
    public void excluirPet()
    {
        String petId = "4203";

        given()
                .contentType("application/json") //Tipo de conteudo da requisi��o
                .log().all()
        .when()
                .delete("https://petstore.swagger.io/v2/pet/"+petId)//Mostra pelo petId
        .then()
                .log().all()
                .statusCode(200) //Validou que a opera��o foi realizada
                .body("code", is(200))
        ;
    }


}