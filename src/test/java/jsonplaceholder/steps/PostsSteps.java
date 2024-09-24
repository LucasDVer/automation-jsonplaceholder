package jsonplaceholder.steps;

import com.common.framework.utils.FileUtils;
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

    private JsonObject jsonData;

    //TODO: This attribute/constant has to be move to a higher class. Thinking about creating an abstract base class.
    private static final String jsonPlaceholderUrl  = "https://jsonplaceholder.typicode.com/";

    private static final String postsInformation = jsonPlaceholderUrl + "posts";

    private static String getPostsInformationByQuery = postsInformation + "?%s";

    private static final String postsInformationById = postsInformation + "/%s";

    private static final String getNestedInformation = postsInformationById + "/comments";

    @Given("The user has access to the JsonPlaceholder endpoint.")
    public void theUserAccessToTheJsonPlaceholderEndpoint(){
        System.out.println("This API doesn't need any authentication.");
    }

    @When("The user performs a get request {string} to obtain all the posts.")
    public void theUserPerformsAGetRequestToObtainAllThePosts(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        response = RestAssured.given().get(postsInformation);
    }

    @When("The user performs a get request {string} to obtain a post by Id.")
    public void theUserPerformsAGetRequestToObtainAPostById(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(postsInformationById, jsonData.get("id"));
        response = RestAssured.given().get(path);
    }

    @When("The user performs a post request {string} to create a new post.")
    public void theUserPerformsAPostRequestToCreateAPost(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        response = RestAssured.given().header("Content-Type", "application/json")
                .body(FileUtils.getStringFromJsonFileWithoutStatusCode(jsonDataPath,requestName))
                .when()
                .post(postsInformation)
                .then()
                .extract()
                .response();
    }

    @When("The user performs a put request {string} to update a post.")
    public void theUserPerformsAPutRequestToUpdateAPost(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(postsInformationById, jsonData.get("id"));
        response = RestAssured.given().header("Content-Type", "application/json")
                .body(FileUtils.getStringFromJsonFileWithoutStatusCode(jsonDataPath,requestName))
                .when()
                .put(path)
                .then()
                .extract()
                .response();
    }

    @When("The user performs a patch request {string} to update a post.")
    public void theUserPerformsAPatchRequestToUpdateAPost(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(postsInformationById, jsonData.get("id"));
        response = RestAssured.given().header("Content-Type", "application/json")
                .body(FileUtils.getStringFromJsonFileWithoutStatusCode(jsonDataPath,requestName))
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    @When("The user performs a delete request {string} to delete a post.")
    public void theUserPerformsADeleteRequestToDeleteAPost(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(postsInformationById, jsonData.get("id"));
        response = RestAssured.given().delete(path);
    }

    @When("The user performs a get request {string} to obtain a comment by postId.")
    public void theUserPerformsAGetRequestToObtainACommentByPostId(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(getNestedInformation, jsonData.get("id"));
        response = RestAssured.given().get(path);
    }

    @When("The user performs a get request {string} to obtain all the posts by userId.")
    public void theUserPerformsAGetRequestToObtainAllThePostByUserId(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(getPostsInformationByQuery, "userId=" + jsonData.get("userId"));
        response = RestAssured.given().get(path);
    }

    @When("The user performs a get request {string} to obtain all the posts by title.")
    public void theUserPerformsAGetRequestToObtainAllThePostByTitle(String requestName){
        jsonData = FileUtils.getStringFromJsonFile(jsonDataPath, requestName);
        String path = String.format(getPostsInformationByQuery, "title=" + jsonData.get("title"));
        response = RestAssured.given().get(path);
    }

    @Then("The user expects the correct status code.")
    public void theStatusCodeShouldBeTheCorrectOne(){
        Assert.assertEquals("The status code is not the correct one.",jsonData.get("statusCode").getAsInt()
                                                                             , response.statusCode());
    }

    @And("The user expects the correct count.")
    public void theNumberOfPostsShouldBeTheCorrectOne(){
        Assert.assertEquals("The count post is not the correct one.",jsonData.get("countPosts").getAsInt()
                                                                            , response.as(List.class).size());

    }

    @And("The user expects the correct user id.")
    public void theUserIdOfPostShouldBeTheCorrectOne(){
        Assert.assertEquals("The user id is not the correct one", jsonData.get("userId").getAsInt()
                                                                        , response.jsonPath().getInt("userId"));
    }

    @And("The user expects the correct id.")
    public void theIdOfPostShouldBeTheCorrectOne(){
        Assert.assertEquals("The user id is not the correct one", jsonData.get("id").getAsInt()
                , response.jsonPath().getInt("id"));
    }

    @And("The user expects the correct title.")
    public void theTitleOfPostShouldBeTheCorrectOne(){
        Assert.assertEquals("The title is not the correct one.", jsonData.get("title").getAsString()
                                                                       , response.jsonPath().getString("title"));
    }

    @And("The user expects the correct body.")
    public void theBodyOfThePostShouldBeTheCorrectOne(){
        Assert.assertEquals("The body is no the correct one", jsonData.get("body").getAsString()
                                                                     , response.jsonPath().getString("body"));
    }

    @And("The user expects the correct comment count.")
    public void theCommentCountShouldBeTheCorrectOne(){
        Assert.assertEquals("The comment cont is not the correct one.", jsonData.get("countComments").getAsInt()
                                                                              , response.as(List.class).size());
    }
}
