package alevitt;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MapsAutomatization {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Create place

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


        // Update place
        String newAddress = "70 Summer walk, USA";
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\r\n" +
                        "             \"place_id\": \"" + placeId + "\",\r\n" +
                        "             \"address\": \"" + newAddress + "\",\r\n" +
                        "             \"key\": \"qaclick123\"\r\n" +
                        " }\r\n" +
                        " ")
        .when()
                .put("maps/api/place/update/json")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(200)
                    .body("msg", equalTo("Address successfully updated"))
        ;

        // Get place
        String getPlaceResponse =
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
        .when()
                .get("maps/api/place/get/json")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(200)
                    //.body("address", equalTo(newAddress))
                .extract().response().asString()
        ;

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actualAdress = js1.getString("address");
        Assert.assertEquals(actualAdress, newAddress); // TestNG

    }
}
