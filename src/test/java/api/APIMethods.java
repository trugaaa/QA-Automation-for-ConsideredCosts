package api;

public class APIMethods {
    private static final String ROOT_URL = "https://ccostsproject.azurewebsites.net";

    private static String validAuthToken;
    private static final String INVALID_AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9u" +
            "YW1lIjoiQWRtaW4iLCJuYmYiOjE1NzQ4ODE3NTMsImV4cCI6MTU3NDg4MjM1MywiaXNzIjoiQ0NUZWFtIiwiYXVkIjoiQ0NVc2VycyJ9.yz1mP4VMxBzN516mvmH-T0F9VshYQmVfbnmzi6CkOw";



    public static String getValidAuthToken() {
        return validAuthToken;
    }

    public static void setValidAuthToken(String validAuthToken) {
        APIMethods.validAuthToken = validAuthToken;
    }

    public static String getInvalidAuthToken() {
        return INVALID_AUTH_TOKEN;
    }

    public static String getURLWithEndpoint(String endpoint)
    {
        return ROOT_URL + endpoint;
    }
}
