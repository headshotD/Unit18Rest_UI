//package tests;
//
//import models.LoginBodyModel;
//import models.LoginResponseModel;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
//import static io.qameta.allure.Allure.step;
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static specs.Specs.requestSpec;
//import static specs.Specs.loginResponseSpec;
//
//@Tag("AllApi")
//public class AddBooksDemoQa extends TestBase {
//
//    @Test
//    @DisplayName("Successful authentication test")
//    void successAuthTest() {
//        LoginBodyModel authData = new LoginBodyModel();
//        authData.setUserName("OLEG");
//        authData.setPassword("Oleg!234");
//
//        LoginResponseModel response = step("Auth", () ->
//                given(requestSpec)
//                        .body(authData)
//                        .when()
//                        .post("Account/v1/Login")
//                        .then()
//                        .spec(loginResponseSpec)
//                        .extract().as(LoginResponseModel.class));
//
//        step("Check response", () -> {
//            assertNotNull(response.getToken());
//            assertFalse(response.getToken().isEmpty());
//            assertFalse(response.getToken().trim().isEmpty());
//        });
//
//        this.sessionToken = response.getToken();
//    }
//    private String sessionToken;
//
//}
