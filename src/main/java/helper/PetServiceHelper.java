package helper;
import constants.ApiMethods;
import constants.BaseUrl;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class PetServiceHelper {
    private String method;
    private String BASE_URL = BaseUrl.STAGING;
    private String fullURL;

    private RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    public PetServiceHelper(String endpoint, String method, String token){
        this.fullURL = this.BASE_URL + endpoint;
        this.method = method;
        if (token != null)
            requestSpecBuilder.addHeader("Authorization", "Bearer " + token);

    }

    public String getFullURL() {
        return fullURL;
    }

    private ResponseOptions<Response> executeAPI() {
        RequestSpecification requestSpecification = requestSpecBuilder.build();
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpecification);

        if (this.method.equalsIgnoreCase(ApiMethods.POST))
            return request.post(this.fullURL);
        else if(this.method.equalsIgnoreCase(ApiMethods.GET))
            return request.get(this.fullURL);
        else if(this.method.equalsIgnoreCase(ApiMethods.PUT))
            return request.put(this.fullURL);
        else if(this.method.equalsIgnoreCase(ApiMethods.DELETE))
            return request.delete(this.fullURL);
        return null;

    }

    public String authenticate(Object body) {
        requestSpecBuilder.setBody(body);
        return executeAPI().getBody().jsonPath().get("access_token");
    }

    public ResponseOptions<Response> executeWithQueryParams(Map<String, String> queryParams) {
        requestSpecBuilder.addQueryParams(queryParams);
        return executeAPI();

    }

    public ResponseOptions<Response> executeWithPathParams(Map<String, String> pathParams) {
        requestSpecBuilder.addPathParams(pathParams);
        return executeAPI();
    }

    public ResponseOptions<Response> executeWithBodyParams(Object body) {
        requestSpecBuilder.setBody(body);
        return executeAPI();
    }

    public ResponseOptions<Response> executeWithPathParamsAndBody(Map<String, String> pathParams, Map<String, String> body) {
        requestSpecBuilder.setBody(body);
        requestSpecBuilder.addPathParams(pathParams);
        return executeAPI();
    }


}
