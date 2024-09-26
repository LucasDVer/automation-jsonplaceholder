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

    private static final String JSON_DATA_PATH = "C:/Users/ASUS/Documents/jsonplaceholder data files/PostsDataFile.json";

    private Response response;

    private JsonObject jsonData;

    //TODO: This attribute/constant has to be move to a higher class. Thinking about creating an abstract base class.
    private static final String JSONPLACEHOLDER_URL = "https://jsonplaceholder.typicode.com/";

    private static final String POST_INFORMATION = JSONPLACEHOLDER_URL + "posts";

    private static final String GET_POSTS_INFORMATION_BY_QUERY = POST_INFORMATION + "?%s";

    private static final String POSTS_INFORMATION_BY_ID = POST_INFORMATION + "/%s";

    private static final String GET_NESTED_INFORMATION = POSTS_INFORMATION_BY_ID + "/comments";

    private static final String GET_ENDPOINT = "get";

    private static final String GET_ALL_ENDPOINT = "getAll";

    private static final String POST_ENDPOINT = "post";

    private static final String PUT_ENDPOINT = "put";

    private static final String PATCH_ENDPOINT = "patch";

    private static final String DELETE_ENDPOINT = "delete";

    private static final String GET_COMMENTS_BY_POST_ID_ENDPOINT = "getCommentsByPostId";

    private static final String QUERY_GET_BY_USER_ID_ENDPOINT = "queryGetByUserId";

    private static final String QUERY_GET_BY_TITLE_ENDPOINT = "queryGetByTitle";

    @Given("The user has access to the JsonPlaceholder endpoint.")
    public void theUserAccessToTheJsonPlaceholderEndpoint(){
        System.out.println("This API doesn't need any authentication.");
    }

    @When("The user performs a get request to obtain all the posts.")
    public void theUserPerformsAGetRequestToObtainAllThePosts(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, GET_ALL_ENDPOINT);
        response = RestAssured.given().get(POST_INFORMATION);
    }

    @When("The user performs a get request to obtain a post by Id.")
    public void theUserPerformsAGetRequestToObtainAPostById(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, GET_ENDPOINT);
        String path = String.format(POSTS_INFORMATION_BY_ID, jsonData.get("id"));
        response = RestAssured.given().get(path);
    }

    @When("The user performs a post request to create a new post.")
    public void theUserPerformsAPostRequestToCreateAPost(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, POST_ENDPOINT);
        response = RestAssured.given().header("Content-Type", "application/json")
                .body(FileUtils.getStringFromJsonFileWithoutStatusCode(JSON_DATA_PATH, POST_ENDPOINT))
                .when()
                .post(POST_INFORMATION)
                .then()
                .extract()
                .response();
    }

    @When("The user performs a put request to update a post.")
    public void theUserPerformsAPutRequestToUpdateAPost(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, PUT_ENDPOINT);
        String path = String.format(POSTS_INFORMATION_BY_ID, jsonData.get("id"));
        response = RestAssured.given().header("Content-Type", "application/json")
                .body(FileUtils.getStringFromJsonFileWithoutStatusCode(JSON_DATA_PATH, PUT_ENDPOINT))
                .when()
                .put(path)
                .then()
                .extract()
                .response();
    }

    @When("The user performs a patch request to update a post.")
    public void theUserPerformsAPatchRequestToUpdateAPost(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, PATCH_ENDPOINT);
        String path = String.format(POSTS_INFORMATION_BY_ID, jsonData.get("id"));
        response = RestAssured.given().header("Content-Type", "application/json")
                .body(FileUtils.getStringFromJsonFileWithoutStatusCode(JSON_DATA_PATH, PATCH_ENDPOINT))
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    @When("The user performs a delete request to delete a post.")
    public void theUserPerformsADeleteRequestToDeleteAPost(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, DELETE_ENDPOINT);
        String path = String.format(POSTS_INFORMATION_BY_ID, jsonData.get("id"));
        response = RestAssured.given().delete(path);
    }

    @When("The user performs a get request to obtain a comment by postId.")
    public void theUserPerformsAGetRequestToObtainACommentByPostId(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, GET_COMMENTS_BY_POST_ID_ENDPOINT);
        String path = String.format(GET_NESTED_INFORMATION, jsonData.get("id"));
        response = RestAssured.given().get(path);
    }

    @When("The user performs a get request to obtain all the posts by userId.")
    public void theUserPerformsAGetRequestToObtainAllThePostByUserId(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, QUERY_GET_BY_USER_ID_ENDPOINT);
        String path = String.format(GET_POSTS_INFORMATION_BY_QUERY, "userId=" + jsonData.get("userId"));
        response = RestAssured.given().get(path);
    }

    @When("The user performs a get request to obtain all the posts by title.")
    public void theUserPerformsAGetRequestToObtainAllThePostByTitle(){
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, QUERY_GET_BY_TITLE_ENDPOINT);
        String title = "title=" + jsonData.get("title").toString();
        title = title.replace("\"","");
        String path = String.format(GET_POSTS_INFORMATION_BY_QUERY, title);
        response = RestAssured.given().get(path);
    }

    @Then("The user expects the correct status code.")
    public void theStatusCodeShouldBeTheCorrectOne(){
        Assert.assertEquals("The status code is not the correct one.",jsonData.get("statusCode").getAsInt()
                                                                             , response.statusCode());
    }

    @And("The response returns the correct count.")
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
