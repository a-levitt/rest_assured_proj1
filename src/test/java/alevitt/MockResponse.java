package alevitt;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.Arrays;

public class MockResponse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.dummyResponse());

        int actualSum = 0;

        int coursesCount = js.getInt("courses.size()");
        System.out.println("1. Number of courses: " + coursesCount);

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("2. Purchase amount: " + purchaseAmount);

        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println("3. First course title: " + firstCourseTitle);

        System.out.println("4. All courses with prices:");
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int coursePrice = js.getInt("courses[" + i + "].price");
            int courseCopies = js.getInt("courses[" + i + "].copies");
            actualSum += coursePrice * courseCopies;
            System.out.println("Course " + (i + 1) + ": " + courseTitle + " with price " + coursePrice);
        }

        int copiesOfRPA = js.getInt("courses[2].copies");
        System.out.println("5. Number of copies sold by RPA course: " + copiesOfRPA);

        Assert.assertEquals(actualSum, purchaseAmount);
        System.out.println("6. Actual sum matches purchase amount");
    }

}
