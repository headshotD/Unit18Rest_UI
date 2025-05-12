//package helpers;
//
//import io.restassured.http.Cookie;
//import org.junit.jupiter.api.extension.BeforeEachCallback;
//import org.junit.jupiter.api.extension.ExtensionContext;
//
//import static com.codeborne.selenide.Selenide.open;
//import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
//
//public class LoginExtension implements BeforeEachCallback {
//
//    @Override
//    public void beforeEach(ExtensionContext context) {
//        private static final String SESSION = "ALLURE_TESTOPS_SESSION";
//
//        String cookies = AuthorizationApi.getAuthorizationCookie();
//
//        open("/favicon.ico");
//        getWebDriver().manage().addCookie(new Cookie(SESSION, cookies));
//    }
//}
