import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresApiTests extends TestBase {
    @DisplayName("Создание пользователя")
    @Test
    public void createUserTest() {
        String userData = "{\"name\":\"morpheus\",\"job\":\"leader\"}";
        given()
                .relaxedHTTPSValidation()
                .body(userData)
                .contentType(JSON)
                .log().all()
                .when()
                .post("/users").
                then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @DisplayName("Редактирование пользователя")
    @Test
    public void updateUserTest() {
        String userData = "{\"name\":\"morpheus\",\"job\":\"zion resident\"}";
        given()
                .relaxedHTTPSValidation()
                .body(userData)
                .contentType(JSON)
                .log().all()
                .when()
                .put("/users/2").
                then()
                .log().all()
                .statusCode(200)

                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
    }

    @DisplayName("Частичное редактирование пользователя")
    @Test
    public void partUpdateUserTest() {
        String userData = "{\"name\":\"morpheus\",\"job\":\"zion resident\"}";
        given()
                .relaxedHTTPSValidation()
                .body(userData)
                .contentType(JSON)
                .log().all()
                .when()
                .patch("/users/2").
                then()
                .log().all()
                .statusCode(200)

                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
    }

    @DisplayName("Удаление пользователя")
    @Test
    public void deleteUserTest() {
        given()
                .relaxedHTTPSValidation()
                .log().all()
                .when()
                .delete("/users/2").
                then()
                .log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("Неуспешное получение пользователя по id")
    void getNonexistentUserTest() {
        given()
                .relaxedHTTPSValidation()
                .log().all()
                .when()
                .get("/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }
}
