import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import javaFiles.ReuseableMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class JiraAddCommentApi
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
                         "    \"body\": \"No need of custome comment.\",\n" +
                         "    \"visibility\": {\n" +
                         "        \"type\": \"role\",\n" +
                         "        \"value\": \"Administrators\"\n" +
                         "    }\n" +
                         "}").when().post("/rest/api/2/issue/10011/comment").then().extract().response();

    }
}
