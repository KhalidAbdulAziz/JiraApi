import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import javaFiles.PayLoad;
import javaFiles.ReuseableMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class StaticJson {
//automation test to add book
    @Test

    public void addBook() throws IOException {
      //  String postdata = GenerateStringFromResource("C:\\Users\\mkhalid\\Desktop\\postData.xml");

        //System.out.println(postdata);
        RestAssured.baseURI = "http://216.10.245.166";  //http://216.10.245.166/maps/api/place/add/json
    Response resp =
                given().
               // queryParam("key","qaclick123").
                    header("Content-Type","application/json;charset=UTF-8").
                       body(GenerateStringFromResource("C:\\Users\\mkhalid\\Desktop\\BookDetails.json")).
                            when().
                            post("Library/Addbook.php").
                            then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
                JsonPath js  = ReuseableMethod.rawToJson(resp);
                String id =  js.get("ID");
                System.out.println(id);

    }

    @DataProvider(name = "BooksData")

    public Object[][] getData()
    {
        return new Object[][] {{"vcs","7654"},{"cis","4653"},{"css","555"}};

    }

    public static String GenerateStringFromResource(String path) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


}
