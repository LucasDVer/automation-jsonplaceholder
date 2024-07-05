package jsonplaceholder.steps;

import com.common.framework.utils.FileUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;


public class PostsSteps {

    private static final String jsonDataPath = "C:/Users/ASUS/Documents/jsonplaceholder data files/PostsDataFile.json";

    private Response response;

    //TODO: This attribute/constant has to be move to a higher class. Thinking about creating an abstract base class.
    private static final String jsonPlaceholderUrl  = "https://jsonplaceholder.typicode.com/";

    private static final String postsInformation = jsonPlaceholderUrl + "posts";

    private static String getPostsInformationByQuery = postsInformation + "?query";

    private static final String postsInformationById = postsInformation + "/postId";

    private static final String getNestedInformation = postsInformation + "/comments";

    @Given("The user has access to the JsonPlaceholder endpoint.")
    public void theUserAccessToTheJsonPlaceholderEndpoint(){
        System.out.println("This API doesn't need any authentication.");
    }

    @When("The user performs a get request to obtain all the posts.")
    public void theUserPerformsAGetRequestToObtainAllThePosts(){
        response = RestAssured.given().get(postsInformation);
    }
/*
    @When("The user performs a post request to create a Post.")
    public void theUserPerformsAPostRequestToCreateAPost(){
        response = RestAssured.given().header("Content-Type", "application/json")
                                      .body(FileUtils.getStringFromJsonFile(jsonDataPath,"post"))
                                      .when()
                                      .post("/posts")
                                      .then()
                                      .extract()
                                      .response();
    }
*/
    @Then("The user expects the correct status code after doing a {string} request.")
    public void theStatusCodeShouldBeTheCorrectOne(String requestName){
        JsonObject jsonData = FileUtils.getStringFromJsonFile(jsonDataPath,requestName);
        Assert.assertEquals("The status code is not the correct one.",jsonData.get("statusCode").getAsInt()
                                                                             , response.statusCode());
    }

    @And("The user expects the correct count of posts after doing a {string} request.")
    public void theNumberOfPostsShouldBeTheCorrectOne(String requestName){
        JsonObject jsonData = FileUtils.getStringFromJsonFile(jsonDataPath,requestName);
        Assert.assertEquals("The count post is not the correct one.",jsonData.get("countPosts").getAsInt()
                                                                            , response.as(List.class).size());

    }


}
