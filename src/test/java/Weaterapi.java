import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Weaterapi {

        public static final String API_KEY = "30342da41ff04537a29191035210803";
        public static final String BASE_URI = "http://api.weatherapi.com/";
        public static final String endpoint = "v1/current.json";
        public static RequestSpecification spec; //переменная для подготовки первоначального состояния запросов

        @BeforeAll
        static void setUp(){
            spec = new RequestSpecBuilder()
                    .setBaseUri(BASE_URI)
                    .log(LogDetail.ALL)
                    .setAccept(ContentType.JSON)
                    .addQueryParam("apiKey", API_KEY)
                    .build();
        }

        @DisplayName("Тест проверяет что в результате GET-запроса возвращается код 200")
        @Test
        void checkStatusCode(){
            given().spec(spec)
                    .when().get()
                    .then().statusCode(200);
    }
    @DisplayName("Проверка наличия параметров")
    @ParameterizedTest
  //  @NullSource
//void method(String input){
//            assertTrue
//    }
      @ValueSource(strings = {"London","Moscow"})
       void checkParameter(String input){
            given().spec(spec).queryParam("q","Moscow")
                    .when().get(endpoint)
                    .then().log().all().header("Date",Matchers.notNullValue());
//                    .assertTrue(String.isBlank(input));
 //                   .statusCode(401); //проверяем код ответа от сервера
         //     .and().body(Matchers.containsString("date")); //проверяем


//        @DisplayName("Тест проверяет что в результате GET-запроса возвращается код 200")
//        @Test
//        void checkHeader() {
//            given().spec(spec).queryParam("q","Moscow")
//                    .when().get()
//                    .then().log().all().header("Date",Matchers.notNullValue());
        }

        @DisplayName("Тест проверяет что параметр пуст")
        @Test
        void checkLocationFields() {
            given().spec(spec).queryParam("q", "Moscow")
                    .when().get(endpoint)
                    .then().log().all()
                    .body("location", Matchers.nullValue());
 //                   .and().statusCode(200);
        //            .body("location.country", Matchers.equalTo("Russia"));
        }
    }


