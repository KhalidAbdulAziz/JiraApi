import io.restassured.RestAssured;
import javaFiles.ReuseableMethod;
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


public class JiraApi
{
    Properties prop = new Properties();

    @BeforeTest
    public void getData() throws IOException
    {

        FileInputStream fp = new FileInputStream("C:\\Users\\mkhalid\\IdeaProjects\\comtrainingapi\\src\\main\\java\\files\\env.properties");
        prop.load(fp);

    }

    @Test
    public  void jiraApiMethod()
    {        // creating Issues/Defects
        RestAssured.baseURI = "http://localhost:8080";
         Response resp = given().header("Content-Type","application/json").
                 header("Cookie","JSESSIONID="+ReuseableMethod.getSessionKey()).
                 body("{\n" +
                         "    \"fields\": {\n" +
                         "       \"project\":\n" +
                         "       {\n" +
                         "          \"key\": \"RES\"\n" +
                         "       },\n" +
                         "       \"summary\": \"New Automation issue.\",\n" +
                         "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                         "       \"issuetype\": {\n" +
                         "          \"name\": \"Bug\"\n" +
                         "       }\n" +
                         "   }\n" +
                         "}").when().post("/rest/api/2/issue").then().extract().response();
                        JsonPath jp = ReuseableMethod.rawToJson(resp);
                        String isssueId = jp.get("id");
        System.out.println(isssueId);
    }
}
