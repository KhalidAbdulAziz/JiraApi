import io.restassured.RestAssured;
import javaFiles.TestResources;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import javaFiles.TestResources;
import javaFiles.PayLoad;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics3 {
   Properties prop = new Properties();

    @BeforeTest
    public void getData() throws IOException
    {

        FileInputStream fp = new FileInputStream("C:\\Users\\mkhalid\\IdeaProjects\\comtrainingapi\\src\\main\\java\\files\\env.properties");
        prop.load(fp);
    }
    @Test
    public void AddandDeletePlace()
    {
      RestAssured.baseURI = prop.getProperty("HOST");
        Response resp = given().
                queryParam("key",prop.getProperty("KEY")).
                        body(PayLoad.getPostData()).
                when().
                        post(TestResources.placePostData()).
                then().
                assertThat().
                        statusCode(200).and().contentType(ContentType.JSON).and().
                        body("status",equalTo("OK")).extract().response();

                String respString = resp.asString();
                System.out.println(respString);
        JsonPath jp = new JsonPath(respString);
        String placeId = jp.get("place_id");
        System.out.println(placeId);
        Map<String, Object> delBody = new HashMap<String, Object>();
        delBody.put("place_id", placeId);

        //let's delete the place_Id from the delete request
        given().
                queryParam("key",prop.getProperty("KEY")).
                body(delBody).
                when().
                post(TestResources.deletePostData()).
                then().and().
                assertThat().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("status",equalTo("OK"));
        System.out.println("smooth execution");

    }
}
