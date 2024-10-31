
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresTests {

@Test
    void checkSingleResource() {

         given()
            .log().uri()
            .get("https://reqres.in/api/unknown/2")

         .then()
            .log().body()
            .body("data.pantone_value", is("17-2031"));
}

@Test
    void successfulCheckListResource() {

         given()
                .log().uri()
                .get("https://reqres.in/api/unknown")

         .then()
                .log().body()
                .assertThat()
                .body("data.id", hasItems(3, 4, 5))
                .body("data.name", hasItems("true red", "blue turquoise"))
                .body("data.year", hasItems(2005, 2002, 2003))
                .body("data.color", hasItems("#53B0AE", "#BF1932"))
                .body("data.pantone_value", hasItems("19-1664"));
    }

    @Test
    void unsuccessfulCheckListResource() {

        given()
                .log().uri()
                .get("https://reqres.in/api/unknown")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("data.id", hasItems(10));
    }

@Test
    void successfulCheckCreateUser() {

    String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
            .body(data)
            .contentType(JSON)
            .log().uri()

        .when()
            .post("https://reqres.in/api/users")

        .then()
            .log().status()
            .log().body()
            .statusCode(201)
            .body("name", is("morpheus"))
            .body("job", is("leader"));
}

    @Test
    void unsuccessfulCheckCreateUser() {

        String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()

        .when()
                .post("https://reqres.in/api/users")

        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("nothing"));
    }

    @Test
    void checkDeleteUser() {

         given()
                .log().uri()
                .delete("https://reqres.in/api/users/2")
         .then()
                .log().body()
                .log().status()
                .statusCode(204);
    }
}
