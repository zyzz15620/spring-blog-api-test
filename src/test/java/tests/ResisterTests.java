package tests;

import common.RestAssuredSetup;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import static common.ConstantUtils.*;

public class ResisterTests {
    //Verify Response - Valid
    @Test
    public void verifyValidResponse(){
        RestAssuredSetup.setup();
        Response response= RestAssured.given().log().all()
                .get(GET_POSTS_PATH);
        System.out.println(response.asString());
    }

}
