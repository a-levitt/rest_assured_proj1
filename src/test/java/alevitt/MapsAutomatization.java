package alevitt;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MapsAutomatization {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response =
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.addPlace())
        .when()
                .post("maps/api/place/add/json")
        .then()
                //.log().all()
                .assertThat()
                    .statusCode(200)
                    .body("scope", equalTo("APP"))
                    .header("Server", equalTo("Apache/2.4.52 (Ubuntu)")) // ("Server", "value")
                .extract().response().asString()
        ;

        // parsing Json from String
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println(placeId);
    }
}
