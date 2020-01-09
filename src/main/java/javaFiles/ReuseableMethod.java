package javaFiles;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReuseableMethod
{
    public static XmlPath rawToXml(Response r)
    {
        String res_x = r.asString();
        XmlPath xmlp = new XmlPath(res_x);
        return xmlp;
    }

    public static JsonPath rawToJson(Response r)
    {
        String res_j = r.asString();
        JsonPath jp = new JsonPath(res_j);
        return  jp;
    }

    public static String getSessionKey()
    {
        // creating the session
        RestAssured.baseURI = "http://localhost:8080";
        Response resp = given().header("Content-Type","application/json").
                body("{ \"username\": \"mkhalid\", \"password\": \"Pakistan123\" }").
                post("/rest/auth/1/session").
                then().statusCode(200).extract().response();
        JsonPath jp = ReuseableMethod.rawToJson(resp);
        String sessionValue = jp.get("session.value");
        return sessionValue;
    }

    public static String getIssueId()
    {
        RestAssured.baseURI = "http://localhost:8080";
        Response resp = given().header("Content-Type","application/json").
                header("Cookie","JSESSIONID="+ReuseableMethod.getSessionKey()).
                body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"RES\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Automation issue/defect is created.\",\n" +
                        "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}").when().post("/rest/api/2/issue").then().extract().response();
        JsonPath jp = ReuseableMethod.rawToJson(resp);
        String isssueId = jp.get("id");
        return isssueId;
    }

}
