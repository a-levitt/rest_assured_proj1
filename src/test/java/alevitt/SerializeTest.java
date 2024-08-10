package alevitt;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import files.pogo.*;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializeTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlaceBase addPlaceBody = new AddPlaceBase();
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlaceBody.setLocation(location);
        addPlaceBody.setAccuracy(50);
        addPlaceBody.setName("Frontline house");
        addPlaceBody.setPhone_number("(+91) 983 893 3937");
        addPlaceBody.setAddress("29, side layout, cohen 09");
        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        addPlaceBody.setTypes(types);
        addPlaceBody.setWebsite("https://rahulshettyacademy.com");
        addPlaceBody.setLanguage("French-IN");

        Response response =
        given()
                .queryParam("key", "qaclick123")
                .body(addPlaceBody)
        .when()
                .post("maps/api/place/add/json")
        .then()
                //.log().all()
                .assertThat()
                    .statusCode(200)
                .extract().response();
        ;
        String stringRes = response.asString();
        System.out.println(stringRes);
    }
}
