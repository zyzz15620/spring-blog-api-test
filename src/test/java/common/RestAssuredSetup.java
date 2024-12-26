package common;

import io.restassured.RestAssured;

import static common.ConstantUtils.BASE_URL;
import static common.ConstantUtils.PORT;

public class RestAssuredSetup {
    public static void setup(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = Integer.valueOf(PORT);
    }
}
