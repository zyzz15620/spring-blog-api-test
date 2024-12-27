package common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Account;
import model.AccountEntity;

import static common.ConstantUtils.CREATE_ACCOUNT_PATH;

public class MethodUtils {
    public static Response createAccount(Account account){
        Response response= RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(account)
                .post(CREATE_ACCOUNT_PATH);
        return response;
    }
}
