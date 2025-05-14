package tests.api;

import models.LoginRequestModel;
import models.LoginResponseModel;
import specs.Specs;
import tests.TestData;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthorizationApi {

    public static LoginResponseModel login() {
        LoginRequestModel request = new LoginRequestModel(TestData.USERNAME, TestData.PASSWORD);
        return given(Specs.requestSpec)
                .body(request)
                .contentType(JSON)
                .when()
                .post("/Account/v1/Login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }

}
