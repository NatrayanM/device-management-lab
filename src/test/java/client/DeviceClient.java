package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DeviceClient {

    private RequestSpecification reqSpec;

    public DeviceClient(){
        reqSpec = buildSpec();
    }

    private RequestSpecification buildSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://api.restful-api.dev")
                .addHeader("Content-Type", "application/json")
                .addHeader("accept", "application/json")
                .build();
    }



    public Response post(Object body, String path){

        return given()
                .spec(reqSpec)
                .body(body)
                .when()
                .post(path);
    }

    public Response post( String path){

        return given()
                .spec(reqSpec)
                .when()
                .post(path);
    }

    public Response get(String path){
        return given()
                .spec(reqSpec)
                .when()
                .get(path);
    }

    public Response update(Object body, String path){
        return  given()
                .spec(reqSpec)
                .body(body)
                .when()
                .put(path);
    }

    public Response patch(){
        return given().spec(reqSpec)
                .when()
                .patch();
    }

    public Response delete(String path){
        return given().spec(reqSpec)
                .when()
                .delete(path);
    }

}
