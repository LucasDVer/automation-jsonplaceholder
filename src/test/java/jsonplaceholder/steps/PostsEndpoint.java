package jsonplaceholder.steps;

import com.common.framework.api.BaseAPI;
import com.common.framework.utils.FileUtils;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PostsEndpoint extends BaseAPI {

    private static JsonObject jsonData;

    private static final String GET_ALL_ENDPOINT = "getAll";

    private static final String JSON_DATA_PATH = "C:/Users/ASUS/Documents/jsonplaceholder data files/PostsDataFile.json";

    @Override
    protected void loadBaseUrl() {
        setBaseUrl("https://jsonplaceholder.typicode.com/");
    }

    @Override
    protected void loadSpecificPath() {
        setSpecificPath("posts");
    }

    private static final String GET_POSTS_INFORMATION_BY_QUERY = "?%s";

    private static final String POSTS_INFORMATION_BY_ID = "/%s";

    //private static final String GET_NESTED_INFORMATION = POSTS_INFORMATION_BY_ID + "/comments";

    public Response getPosts() {
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, GET_ALL_ENDPOINT);
        return doGet("");
    }

    public Response getPostsById() {
        jsonData = FileUtils.getStringFromJsonFile(JSON_DATA_PATH, GET_ALL_ENDPOINT);
        return doGet(String.format(POSTS_INFORMATION_BY_ID, jsonData.get("id")));
    }

}
