package alevitt;

import files.pogo.AddPlaceBase;
import files.pogo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuildersTest {
    public static void main(String[] args) {

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

        RequestSpecification REQsb = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build()
        ;

        ResponseSpecification RESsb = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build()
        ;

        RequestSpecification request =
        given()
                .spec(REQsb)
                .body(addPlaceBody)
        ;

        Response response =
        request.when()
                .post("maps/api/place/add/json")
        .then()
                .spec(RESsb)
                .extract().response();
        ;

        String stringRes = response.asString();
        System.out.println(stringRes);
    }
}
