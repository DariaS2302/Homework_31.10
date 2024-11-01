import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class ReqresTests extends TestBase {

    @Test
    void checkSingleResourceTest() {

        given()
                .log().uri()
                .get("/unknown/2")

                .then()
                .log().body()
                .body("data.pantone_value", is("17-2031"));
    }

    @Test
    void successfulCheckListResourceTest() {

        given()
                .log().uri()
                .get("/unknown")

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
    void unsuccessfulCheckListResourceTest() {

        given()
                .log().uri()
                .get("/unknown")

                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("data.id", hasItems(10));
    }

    @Test
    void successfulCheckCreateUserTest() {

        String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void unsuccessfulCheckCreateUserTest() {

        String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("nothing"));
    }

    @Test
    void checkDeleteUserTest() {

        given()
                .log().uri()
                .delete("/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }
}
