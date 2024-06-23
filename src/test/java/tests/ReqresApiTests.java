package tests;

import models.*;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.CreateSpec.*;

public class ReqresApiTests extends TestBase {
    @DisplayName("Создание пользователя")
    @Test
    public void createUserTest() {
        CreateUserBodyModel userData = new CreateUserBodyModel();
        userData.setName("morpheus");
        userData.setJob("leader");
        CreateUserResponseModel response = step("Отправляем запрос", () ->
        given()
                .spec(requestSpec)
                .body(userData)
                .when()
                .post("/users")
                .then()
                .spec(createdResponseSpec)
                .extract().as(CreateUserResponseModel.class));
        step("Проверяем, что имя соответствует заданному", () ->
                assertThat(response.getName(), equalTo(userData.getName())));
        step("Проверяем, что работа соответствует заданной", () ->
                assertThat(response.getJob(), equalTo(userData.getJob())));
        step("Проверяем, что id не пустой", () ->
                assertThat(response.getId(), notNullValue()));
    }

    @DisplayName("Редактирование пользователя")
    @Test
    public void updateUserTest() {
        CreateUserBodyModel userData = new CreateUserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");
        CreateUserResponseModel response = step("Отправляем запрос", () ->
                given()
                        .spec(requestSpec)
                        .body(userData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(successful200ResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
        step("Проверяем, что имя соответствует заданному", () ->
                assertThat(response.getName(), equalTo(userData.getName())));
        step("Проверяем, что работа соответствует заданной", () ->
                assertThat(response.getJob(), equalTo(userData.getJob())));
    }

    @DisplayName("Частичное редактирование пользователя")
    @Test
    public void partUpdateUserTest() {
        CreateUserBodyModel userData = new CreateUserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");
        CreateUserResponseModel response = step("Отправляем запрос", () ->
                given()
                        .spec(requestSpec)
                        .body(userData)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(successful200ResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
        step("Проверяем, что имя соответствует заданному", () ->
                assertThat(response.getName(), equalTo(userData.getName())));
        step("Проверяем, что работа соответствует заданной", () ->
                assertThat(response.getJob(), equalTo(userData.getJob())));
    }

    @DisplayName("Удаление пользователя")
    @Test
    public void deleteUserTest() {
        step("Отправляем запрос", () ->
                given(requestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(successful204ResponseSpec));
    }

    @Test
    @DisplayName("Неуспешное получение пользователя по id")
    void getNonexistentUserTest() {
        CreateUserResponseModel response = step("Отправляем запрос", () ->
                given(requestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(error404ResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
    }
}
