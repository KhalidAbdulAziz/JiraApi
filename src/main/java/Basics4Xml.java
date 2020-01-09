import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import javaFiles.ReuseableMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics4Xml {

    @Test
    public void postData() throws IOException {
        String postdata = GenerateStringFromResource("C:\\Users\\mkhalid\\Desktop\\postData.xml");

        System.out.println(postdata);
        RestAssured.baseURI = "http://216.10.245.166";  //http://216.10.245.166/maps/api/place/add/json
    Response resp =
                given().
                queryParam("key","qaclick123").
                body(postdata).
                            when().
                            post("/maps/api/place/add/xml").
                            then().assertThat().statusCode(200).and().contentType(ContentType.XML).extract().response();

      /*           String res = resp.asString();
                 System.out.println(res);
                 XmlPath xmlp = new XmlPath(res); */

       XmlPath xmlp = ReuseableMethod.rawToXml(resp);
        System.out.println(xmlp.get("response.status"));


    }

    public static String GenerateStringFromResource(String path) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


}
