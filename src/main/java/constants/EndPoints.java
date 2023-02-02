package constants;


public class EndPoints {
    public static final String GET_SINGLE_PET = "/pet/{id}";
    public static final String ADD_PET = "/pet";
    public static final String UPDATE_PET = "/pet";
    public static final String DELETE_PET = "/pet/{id}";

    public static final String GET_ALL_PETS_WITH_GIVEN_STATUS = "/pet/findByStatus"; // query param: status [available | pending | sold]

}
