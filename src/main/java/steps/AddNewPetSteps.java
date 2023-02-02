package steps;

import constants.ApiMethods;
import constants.EndPoints;
import helper.PetServiceHelper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import model.Category;
import model.Pet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

public class AddNewPetSteps {

    public static ResponseOptions<Response> response;
    public static String token;
    public static Category newPetCategory = new Category();
    @Given("I create new pet category with name={string} and id={int}")
    public void iCreateNewPetCategoryWithNameAndId(String newCategoryName, Integer newCategoryID) {
        newPetCategory.setId(newCategoryID);
        newPetCategory.setName(newCategoryName);
    }

    @Given("I create and send new pet with name={string} and status={string} and id={int}")
    public void iCreateAndSendNewPetWithNameAndStatusAndId(String newPetName, String newPetStatus, Integer newPetID) {
        Pet pet = new Pet();
        pet.setCategory(newPetCategory);
        pet.setId(newPetID);
        pet.setName(newPetName);
        pet.setStatus(newPetStatus);

        System.out.println(pet.getName());


        PetServiceHelper petServiceHelper = new PetServiceHelper(EndPoints.ADD_PET, ApiMethods.POST, token);
        System.out.println("FULL URL: " + petServiceHelper.getFullURL());
        response = petServiceHelper.executeWithBodyParams(pet);

    }

    @Then("I {string} see pet id={int} in response body")
    public void iSeePetIdInResponseBody(String condition, Integer expectedPetID) {
        // Response as JSON
        JsonPath jsonResponse = response.getBody().jsonPath();
        String petIDfromJSONResponse = jsonResponse.getString("id");

        // Response as deserialized POJO
        Pet petObjResponse = response.getBody().jsonPath().getObject(".", Pet.class);
        Integer petIDfromObjResponse = petObjResponse.getId();

        if (condition.equalsIgnoreCase("should not"))
            assertNotEquals(petIDfromJSONResponse, expectedPetID.toString(), "The PetID is visible in the response body but it should not be.");
        else {
            assertEquals(petIDfromJSONResponse, expectedPetID.toString());
            // or compare integers using POJO response
            assertEquals(petIDfromObjResponse, expectedPetID);
        }

    }

    @And("Status={string}")
    public void status(String arg0) {
        System.out.println("Status: " + arg0);
    }

    @Given("I request a list of pets with status={string}")
    public void iRequestAListOfPetsWithStatus(String givenStatus) {
        PetServiceHelper petServiceHelper = new PetServiceHelper(EndPoints.GET_ALL_PETS_WITH_GIVEN_STATUS, ApiMethods.GET, token);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("status", givenStatus);
        response = petServiceHelper.executeWithQueryParams(queryParams);

    }

    @Then("I should get only pets with status={string}")
    public void iShouldGetOnlyPetsWithStatus(String givenStatus) {
        List <Pet> pets = response.getBody().jsonPath().getList(".", Pet.class);
        List <Pet> petsWithDifferentStatus = pets.stream().filter(pet -> !Objects.equals(pet.getStatus(), givenStatus)).toList();
        petsWithDifferentStatus.forEach(pet -> {
            System.out.println(pet.getName() + " | " + pet.getStatus());
        });
        assertTrue(petsWithDifferentStatus.isEmpty(), String.format("The response returns pets with status different than: %s", givenStatus));
        }

    @Then("I should get an empty list")
    public void iShouldGetAnEmptyList() {
        List <Pet> pets = response.getBody().jsonPath().getList(".", Pet.class);
        assertTrue(pets.isEmpty(), "The response is not an empty list.");
    }

    @And("I make request for retrieving data of a single pet with id={int}")
    public void iMakeRequestForRetrievingDataOfASinglePetWithId(Integer petID) {
        PetServiceHelper petServiceHelper = new PetServiceHelper(EndPoints.GET_SINGLE_PET, ApiMethods.GET, token);
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", petID.toString());
        response = petServiceHelper.executeWithPathParams(pathParams);

    }

    @Then("I should see pet details")
    public void iShouldSeePetDetails(DataTable data) {
        System.out.println(data.toString());
        List<String> expectedData = data.row(1);

        Pet petObjResponse = response.getBody().jsonPath().getObject(".", Pet.class);
        Integer petIDfromObjResponse = petObjResponse.getId();
        String petNameFromObjResponse = petObjResponse.getName();
        String petStatusFromObjResponse = petObjResponse.getStatus();

        assertEquals(expectedData.get(0), petIDfromObjResponse.toString());
        assertEquals(expectedData.get(1), petNameFromObjResponse);
        assertEquals(expectedData.get(2), petStatusFromObjResponse);

    }


}


